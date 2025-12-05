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
import java.util.Set;


public class Minage implements Listener {





    Connection connection;
    RPGPlugin main;
    Compétence_Niveaux competence;

    public Minage(RPGPlugin main, Connection connection,Compétence_Niveaux competence){


        this.main = main;
        this.connection = connection;
        this.competence = competence;
    }



    @EventHandler
    public void Minagee(BlockBreakEvent event){

        Player player = event.getPlayer();

        Material type = event.getBlock().getType();

        if(player == null)return;





            player.sendMessage("§2 Vous avez casser de la pierre ");



            competence._ADD_(player,"Minage","Block.yml",type);












    }





    }
