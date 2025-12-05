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
Compétence_Niveaux competence;


public Agriculture(RPGPlugin main,Connection connection, Compétence_Niveaux competence){
    this.main = main;
    this.connection = connection;
    this.competence = competence;
    }


    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        Player p = e.getPlayer();

        if (b.getType() == Material.WHEAT) {
            Ageable age = (Ageable) b.getBlockData();
            if (age.getAge() == age.getMaximumAge()) {
                p.sendMessage("Tu as récolté du blé mature !");


                competence._ADD_(p,"Agriculture","Agriculture.yml", b.getType());




            } else {
                p.sendMessage("Le blé n'est pas mûr !");
            }
        }
    }



    @EventHandler
    public void onFertilize(BlockFertilizeEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        p.sendMessage("Tu as utilisé de l’engrais !");
        competence._ADD_(p,"Agriculture","Agriculture.yml", b.getType());
    }



    @EventHandler
    public void onPlant(PlayerInteractEvent e) {


        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null && e.getItem().getType() == Material.WHEAT_SEEDS) {
                e.getPlayer().sendMessage("Tu as planté une graine !");
                competence._ADD_(e.getPlayer(),"Agriculture","Agriculture.yml", Material.WHEAT_SEEDS);
            }
        }
    }





}



