package Commande;

import Sauvegarde.Connection;
import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Level implements CommandExecutor {

    RPGPlugin main;


    public Level(RPGPlugin main){

        this.main = main;

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if(label.equals("level")) {
            Connection connection = new Connection(main);

            Player player = (Player) sender;

            if (args.length >= 1) {


                    if (args[0].equalsIgnoreCase("retirer")) {


                        if (args.length >= 3) {

                        connection.Retirer(player.getUniqueId(), Integer.parseInt(args[1]),"level",args[2]);
                        player.sendMessage("§6 Le Niveau  de la compétence : " + args[2] + " §6 à été mis a jour");
                         connection.recupererCompetence(player);
                            return true;

                        }else{

                            player.sendMessage("Veuillez mettre une valeur ");


                        }


                        return true;
                    }

                    if (args[0].equalsIgnoreCase("ajouter")) {


                        if (args.length >= 3) {


                            connection.Ajouter(player.getUniqueId(), Integer.parseInt(args[1]),"level",args[2],player);
                            player.sendMessage("§6 Le Niveau  de la compétence : " + args[2] + " §6 à été mis a jour");
                            connection.recupererCompetence(player);
                            return true;

                        }else{

                            player.sendMessage("Veuillez mettre une valeur ");


                        }


                        return true;
                    }










            }else{


                connection.recupererCompetence(player);
                return true;
            }


        }

        return false;
    }
}
