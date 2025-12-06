package Sauvegarde;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.*;


import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

import java.sql.ResultSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

/**
 22/11/25

 ok alorts le tyruc marche il reussi a recupérer les truc que il ya dans le yml par à port au ability voir la fonction create competence if not existe

 et la fonction ajouterJoueur
 parcontre ce qui est bizzard c'est quand on supprime la table ans releoad ou après realod
 enfin y a un bail avec ça qui fait que ça fait une erreur l'erreur vien pas du plugin
 mes a mon avis elle vienn plutot du vien de la sycronisation un truc du genre quand le truc ajoute
 à monn avis quand on supprime et on rl pas bah le truc ce dit que y a desjà des truc alors que non
 ou enversement donc fait gaffe à ça très chiant

 truc à faire pour plus tard
 faire des teste en plus pour voir pourquoi ça fait et demander a chat gpt 

 mettre plus compétence dans le truc combat juste pour tester et après faire pareil pour les autre truc
 c'est a dire les compétence puis vérifier et faire des fonctione pour récupérer les truc pars la suite

 après avoir fait ça il faudra mettre sur git le code pour pas se perdre

 et surtout surtout reograniser le code pour pas se perdre parce que la dans l trcu connection y a trop de fonction
 trop de truc abstrait faudrais juste crée des classe en plus ou reorganiser le truc aussi mettre des commentaire pour ça

 **/


/**
 * UPDATE 23/11/25
 *
 * Bon en gros j'ai reussi a faire un sorte que on puise retourner tout les compétence true false tu connais
 * la ce que je fait c'est répartir les les fonction car trop le bordel ptn donc je fait ça
 *
 * truc a faire pour plus tard
 *
 *
 * continuer de diviser les fonction dans d'autre classe et verifier le bon fonction dejà vérifier si le tru
 *avec json marche qsue j'ai modifier puis aussi vérifier si la fonction public void recuperer sert a quelque chose
 * car j'ai elle retourne directe sur une autre fonction donc j'ai modifie la ou cette focntion a été appeler pour la remplacer
 * par lka fonction que la fonction à appeler verifier si tout marche et après supprimer si vrm inutile
 *
 */





public class Connection implements Listener {
    RPGPlugin main;

    int finale = 0;
    int unity;
    int Big_Stokage;

    public static java.sql.Connection con;

    public Connection(RPGPlugin main) {
        this.main = main;

    }

    @EventHandler
    public void Onjoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String username = player.getDisplayName();
        UUID uuid = player.getUniqueId();
        Logger log = Bukkit.getLogger();


        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            boolean exists = false;

