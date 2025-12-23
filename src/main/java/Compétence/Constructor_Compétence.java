package Compétence;

import Sauvegarde.Compétence;
import Sauvegarde.Connection;
import Sauvegarde.Json;
import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Constructor_Compétence implements Listener {

    RPGPlugin main;
    Connection connection;



    public Constructor_Compétence(RPGPlugin main, Connection connection) {

        this.main = main;
        this.connection = connection;


    }

    @EventHandler
    public boolean constructor_start_RIGHT(PlayerInteractEvent event,String name_item,String Competence_item_name,String competence_player, Material material){

        Json json = new Json(main);
        Player player = event.getPlayer();

        Compétence compétence = new Compétence(main, connection, json);

            Action action = event.getAction();

        if (event.getItem() != null && event.getItem().getType() == material) {


            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(name_item)) {

                if (event.getItem().getItemMeta().hasEnchants()) {

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            event.setCancelled(true);
            player.sendMessage("§4 Click gauche interdit !");

            return false;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {


                        if (compétence.Check_Competence(Competence_item_name, competence_player, event.getPlayer())) {

                            player.sendMessage(ChatColor.AQUA + "Ok c'est bon !");
                            return true;
                        }

                    }

                }

            }
        }

                    return false;
    }


    @EventHandler
    public boolean constructor_start_LEFT(PlayerInteractEvent event,String name_item,String Competence_item_name,String competence_player, Material material){

        Json json = new Json(main);
        Player player = event.getPlayer();

        Compétence compétence = new Compétence(main, connection, json);

        Action action = event.getAction();

        if (event.getItem() != null && event.getItem().getType() == material) {


            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(name_item)) {

                if (event.getItem().getItemMeta().hasEnchants()) {

                    if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                        event.setCancelled(true);
                        player.sendMessage("§4 Click droit interdit !");

                        return false;
                    }

                    if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {


                        if (compétence.Check_Competence(Competence_item_name, competence_player, event.getPlayer())) {

                            player.sendMessage(ChatColor.AQUA + "Ok c'est bon !");
                            return true;
                        }

                    }

                }

            }
        }

        return false;
    }





}
