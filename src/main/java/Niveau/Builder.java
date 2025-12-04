package Niveau;

import Sauvegarde.Connection;
import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.File;
import java.io.IOException;

public class Builder {



    RPGPlugin main;
    Connection connection;


    public Builder(RPGPlugin main){


       this.main = main;



    }


    @EventHandler
    public void onBreak(BlockPlaceEvent e) {
        Block b = e.getBlock();
        Player p = e.getPlayer();



                _ADD_(p, b.getType());




        }










    public void _ADD_(Player player, Material matos) {

        //on va faire un fichier yml qui va Stoker tous les mob du jeux
        File file = new File(main.getDataFolder(), "Builder.yml");

        if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdirs();
        }


        if (!file.exists()) {
            main.getLogger().warning("Le fichier Builder.yml n'existe pas !");


            try {
                file.createNewFile();
                main.getLogger().info("Fichier Builder.yml créé (vide) !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String type = matos.toString();// va juste recupéré le nom du block !

        // Vérifie si le type existe dans le YAML
        if (!config.contains(type)) {
            main.getLogger().warning("Block inconu  inconnu dans le YAML: " + type);
            return;
        }


        //on va recupérer le tyype du mob si c'est un zombie ou un skellete etc..
        ConfigurationSection section = config.getConfigurationSection(type);
        if (section == null) return;

        //on va faire un boucle qui va rrecuperer tous les nom que le Mobs peut avoir
        for (String key : section.getKeys(false)) {



            if (type.equalsIgnoreCase(key)) {


                int exp = section.getInt(key + ".exp");



                connection.Ajouter(player.getUniqueId(), exp, "xp", "Builder", player);


                player.sendMessage("§4 le block poser vous rapporte  " + "§5" + exp);
                player.sendMessage("                 ");
                player.sendMessage(ChatColor.AQUA + "Votre Xp à été mis à jour ! ");



                connection.recupererCompetence(player);


            }

        }


    }





}

























