package Sauvegarde;

import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Compétence {

    RPGPlugin main;
    Connection connection;
    Json json;



    public Compétence(RPGPlugin main,Connection connection,Json json) {
        this.main = main;
        this.connection = connection;
        this.json = json;


    }



    public void Deblocage_Competence_Special(Player player,String Competence,int level){



        player.sendMessage("§7 Appel de la fonction " );


            //on va faire un fichier yml qui va Stoker tous les mob du jeux
            File file = new File(main.getDataFolder(), "Competence.yml");

            if (!main.getDataFolder().exists()) {
                main.getDataFolder().mkdirs();
            }


            if (!file.exists()) {
                main.getLogger().warning("Le fichier Competence.yml n'existe pas !");


                try {
                    file.createNewFile();
                    main.getLogger().info("Fichier Competence.yml créé (vide) !");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        player.sendMessage("§1 truc vérification de fichier avant config" );

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        player.sendMessage("§2 après config  " );

             // -> "ZOMBIE" ou "SKELETON"

            // Vérifie si le type existe dans le YAML
            if (!config.contains(Competence)) {
                main.getLogger().warning("Type de mob inconnu dans le YAML: " + Competence);
                player.sendMessage("§4 innconnue yaml " );
                return;
            }


            //on va recupérer le tyype du mob si c'est un zombie ou un skellete etc..
        player.sendMessage("§2 avant section   " );
            ConfigurationSection section = config.getConfigurationSection(Competence);
        player.sendMessage("§2 après section avant null  " );
            if (section == null) return;

        player.sendMessage("§2 après null  " );

            /*
            Ici on va faire un boucle qui va récupérer tout les coméptence d'un compétence

            Exemple Combat donc la boucle va récupérer toute les compétence de Combat donc

            Combat:
                Rage_Mode: 2
                Resitor: 5
                vitzpeed: 7




             */
            for (String key : section.getKeys(false)) {

                player.sendMessage("§9 BOUCLE !!!!  " );


                    /*
                     int exp = section.getInt(key);
                     ici la variable exp va recupérer la valeur de la  liste donc on va commencer par rage mode
                     donc elle va recupérer 2 car  Rage_Mode: 2 le numéro correspond à quand on débloque la compétence
                     ici on niveau 2


                     */

                    int exp = section.getInt(key);


                    if(exp == level){
// variable et vérification que on a dejà pas la compétence
                        boolean verif_déjà_aquis = json.Get_Competence_Json(player,Competence,key);

                        if(!verif_déjà_aquis){

                            json.setCompetence_Json(player,Competence,key,true);
                            //on donne la compétence

                            player.sendMessage("§2 Vous avez debloquer la compétence : " + key);

                        }else{
                            player.sendMessage("§4 Vous avez déjà la compétence : " + key);


                        }





                    }else{
                        player.sendMessage("§4 HUM non c'est bon : " + key);


                    }










                }








    }














}
