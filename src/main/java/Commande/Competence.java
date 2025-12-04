package Commande;

import Sauvegarde.Connection;
import Sauvegarde.Json;
import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Competence implements CommandExecutor {

    RPGPlugin main;


    public Competence(RPGPlugin main){

        this.main = main;

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (label.equals("competence")) {

            Connection connection = new Connection(main);

            Json json = new Json(main);

            Player player = (Player) sender;

            player.sendMessage("§2 Execution de la commande Competence en cours !");


/*

01/11/25
j'ai regler le soucis la du fait que on pouvait pas recup j'ai fait un focntrion pour modifier l'état du tru

la ce qu'il faut faire c'est tester le truc si dessous que je vien de faire


 */
            if (args.length >= 3) {


                if (args[2].equalsIgnoreCase("true")) {
                    json.setCompetence_Json(player, args[0].toString(), args[1].toString(), true);


                } else if (args[2].equalsIgnoreCase("false")) {

                    json.setCompetence_Json(player, args[0].toString(), args[1].toString(), false);


                }
            }
            if (args.length >= 2) {

                    player.sendMessage("§8 Recupération en cours.... ");

                    if(args[0].equalsIgnoreCase("ALL") && args[1].equalsIgnoreCase("ALL")){

                        String list[] = {"Agriculture","Combat","Exploration","Global","Minage"};

                        for(String boucle : list){
                            player.sendMessage("§8 COMPETENCE : " + boucle);
                            player.sendMessage("§8________________");
                            player.sendMessage("                  ");

                            json.Get_Competence_Json(player, boucle, args[1].toString());

                            player.sendMessage("                  ");
                            player.sendMessage("§8________________");

                        }



                    }else {


                        json.Get_Competence_Json(player, args[0].toString(), args[1].toString());
                    }
                    player.sendMessage("§2 Recupération Reussi");

                }





        }

        return true;

    }

}
