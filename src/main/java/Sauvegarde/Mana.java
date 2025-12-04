package Sauvegarde;

import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Mana {


    /**
     * Cette Class s'ocupe du mana de l'ajout de mana en enlever du mana avec la vérification
     * par à port a si le joueur peut utiliser du mana ou pas
     *
     *
     *
     *

    */

    RPGPlugin main;
    Connection connection;
    public Mana(RPGPlugin main){


        this.main = main;
    }

    public double GetMana(Player player){



        return 0.0;
    }

    /*

    méthode pour ajouter du mana add mana

     */
    public void addMana(Player player, double value){

        double Mana = GetMana(player);

        Mana += value;






    }




/*


méthode pour définir le mana set mana
 */


 public void Set_Mana(double value){




 }




/*


méthode pour rémove le mana

 */


    public void removeMana(Player player, double value){

        double Mana = GetMana(player);

        Mana -= value;






    }


    /*


    méthode pour mettre un cooldown de récupération de mana
     */


    public void CooldownMana(Player player, double value){


        Bukkit.getScheduler().runTaskTimer(main, () -> {

                addMana(player, value);

        }, 20, 20);





    }








    /*

    méthode pour avoir le max de mana genre vérifer si le joueur a son mana au max



     */

    public boolean Max_Mana(){

        return false;
    }



    /*


    méthode pour avoir le minimum du mana voir si le joueur à 0 de mana pour pas
     */


    public boolean Min_Mana(){

        return false;
    }


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
