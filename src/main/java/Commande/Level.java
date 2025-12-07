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

                try {
                    connection.recuperer_1_Competence(player, args[0]);

                } catch (Exception e) {
                    player.sendMessage("§4 La compétence demander n'est pasd valide ou n'existe pas !");
                }







            }else{


                connection.recupererCompetence(player);
                return true;
            }


        }

        return false;
    }
}
