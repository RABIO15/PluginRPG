package Compétence;

import Sauvegarde.Compétence;
import Sauvegarde.Connection;
import Sauvegarde.Json;
import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Combat_Compétence implements Listener {


    RPGPlugin main;
    Connection connection;


    public Combat_Compétence(RPGPlugin main,Connection connection) {

        this.main = main;
        this.connection = connection;


    }

    @EventHandler
    public void Rage_modes(PlayerInteractEvent event) {

  Json json = new Json(main);
        Player player = event.getPlayer();
        Compétence compétence = new Compétence(main, connection, json);

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getType() == Material.NETHER_STAR) {


                if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9RAGE_MODE")) {

                    if (event.getItem().getItemMeta().hasEnchants()) {


                        if (compétence.Check_Competence("Rage_modes", "Combat", event.getPlayer())) {
                            player.sendMessage("§4 RAGE MODE ACTIVATE");
                            player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 20 * 30, 1));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 20 * 15, 0));
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 3.0f, 0.5f);


                        }


                    }


                }


            }


        }

    }

    @EventHandler
    public void Avegmi_modes(PlayerInteractEvent event) {


        Player player = event.getPlayer();
        Json json = new Json(main);
        Compétence compétence = new Compétence(main, connection, json);

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getType() == Material.ENDER_EYE) {


                if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Avegmi")) {

                    if (event.getItem().getItemMeta().hasEnchants()) {


                        if (compétence.Check_Competence("Avegmi", "Combat", event.getPlayer())) {
                            player.sendMessage("§4 Avegmi ACTIVATE");

                            player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 2.0f, 2.5f);

                            double radius = 10.0;

                            // Chercher les joueurs dans le rayon
                            for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                                if (entity instanceof Player target) {

                                    player.sendMessage("Tu as trouvé un joueur à moins de 10 blocs : " + target.getName());

                                    // Exemple d'effet
                                    target.sendMessage("YOU ARE DEAD ");
                                    target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15, 0));


                                }


                            }


                        }


                    }


                }

            }

        }

    }





    @EventHandler
    public void Boumbaa_modes(PlayerInteractEvent event) {


        Player player = event.getPlayer();
        Json json = new Json(main);
        Compétence compétence = new Compétence(main, connection, json);

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getType() == Material.BRICKS) {


                if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4BOUMBAA_MODE")) {

                    if (event.getItem().getItemMeta().hasEnchants()) {


                        if (compétence.Check_Competence("Boumbaa", "Combat", event.getPlayer())) {
                            player.sendMessage("§4 BOUM BOUM");

                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_FLUTE, 4.0f, 3.5f);
                        for(int i = 0;  i < 25; i+=2) {
                            Location loc = player.getLocation().add(
                                    player.getLocation().getDirection().normalize().multiply(i)
                            );

                            World world = player.getWorld();

                            TNTPrimed tnt = (TNTPrimed) world.spawnEntity(loc, EntityType.TNT);

                            // Explosion très puissante
                            tnt.setFuseTicks(50);   // 4 secondes
                            tnt.setYield(22f);      // explosion énorme
                            tnt.setRotation(2.3f,5.9f);
                            tnt.setIsIncendiary(true);


                            // met le feu


                        }


                        }else{


                            player.sendMessage(ChatColor.RED +"Vous ne pouvez pas activer cette compétence car pas debloquer compétence :(");


                        }


                    }


                }


            }


        }

    }
















}
