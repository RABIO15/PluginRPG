package Commande;

import Sauvegarde.*;
import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommande implements CommandExecutor {

    RPGPlugin main;


    public AdminCommande(RPGPlugin main){

        this.main = main;

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (player.isOp()){

            if (label.equals("allcompetence")) {

                Connection connection = new Connection(main);

                Json json = new Json(main);
                Compétence compétence = new Compétence(main,connection,json);


                compétence.Admin_Give_Item(player);
                return true;

            }




            if (label.equals("mana")) {

                Connection connection = new Connection(main);

                Json json = new Json(main);
                Compétence compétence = new Compétence(main,connection,json);


                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("set")) {


                        try {


                            Mana mana = new Mana(main);

                            mana.Set_Mana( Double.parseDouble(args[1]),player.getUniqueId());

                        } catch (RuntimeException e) {
                            player.sendMessage("§c il s'est produit un soucis par a port au Mana");
                            player.sendMessage("§c le args utiliser est " + args[1]);

                        }


                    }

                }

                return true;

            }










            if (label.equals("annulation")) {




                if (args.length >= 1) {
                    try {
                        CooldownManager.removeCooldown(player.getUniqueId(), args[0]);
                    } catch (RuntimeException e) {
                        player.sendMessage("§c il s'est produit un soucis par a port à l'annulation");
                        player.sendMessage("§c le args utiliser est " +  args[0]);

                    }

                }else{

                    player.sendMessage("§c Veuillez mettre la conpétence que vous voulez annulez !" );

                }


                return true;

            }


            if (label.equals("opcompetence")) {

                Connection connection = new Connection(main);

                Json json = new Json(main);


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

            }



            if(label.equals("oplevel")) {
                Connection connection = new Connection(main);



                if (args.length >= 1) {


                    if (args[0].equalsIgnoreCase("retirer")) {


                        if (args.length >= 3) {

                            connection.Retirer(player.getUniqueId(), Integer.parseInt(args[1]), "level", args[2]);
                            player.sendMessage("§6 Le Niveau  de la compétence : " + args[2] + " §6 à été mis a jour");
                            connection.recupererCompetence(player);
                            return true;

                        } else {

                            player.sendMessage("Veuillez mettre une valeur ");


                        }


                        return true;
                    }

                    if (args[0].equalsIgnoreCase("ajouter")) {


                        if (args.length >= 3) {


                            connection.Ajouter(player.getUniqueId(), Integer.parseInt(args[1]), "level", args[2], player);
                            player.sendMessage("§6 Le Niveau  de la compétence : " + args[2] + " §6 à été mis a jour");
                            connection.recupererCompetence(player);
                            return true;

                        } else {

                            player.sendMessage("Veuillez mettre une valeur ");


                        }


                        return true;
                    }


                }





                }



            if(label.equals("opxp")) {
                Connection connection = new Connection(main);


                if (args.length == 0) {
                    // Pas d’argument → afficher XP de Combat par défaut
                    player.sendMessage(ChatColor.RED + "Veuillez mettre une valeur  retirer ou ajouter ?");


                    return true;
                }

                if (args[0].equalsIgnoreCase("retirer")) {


                    if (args.length >= 3) {

                        connection.Retirer(player.getUniqueId(), Integer.parseInt(args[1]), "xp", args[2]);
                        player.sendMessage("§6 L'xp  de la compétence : " + args[2] + " §6 à été mis a jour");
                        connection.recupererCompetence(player);

                        return true;


                    } else {

                        player.sendMessage("Veuillez mettre une valeur ");


                    }


                    return true;
                }


                if (args[0].equalsIgnoreCase("ajouter")) {


                    if (args.length >= 3) {

                        connection.Ajouter(player.getUniqueId(), Integer.parseInt(args[1]), "xp", args[2], player);

                        player.sendMessage("§6 L'xp  de la compétence : " + args[2] + " §6 à été mis a jour");
                        connection.recupererCompetence(player);
                        return true;


                    } else {

                        player.sendMessage("Veuillez mettre une valeur ");


                    }


                }
            }

    }
        return true;
    }

}
