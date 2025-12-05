package fr.rabio.rPGPlugin;

import Commande.Competence;
import Commande.Level;
import Commande.Xp;
import Niveau.Agriculture;
import Niveau.Builder;
import Niveau.Combat;

import Niveau.Minage;
import Sauvegarde.Connection;
import Sauvegarde.Mysql;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public final class RPGPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic

        Connection connection = new Connection(this);
        getServer().getPluginManager().registerEvents(new Connection(this), this);
        getCommand("level").setExecutor(new Level(this));
        getCommand("xp").setExecutor(new Xp(this));
        getCommand("competence").setExecutor(new Competence(this));
        getServer().getPluginManager().registerEvents(new Combat(this,connection), this);
        getServer().getPluginManager().registerEvents(new Minage(this,connection),this);
        getServer().getPluginManager().registerEvents(new Agriculture(this,connection),this);
        getServer().getPluginManager().registerEvents(new Builder(this,connection),this);
















        Mysql.connect();

        PreparedStatement ps = null;
        PreparedStatement psA = null;

        try{
            ps = Mysql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Joueurs (Pseudo VARCHAR(100),UUID VARCHAR(100),PRIMARY KEY(Pseudo))");

            psA = Mysql.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS joueurs_competences (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "uuid VARCHAR(36) NOT NULL, " +
                            "pseudo VARCHAR(16) NOT NULL, " +
                            "competence VARCHAR(32) NOT NULL, " +
                            "level INT DEFAULT 0, " + "xp INT DEFAULT 0," +
                            "UNIQUE (uuid, competence)," +
                            "abilities_unlocked LONGTEXT" +
                            ")"
            );


   

            getLogger().info("YOUPIIIIIIIIIIIIIIIIIIIIIIIII");
            getLogger().info("YOUPIIIIIIIIIIIIIIIIIIIIIIIII");
            getLogger().info("YOUPIIIIIIIIIIIIIIIIIIIIIIIII");
            getLogger().info("YOUPIIIIIIIIIIIIIIIIIIIIIIIII");
            getLogger().info("YOUPIIIIIIIIIIIIIIIIIIIIIIIII");




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            ps.executeUpdate();
            psA.executeUpdate();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
   

        Mysql.disconnect();


    }






}
