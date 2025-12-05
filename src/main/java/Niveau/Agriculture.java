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
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;
import java.io.IOException;

public class Agriculture implements Listener {

RPGPlugin main;
Connection connection;


public Agriculture(RPGPlugin main,Connection connection){
    this.main = main;
    this.connection = connection;

    }


    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        Player p = e.getPlayer();

        if (b.getType() == Material.WHEAT) {
            Ageable age = (Ageable) b.getBlockData();
            if (age.getAge() == age.getMaximumAge()) {
                p.sendMessage("Tu as récolté du blé mature !");



                _ADD_(p,b);



            } else {
                p.sendMessage("Le blé n'est pas mûr !");
            }
        }
    }



    @EventHandler
    public void onFertilize(BlockFertilizeEvent e) {
        Player p = e.getPlayer();
        p.sendMessage("Tu as utilisé de l’engrais !");
        _ADD_(p,"Fertiliser");
    }



    @EventHandler
    public void onPlant(PlayerInteractEvent e) {


        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null && e.getItem().getType() == Material.WHEAT_SEEDS) {
                e.getPlayer().sendMessage("Tu as planté une graine !");
                _ADD_((Player) e,"Planter");
            }
        }
    }

    public void _ADD_(Player player, Block matos) {

        //on va faire un fichier yml qui va Stoker tous les mob du jeux
        File file = new File(main.getDataFolder(), "Agriculture.yml");

        if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdirs();
        }


        if (!file.exists()) {
            main.getLogger().warning("Le fichier Agriculture.yml n'existe pas !");


            try {
                file.createNewFile();
                main.getLogger().info("Fichier Agriculture.yml créé (vide) !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String type = matos.toString();// va juste recupéré le nom du block !

        // Vérifie si le type existe dans le YAML
        if (!config.contains(type)) {
            main.getLogger().warning("Agriculture  inconnu dans le YAML: " + type);
            return;
        }


        //on va recupérer le tyype du mob si c'est un zombie ou un skellete etc..
        ConfigurationSection section = config.getConfigurationSection(type);
        if (section == null) return;

        //on va faire un boucle qui va rrecuperer tous les nom que le Mobs peut avoir
        for (String key : section.getKeys(false)) {



            if (type.equalsIgnoreCase(key)) {


                int exp = section.getInt(key + ".exp");



                connection.Ajouter(player.getUniqueId(), exp, "xp", "Agriculture", player);



                player.sendMessage("§4 la plante casser vous rapporte  " + "§5" + exp);
                player.sendMessage("                 ");
                player.sendMessage(ChatColor.AQUA + "Votre Xp à été mis à jour ! ");



                connection.recupererCompetence(player);


            }

        }


    }


    public void _ADD_(Player player,String type) {

        //on va faire un fichier yml qui va Stoker tous les mob du jeux
        File file = new File(main.getDataFolder(), "Agriculture.yml");

        if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdirs();
        }


        if (!file.exists()) {
            main.getLogger().warning("Le fichier Agriculture.yml n'existe pas !");


            try {
                file.createNewFile();
                main.getLogger().info("Fichier Agriculture.yml créé (vide) !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

       // va juste recupéré le nom du block !

        // Vérifie si le type existe dans le YAML
        if (!config.contains(type)) {
            main.getLogger().warning("Agriculture  inconnu dans le YAML: " + type);
            return;
        }


        //on va recupérer le tyype du mob si c'est un zombie ou un skellete etc..
        ConfigurationSection section = config.getConfigurationSection(type);
        if (section == null) return;

        //on va faire un boucle qui va rrecuperer tous les nom que le Mobs peut avoir
        for (String key : section.getKeys(false)) {



            if (type.equalsIgnoreCase(key)) {


                int exp = section.getInt(key + ".exp");

                player.sendMessage("                                                                                                                      ");
                player.sendMessage("_____________________________");
                player.sendMessage(ChatColor.DARK_AQUA + " Vous avez tuer un(e) " + ChatColor.GOLD + type + ChatColor.DARK_AQUA + " de type " + ChatColor.GOLD + type);
                player.sendMessage("_____________________________");
                player.sendMessage("                                                                                                                      ");


                connection.Ajouter(player.getUniqueId(), exp, "xp", "Agriculture", player);

                if(type.equalsIgnoreCase("Fertiliser")) {
                    player.sendMessage("§4 la plante fertiliser vous rapporte  " + "§5" + exp);
                    player.sendMessage("                 ");
                    player.sendMessage(ChatColor.AQUA + "Votre Xp à été mis à jour ! ");
                }else{

                    player.sendMessage("§4 la plante planter  vous rapporte  " + "§5" + exp);
                    player.sendMessage("                 ");
                    player.sendMessage(ChatColor.AQUA + "Votre Xp à été mis à jour ! ");

                }


                connection.recupererCompetence(player);


            }

        }


    }




}



