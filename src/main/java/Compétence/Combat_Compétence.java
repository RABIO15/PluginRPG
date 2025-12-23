package Compétence;

import Sauvegarde.*;
import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class Combat_Compétence implements Listener {


    RPGPlugin main;
    Connection connection;



    public Combat_Compétence(RPGPlugin main, Connection connection) {

        this.main = main;  
        this.connection = connection;


    }

    @EventHandler
    public void Rage_modes(PlayerInteractEvent event) {


        Player player = event.getPlayer();


        Constructor_Compétence constructorCompétence = new Constructor_Compétence(main,connection);

        if(constructorCompétence.constructor_start_RIGHT(event,"§9RAGE_MODE","Rage_modes","Combat",Material.NETHER_STAR)) {
            CooldownManager.handleCooldown(player, "Rage_modes", () -> {
                RAGEMODE_EFFECT(player);
            });

            Mana mana = new Mana(main);

           mana.cooldownMana(player);
           player.sendMessage("§2 votre manna est de :  " + mana.GetMana(player));



        }




    }












    @EventHandler
    public void Avegmi_modes(PlayerInteractEvent event) {

        Player player = event.getPlayer();






        Constructor_Compétence constructorCompétence = new Constructor_Compétence(main,connection);
        if(constructorCompétence.constructor_start_LEFT(event,"§6Avegmi","Avegmi","Combat", Material.ENDER_EYE)) {
            event.setUseItemInHand(Event.Result.DENY);
            CooldownManager.handleCooldown(player, "Avegmi", () -> {
                AVEUGMI(player);
            });

        }
    }














    @EventHandler
    public void Boumbaa_modes(PlayerInteractEvent event) {


        Player player = event.getPlayer();



        Constructor_Compétence constructorCompétence = new Constructor_Compétence(main,connection);
        if(constructorCompétence.constructor_start_LEFT(event,"§4BOUMBAA_MODE","Boumbaa","Combat", Material.BRICKS)) {


            CooldownManager.handleCooldown(player, "Boumbaa", () -> {
                BOUMBOUM_EXECUTE_PLAYER(player);
            });




        }

    }


    public void BOUMBOUM_EXECUTE_PLAYER(Player player) {


        player.sendMessage("§4 BOUM BOUM");

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_FLUTE, 4.0f, 3.5f);
        for (int i = 0; i < 25; i += 2) {
            Location loc = player.getLocation().add(
                    player.getLocation().getDirection().normalize().multiply(i)
            );

            World world = player.getWorld();

            TNTPrimed tnt = (TNTPrimed) world.spawnEntity(loc, EntityType.TNT);

            // Explosion très puissante
            tnt.setFuseTicks(20);   // 4 secondes
            tnt.setYield(10f);      // explosion énorme
            tnt.setRotation(4.3f, 5.9f);
            tnt.setIsIncendiary(true);
            CooldownManager.setCooldown(player.getUniqueId(), 20,"Boumbaa");


        }


    }


    public void RAGEMODE_EFFECT(Player player){

        player.sendMessage("§4 RAGE MODE ACTIVATE");
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 20 * 30, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 20 * 15, 0));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 3.0f, 0.5f);

        CooldownManager.setCooldown(player.getUniqueId(), 15,"Rage_modes");

    }



    public void AVEUGMI(Player player){



        player.sendMessage("§4 Avegmi ACTIVATE");
        CooldownManager.setCooldown(player.getUniqueId(), 200,"Avegmi");

        player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 2.0f, 2.5f);

        double radius = 35.0;

        // Chercher les joueurs dans le rayon
        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (entity instanceof Player target) {

                player.sendMessage("§2 Tu as trouvé un joueur à moins de 10 blocs : " + target.getName());

                // Exemple d'effet
                target.sendMessage("§2 chaud chaud chocolat ");
                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15, 0));


            }


        }

    }






}