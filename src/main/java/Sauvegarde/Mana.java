package Sauvegarde;

import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Mana {


    /**
     * Cette Class s'ocupe du mana de l'ajout de mana en enlever du mana avec la vérification
     * par à port a si le joueur peut utiliser du mana ou pas
     */

    RPGPlugin main;
    Connection connection;
    double finalmana;
    double a;
    double niveau_mana_player;

    public Mana(RPGPlugin main) {


        this.main = main;
    }

    public double GetMana(Player player) {


        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            double mana = 0.0;

            try {

                PreparedStatement ps = Mysql.getConnection().prepareStatement(
                        "SELECT Mana FROM joueurs_competences WHERE uuid = ? "
                );
                ps.setString(1, player.getUniqueId().toString());


                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    mana = rs.getDouble("Mana");


                }


            } catch (SQLException e) {
                e.printStackTrace();
            }

            finalmana = mana;

            Bukkit.getScheduler().runTask(main, () -> {
                player.sendMessage("§6 " + " votre mana est de " + finalmana);
                player.sendMessage("  ");


            });


        });


        return finalmana;

    }

    /*

    méthode pour ajouter du mana add mana

     */
    public void addMana(Player player, double value) {

        double Mana = GetMana(player);

        Mana += value;


        Set_Mana(Mana, player.getUniqueId());


    }




/*


méthode pour définir le mana set mana
 */


    public void Set_Mana(double value, UUID uuid) {

        try {
            PreparedStatement ps_xp = Mysql.getConnection()
                    .prepareStatement("UPDATE joueurs_competences SET " + "Mana" + " = " + "Mana" + " = ? WHERE UUID = ? ");
            ps_xp.setDouble(1, value);
            ps_xp.setString(2, uuid.toString());

            ps_xp.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }




/*


méthode pour rémove le mana

 */


    public void removeMana(Player player, double value) {

        double Mana = GetMana(player);

        Mana -= value;


        Set_Mana(Mana, player.getUniqueId());


    }


    /*


    méthode pour mettre un cooldown de récupération de mana
     */


    public void cooldownMana(Player player) {


        a = 300.0 / GetMana(player); // secondes


        if (a <= 0.0) {

            a = 300.0;

        }


        BukkitTask task = Bukkit.getScheduler().runTaskTimer(main, new Runnable() {

            // secondes

            double timeLeft = a;


            @Override
            public void run() {

                if (timeLeft <= 0.0 || !player.isOnline()) {
                    Bukkit.getScheduler().cancelTask(this.hashCode());
                    return;
                }

                addMana(player, 0.5);
                timeLeft--;
            }

        }, 0, 20);
    }


    public boolean Max_Mana(UUID uuid, Player player,int value) {


        double  niveau_mana_max_player = 0.0;
        File file = new File(main.getDataFolder(), "ManaLevel.yml");

        // Crée le fichier et le dossier s’ils n’existent pas
        if (!main.getDataFolder().exists()) main.getDataFolder().mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
                main.getLogger().warning("Fichier ManaLevel.yml créé, ajoutez-y vos paramètres !");
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String type = "Level" + value;
        //le value sera le niveau de mana que le joueur aura  genre par exemple niveaux 4

        if (!config.contains(type)) {

            player.sendMessage("§4 Il n'arrive pas à trouver le chemin :( ");
            player.sendMessage("§2 TYPE =  " + type);

            // Si le mob n’a pas de configuration, on sort.
        }




        niveau_mana_max_player = config.getDouble(type, 0.0);


        double finalNiveau_mana_max_player = niveau_mana_max_player;
        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {






            try {

                PreparedStatement ps = Mysql.getConnection().prepareStatement(
                        "SELECT level,xp FROM joueurs_competences WHERE uuid = ?"
                );
                ps.setString(1, player.getUniqueId().toString());


                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    niveau_mana_player = rs.getDouble("Mana");



                }


            } catch (SQLException e) {
                e.printStackTrace();
            }


            double finalNiveau_mana_player = niveau_mana_player;



                if(finalNiveau_mana_player >= finalNiveau_mana_max_player){




                }







        });



        if(niveau_mana_player >= niveau_mana_max_player){


            return true;
        }


return false;

    }






    /*

    méthode pour avoir le max de mana genre vérifer si le joueur a son mana au max



     */


    /*


    méthode de vérification avec un valeur donnée par exemple 5 en appelant la méthode getMana on va compérer
    avec ce qu'il y a en paramétre et voir si y a assez
     */
    public boolean Verification_Mana(Player player, double value){

        double Mana = GetMana(player);

        Mana -= value;

        if(Mana <= 0){

            return  false;


        }else{

            return true;
        }




    }




}