            try {
                PreparedStatement ps = Mysql.getConnection()
                        .prepareStatement("SELECT * FROM Joueurs WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    exists = true;


                } else {
                    PreparedStatement insert = Mysql.getConnection()
                            .prepareStatement("INSERT IGNORE INTO Joueurs (Pseudo,UUID) VALUES (?,?)");
                    insert.setString(1, username);
                    insert.setString(2, uuid.toString());
                    insert.executeUpdate();

                    log.info("AJOUT dans la table de " + username);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            boolean finalExists = exists; // Nécessaire pour l'utiliser dans la lambda suivante

            // → Retour en sync pour interagir avec Minecraft
            Bukkit.getScheduler().runTask(main, () -> {
                if (finalExists) {
                    player.sendMessage("§9 Bon retourrr !!!");
                } else {
                    player.sendMessage("§1 Bienvenue dans le serveur !!!");
                }
            });
        });
        player.sendMessage("BITTE fonction ");
        ajouterJoueur(player.getUniqueId(), player.getName(),player); // ajoute en base
       // recupererCompetence(player);


    }






    public void ajouterJoueur(UUID uuid, String pseudo,Player player ) {




        // → On fait ça en asynchrone pour éviter de freeze le serveur
        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            boolean exists = false;

            try {



                /*

                15/11/25
                Chose que j'ai fait

                il y avait un soucis avec la connexion car le truc n'arrivais pas à ce connecter
                car je sais pas quoi le truc à pas eté connecter depuis longtemps donc avec cette
                ligne de code ça arranger tout
                    "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true",
                 après il y a eu un soucis que le truc ne trouver pas les colone normal quand j'ai fait les trucpour
                 le json j'ai enlever le truc qui fesait en sorte que quand le joueur se co ça verifie si il a bien les colmone sinon les crée
                 en plus a cause de gpt qui a modifier les truc y a plein de truc manquant donc j'ai modifier et je les mis






                Chose à faire plus tard

                Tester si on se connecte au serveur les colonne ce crée et que y a pas de erreur

                si ça reussi faire en sorte que quand on fait un commande spécifique ça retourne toute les compétence


               pour faire ça on pourrait mettre dans un yml tout les compétence et puis faire le truc qui resort true ou false
               avec une boucle qui va récupérer les truc du yml et va les comparer





                 */










    PreparedStatement ps = Mysql.getConnection()
            .prepareStatement("SELECT * FROM joueurs_competences WHERE UUID = ?");
    ps.setString(1, uuid.toString());
    ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    exists = true;



                } else {


                    createCompetenceIfNotExists(player,"Combat");

                    PreparedStatement insert = Mysql.getConnection().prepareStatement(
                            "INSERT INTO joueurs_competences (uuid, pseudo, competence, level,xp) VALUES (?, ?, ?, ?,?) " +
                                    "ON DUPLICATE KEY UPDATE level = VALUES(level), xp = VALUES(xp)"
                    );
                    String list[] = {"Agriculture","Combat","Build","Global","Minage"};

                    for(String i : list) {


                        createCompetenceIfNotExists(player,i);
                       ;

                    }



                }
            } catch (SQLException e) {
                player.sendMessage("BITTE EROR on force la fonction !");



            }

            boolean finalExists = exists; // Nécessaire pour l'utiliser dans la lambda suivante

            // → Retour en sync pour interagir avec Minecraft
            Bukkit.getScheduler().runTask(main, () -> {
                if (finalExists) {
                    player.sendMessage("§2 Bon retour !");
                } else {
                    player.sendMessage("§6 yoo");
                }
            });
        });

    }





    public void recupererCompetence(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            int niveau = 0;
            int xp = 0;
            String competence[] = {"Agriculture","Combat","Build","Global","Minage"};


            for(String i : competence) {
                try {

                    PreparedStatement ps = Mysql.getConnection().prepareStatement(
                            "SELECT level,xp FROM joueurs_competences WHERE uuid = ? AND competence = ?"
                    );
                    ps.setString(1, player.getUniqueId().toString());
                    ps.setString(2, i);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        niveau = rs.getInt("level");
                        xp = rs.getInt("xp");


                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }

                int finallevel = niveau;
                int xpFinal = xp;
                Bukkit.getScheduler().runTask(main, () -> {
                    player.sendMessage("§6 " + i + " → Niveaux: " + finallevel + " | XP: " + xpFinal);
                    player.sendMessage("  ");



                });
            }


        });


    }



    public void recuperer_1_Competence(Player player, String competence) {
        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            int niveau = 0;
            int xp = 0;




                try {

                    PreparedStatement ps = Mysql.getConnection().prepareStatement(
                            "SELECT level,xp FROM joueurs_competences WHERE uuid = ? AND competence = ?"
                    );
                    ps.setString(1, player.getUniqueId().toString());
                    ps.setString(2, competence);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        niveau = rs.getInt("level");
                        xp = rs.getInt("xp");


                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }

                int finallevel = niveau;
                int xpFinal = xp;
                Bukkit.getScheduler().runTask(main, () -> {
                    player.sendMessage("  ");
                    player.sendMessage("§6 " + competence + " → Niveaux: " + finallevel + " | XP: " + xpFinal);
                    player.sendMessage("  ");



                });



        });


    }






    public void Ajouter(UUID uuid, int ajout,String selection, String competence,Player player){



        try {


            PreparedStatement ps_xp = Mysql.getConnection()
                    .prepareStatement("UPDATE joueurs_competences SET " + selection + " = "  + selection + " + ? WHERE UUID = ? AND competence = ?");
            ps_xp.setInt(1, ajout);
            ps_xp.setString(2, uuid.toString());
            ps_xp.setString(3, competence);
            ps_xp.executeUpdate();
            System.out.println("Ajout effectuer");
            String message = "Fonction Ajouter Classe Connection";

             recuperer(player, selection, competence,true,"");


          //  Remiseexp(player,"xp",competence,"");



        } catch (SQLException e) {
            player.sendMessage("ERREUR à la con !");
            throw new RuntimeException(e);
        }



    }


    public void Retirer(UUID uuid, int retire, String selection ,String competence){



        try {
            if (!selection.equalsIgnoreCase("xp") && !selection.equalsIgnoreCase("level")) {
                throw new IllegalArgumentException("Champ invalide : " + selection);
            }

            PreparedStatement ps_xp = Mysql.getConnection().prepareStatement(
                    "UPDATE joueurs_competences SET " + selection + " = " + selection + " - ? WHERE UUID = ? AND competence = ?"
            );
            ps_xp.setInt(1, retire);
            ps_xp.setString(2, uuid.toString());
            ps_xp.setString(3, competence);
            ps_xp.executeUpdate();
            System.out.println("retire effectuer !");

    


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public void recuperer(Player player,String selection,String Competence,boolean xp_niveau,String Message_bug) {
        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            int recuperer = 0;

            try {
                PreparedStatement ps = Mysql.getConnection().prepareStatement(
                        "SELECT " + selection + " FROM joueurs_competences WHERE uuid = ? AND competence = ?"
                );
                ps.setString(1, player.getUniqueId().toString());
                ps.setString(2, Competence);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    recuperer = rs.getInt(selection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            finale  = recuperer;
            Bukkit.getScheduler().runTask(main, () -> {

               // if(finale < 100) {

                    //player.sendMessage("§6Vous avez §e" + finale + "§6 d'xp  !.");

                //}else{

                    Remiseexp(player,"xp",Competence,Message_bug);



               // }





            });
        });






    }

    public void Remiseexp(Player player, String xp, String Competence, String Message_bug) {



        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            int recuperer = 0;
            int stokage = 0;

            try {
                PreparedStatement ps = Mysql.getConnection().prepareStatement(
                        "SELECT " + xp + " FROM joueurs_competences WHERE uuid = ? AND competence = ?"
                );
                ps.setString(1, player.getUniqueId().toString());
                ps.setString(2, Competence);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    recuperer = rs.getInt(xp);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }



            try {
                PreparedStatement ps = Mysql.getConnection().prepareStatement(
                        "SELECT " + "level" + " FROM joueurs_competences WHERE uuid = ? AND competence = ?"
                );
                ps.setString(1, player.getUniqueId().toString());
                ps.setString(2, Competence);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    stokage = rs.getInt("level");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            unity = recuperer;
            Big_Stokage = stokage;

            Bukkit.getScheduler().runTask(main, () -> {

                int Limite_Level = Stat_xp_Level(player, Big_Stokage);

                if(unity >= Limite_Level){


                    player.sendMessage("                         ");

                    player.sendMessage(Message_bug);

                    player.sendMessage("                         ");

                    player.sendMessage(ChatColor.GOLD + "Appel de fonction Remise xp votre xp est de :" + unity);

                    try {

                        //mettre l'exp à une valeur de modifier

  



                        //Ajoute du level
                        PreparedStatement ps_level = Mysql.getConnection()
                                .prepareStatement("UPDATE joueurs_competences SET " + "level" + " = "  + "level" + " + ? WHERE UUID = ? AND competence = ?");
                        ps_level.setInt(1, 1);
                        ps_level.setString(2, player.getUniqueId().toString());
                        ps_level.setString(3, Competence);
                        ps_level.executeUpdate();


   




                        PreparedStatement ps_levelz = Mysql.getConnection()
                                .prepareStatement("UPDATE joueurs_competences SET " + "xp" + " = "  + "xp" + " - ? WHERE UUID = ? AND competence = ?");
                        ps_levelz.setInt(1, Limite_Level);
                        ps_levelz.setString(2, player.getUniqueId().toString());
                        ps_levelz.setString(3, Competence);
                        ps_levelz.executeUpdate();




                        unity -= Limite_Level;

                        player.sendMessage("§9 UNITY apres enlever est : " + unity);



                        if(unity >= Limite_Level){
                            player.sendMessage("§4 On doit repasser un coup  " + unity);
                            Remiseexp(player,"xp",Competence,"C'est la fonction recurisif");

                        }




                    /*
                        if(unity < 100){

                            Ajouter(player.getUniqueId(),unity,"xp",Competence,player);
                            player.sendMessage("§9 JE VIEN E APPELER LA FONCTION AJOUTER " + unity);
                        }

*/


                        System.out.println("LE unity avant est  : " + unity );




//  private void Remiseexp(Player player, String xp, String Competence,String Message_bug) {

                /*

                Mis a jour 24/10/25

                Truc à faire pour la prochaine fois :
                faire en sorte que quand le joueur à plus de 100 xp genre exemple 460
                bah ça enveleve 100 xp et ça lui ajoute un niveaux jusqua qu'il a plus d'exp et garder le
                reste pour lajouter aussi faire un system de niveau par a port à lexp car la on passe au niveau suivant
                quand on a plus de 100 xp mais truc qui serait bien ça serait que quand on est certain niveau ça augmente
                genre

                niveau 1 = 100xp

                niveaux 2 = 125 xp

                niveaux 3 = 175 xp etc.. etc..




                 */




                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                    player.sendMessage("§7 Vous changer de niveaux !");




                }else{
                    player.sendMessage("§6Vous avez §e" + unity + "§6 d'xpouill  !.");




                }




            });
        });





    }



    public int Stat_xp_Level(Player player , int value) {

        int end = 0;
        File file = new File(main.getDataFolder(), "Level.yml");

        // Crée le fichier et le dossier s’ils n’existent pas
        if (!main.getDataFolder().exists()) main.getDataFolder().mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
                main.getLogger().warning("Fichier Level.yml créé, ajoutez-y vos paramètres !");
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String type = "Level"+value;

        if (!config.contains(type)){

            player.sendMessage("§4 Il n'arrive pas à trouver le chemin :( ");
            player.sendMessage("§2 TYPE =  " + type);

            // Si le mob n’a pas de configuration, on sort.
        }

        // Récupère les noms spéciaux configurés pour ce type de mob





        end = config.getInt(type , 0);







        return end;
    }













    public void createCompetenceIfNotExists(Player player, String competence) {


        File file = new File(main.getDataFolder(), "Competence.yml");

        if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdirs();
        }


        if (!file.exists()) {
            main.getLogger().warning("Le fichier Competence.yml n'existe pas !");
            player.sendMessage("Le fichier Competence.yml n'existe pas");


            try {
                file.createNewFile();
                main.getLogger().info("Fichier Competence.yml créé (vide) !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);


        if (!config.contains(competence)) {
            main.getLogger().warning("Competence inconu YAML: " + competence);
            player.sendMessage("Competence inconu YAML: " + competence);
            return;
        }



        ConfigurationSection section = config.getConfigurationSection(competence);

        for (String key : section.getKeys(false)) {


            player.sendMessage("récupération des clée ");


            player.sendMessage("KEY //" + key);

        }




        if (section == null){


            player.sendMessage("LA section est null !" + competence);
            return;
        }


        try {
            PreparedStatement check = Mysql.getConnection().prepareStatement(
                    "SELECT 1 FROM joueurs_competences WHERE UUID=? AND competence=?"
            );
            check.setString(1, player.getUniqueId().toString());
            check.setString(2, competence);
            player.sendMessage("avant verification du rs");

            ResultSet rs = check.executeQuery();
            if (!rs.next()) {
                player.sendMessage("le rs n'existe pas  !");
                // Elle n'existe pas → on la crée





                PreparedStatement insert = Mysql.getConnection().prepareStatement(
                        "INSERT INTO joueurs_competences (UUID,pseudo, competence,level,xp, abilities_unlocked) VALUES (?, ?, ?, ?, ?, ?)"
                );
                insert.setString(1, player.getUniqueId().toString());
                insert.setString(2, player.getDisplayName());
                insert.setString(3, competence);
                insert.setInt(4,0);
                insert.setInt(5,0);

                player.sendMessage("truc apres innsert ");
                // JSON vide ou valeurs de base
                JsonObject baseJson = new JsonObject();

                for (String key : section.getKeys(false)) {


                    baseJson.addProperty(key, false);
                    player.sendMessage("BOUCLE !");

                }


                //baseJson.addProperty("bizz", false);







                insert.setString(6, baseJson.toString());
                insert.executeUpdate();
                player.sendMessage("fin ?!");

                player.sendMessage("✅ Compétence créée pour " + player.getName());
            }else{

                player.sendMessage("Il y a eu un petit soucito");
            }



        } catch (SQLException e) {
            player.sendMessage("il y a eu une erreure dans la fonction create comptence if not existe");
            e.printStackTrace();
        }
    }












}
