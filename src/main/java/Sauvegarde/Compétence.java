package Sauvegarde;

import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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


                        if(!Check_Competence(key,Competence,player)){

                            json.setCompetence_Json(player,Competence,key,true);
                            //on donne la compétence

                            player.sendMessage("§2 Vous avez debloquer la compétence : " + key);

                            Item_Competence_Give(player,key);




                        }else{
                            player.sendMessage("§4 Vous avez déjà la compétence : " + key);


                        }





                    }else{
                        player.sendMessage("§4 HUM non c'est bon : " + key);


                    }










                }








    }






    public boolean Check_Competence(String key,String Competence,Player player){

        boolean verif_déjà_aquis = json.Get_Competence_Json(player,Competence,key);

            return verif_déjà_aquis;
    }


public void Item_Competence_Give(Player player,String Competence_Unlock){

    switch (Competence_Unlock){

        case "Rage_Mode":

            Create_Object_Give(player,Material.NETHER_STAR,"§9RAGE_MODE");

            break;

        case "Avegmi":

            Create_Object_Give(player, Material.ENDER_EYE,"§6Avegmi");

            break;



        case "Boumbaa":

            Create_Object_Give(player,Material.BRICKS,"§4BOUMBAA_MODE");

            break;






        default:
            player.sendMessage( ChatColor.DARK_AQUA+ "nadale RIEN");
            break;

    }



}

public void Admin_Give_Item(Player player){

    Create_Object_Give(player,Material.NETHER_STAR,"§9RAGE_MODE");
    Create_Object_Give(player, Material.ENDER_EYE,"§6Avegmi");
    Create_Object_Give(player,Material.BRICKS,"§4BOUMBAA_MODE");

    player.sendMessage("§2 Cheat ONLY :)");


}



public void Create_Object_Give(Player player, Material material,String name){

    ItemStack item = new ItemStack(material);
    ItemMeta item_meta = item.getItemMeta();
    item_meta.setDisplayName(name);
    item_meta.addEnchant(Enchantment.AQUA_AFFINITY,1,false);
    item_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

    item.setItemMeta(item_meta);
if(!inventaire_full(player)) {
    player.sendMessage("§2 youpiii ! ");
    player.getInventory().addItem(item);
}else{

    player.sendMessage("§5 Item jeter car inventaire full");
    player.getWorld().dropItemNaturally(player.getLocation(), item);
}



}








    public boolean inventaire_full(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }

}


