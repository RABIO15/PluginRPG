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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.File;
import java.io.IOException;

public class Builder implements Listener {



    RPGPlugin main;
    Connection connection;
    Compétence_Niveaux competence;


    public Builder(RPGPlugin main,Connection connection, Compétence_Niveaux competence){


       this.main = main;
       this.connection = connection;
       this.competence = competence;



    }


    @EventHandler
    public void onBreak(BlockPlaceEvent e) {
        Block block = e.getBlock();
        Player player = e.getPlayer();



               competence._ADD_(player,"Build","Builder.yml", block.getType());




        }
















}

























