package Commande;

import Sauvegarde.Connection;
import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Xp implements CommandExecutor {



    RPGPlugin main;


    public Xp(RPGPlugin main){

        this.main = main;

    }





    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {






        if(label.equals("xp")) {
            Connection connection = new Connection(main);

            Player player = (Player) sender;
            if (args.length == 0) {
                // Pas d’argument → afficher XP de Combat par défaut
                String message = "Commande XP ";
               // connection.recuperer(player, "xp", "Combat",false,message);
                connection.Remiseexp(player,"xp","Combat","");
                return true;
            }

            if (args[0].equalsIgnoreCase("retirer")) {


                if (args.length >= 3) {

                    connection.Retirer(player.getUniqueId(), Integer.parseInt(args[1]),"xp",args[2]);
                    player.sendMessage("§6 L'xp  de la compétence : " + args[2] + " §6 à été mis a jour");
                    connection.recupererCompetence(player);

                    return true;



                }else{

                    player.sendMessage("Veuillez mettre une valeur ");


                }


                return true;
            }


            if (args[0].equalsIgnoreCase("ajouter")) {


                if (args.length >= 3) {

                    connection.Ajouter(player.getUniqueId(), Integer.parseInt(args[1]),"xp",args[2],player);

                    player.sendMessage("§6 L'xp  de la compétence : " + args[2] + " §6 à été mis a jour");
                    connection.recupererCompetence(player);
                    return true;



                }else{

                    player.sendMessage("Veuillez mettre une valeur ");


                }


            }if (args[0].equalsIgnoreCase("limite")) {
                if (args.length >= 2) {

                    int limite = connection.Stat_xp_Level(player,Integer.parseInt(args[1]));

                    player.sendMessage("§2 La limite pour le niveau indiquer et " + limite);

                }else{

                    player.sendMessage("§4 PAS ASSEZ DE ARGUMENTS !!!");
                }

            }



            if (args.length == 1) {
                String message = "Commandee Ajouter ";

                try {
                    //connection.recuperer(player, "xp", args[0],false,message);
                    connection.Remiseexp(player,"xp",args[0],"");
                } catch (RuntimeException e) {
                    player.sendMessage("§4 Veuillez mettre un argument VALIDE !");
                }

                return true;

            }




        }




        return true;
    }
}
