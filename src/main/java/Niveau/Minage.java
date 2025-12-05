package Niveau;

import Sauvegarde.Connection;
import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.File;
import java.io.IOException;


public class Minage implements Listener {





    Connection connection;
    RPGPlugin main;


    public Minage(RPGPlugin main, Connection connection){


        this.main = main;
        this.connection = connection;

    }



    @EventHandler
    public void Minagee(BlockBreakEvent event){

        Player player = event.getPlayer();

        Material type = event.getBlock().getType();

        if(player == null)return;


        if(type == Material.STONE){


            player.sendMessage("§2 Vous avez casser de la pierre ");



            _ADD_(player,type);



        }








    }


    public void _ADD_(Player player, Material matos) {
player.sendMessage("Appel de la fonction  add Minnage");
        //on va faire un fichier yml qui va Stoker tous les mob du jeux
        File file = new File(main.getDataFolder(), "Block.yml");

        if (!main.getDataFolder().exists()) {

            player.sendMessage("Fichier inexistant on continue minage ");
            main.getDataFolder().mkdirs();
        }


        if (!file.exists()) {
            main.getLogger().warning("Le fichier Block.yml n'existe pas !");
            player.sendMessage("Le fichier Block.yml n'existe pas Minage !");

            try {
                file.createNewFile();
                main.getLogger().info("Fichier Block.yml créé (vide) !");
                player.sendMessage("Fichier Block.yml créé (vide) Minage ");
            } catch (IOException e) {

                player.sendMessage("Une erreur c'est produite Minage ");
                e.printStackTrace();
            }
        }
        //player.sendMessage("YamlConfiguration Minage ");

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        //player.sendMessage("YamlConfiguration Minage Après ");
        String type = matos.toString();// va juste recupéré le nom du block !
       // player.sendMessage("Récupération du nom du block qui est : " + type);
        // Vérifie si le type existe dans le YAML
        if (!config.contains(type)) {
            main.getLogger().warning("Block inconu  inconnu dans le YAML: " + type);
            player.sendMessage("Block inconue dans le truc sorry " + type);
            return;
        }



        ConfigurationSection section = config.getConfigurationSection(type);
        player.sendMessage("recupération de section  " + type);
        if (section == null){


            player.sendMessage("NULL GERMAIN NULLL  " + section);



        }

        //on va faire unE boucle qui va rrecuperer tous les nom que le Mobs peut avoir
        player.sendMessage("avant boucle minage " );
        for (String key : section.getKeys(false)) {



            if (type.equalsIgnoreCase(key)) {


                int exp = section.getInt(key + ".exp");


                connection.Ajouter(player.getUniqueId(), exp, "xp", "Minage", player);



                player.sendMessage("§4 le block miner vous rapporte  " + "§5" + exp);
                player.sendMessage("                 ");
                player.sendMessage(ChatColor.AQUA + "Votre Xp à été mis à jour ! ");



                connection.recupererCompetence(player);


            }else{

                player.sendMessage("BITTTE le truc dit que c'est pas pareil alorsd que le truc et et pareil");



            }

        }


    }





    }
