package Niveau;

import Sauvegarde.Connection;
import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;

public class Compétence_Niveaux implements Listener {


 RPGPlugin main;

 Connection connection;




 public Compétence_Niveaux(RPGPlugin main, Connection connection){
    this.main = main;
    this.connection = connection;




 }




    public void _ADD_(Player player,String competence,String yml, Material matos) {
  ;
        //on va faire un fichier yml qui va Stoker tous les mob du jeux
        File file = new File(main.getDataFolder(), yml);

        if (!main.getDataFolder().exists()) {

            player.sendMessage("Fichier inexistant  ");
            main.getDataFolder().mkdirs();
        }


        if (!file.exists()) {
            main.getLogger().warning("Le fichier "+ yml +" n'existe pas !");
            player.sendMessage("Le fichier "+ yml +" n'existe pas !");

            try {
                file.createNewFile();
                main.getLogger().info("Fichier créé (vide) !");
                player.sendMessage("Fichier  créé (vide) Minage ");
            } catch (IOException e) {

                player.sendMessage("Une erreur c'est produite !! ");
                e.printStackTrace();
            }
        }


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





        //on va faire unE boucle qui va rrecuperer tous les nom que le Mobs peut avoir
        int exp = config.getInt(type);







        connection.Ajouter(player.getUniqueId(), exp, "xp", competence, player);




        player.sendMessage("                 ");
        player.sendMessage(ChatColor.AQUA + "Votre Xp à été mis à jour ! ");



        connection.recuperer_1_Competence(player,competence);


    }











    /*


    il va s'occuper des niveaux


     */









}
