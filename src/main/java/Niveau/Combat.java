package Niveau;


import Sauvegarde.Connection;
import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.*;
public class Combat  implements Listener {



    RPGPlugin main;
    Connection connection;
    private final Random random = new Random();

    public Combat(RPGPlugin main,Connection connection){

        this.main = main;
        this.connection = connection;
    }




/*
 *Va vérer le niveau combat  il servira pour envoyer le niveau du joueur verifier
 * quand le joueur recupérer de lxp et va s'occuper de tous ce qui concerne le niveau comba
 * quand un joueur tue un mob quand il augmente de niveau etc..
 *
 *
 *
 *
 */

    @EventHandler
    public void Mob_Kill(EntityDeathEvent event){

        Player player = event.getEntity().getKiller();

        if(player == null)return;


        Entity entity = event.getEntity();
        _ADD_(player,entity);



    }











    public void _ADD_(Player player,Entity entity){

        //on va faire un fichier yml qui va Stoker tous les mob du jeux
        File file = new File(main.getDataFolder(), "NomMob.yml");

        if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdirs();
        }


        if (!file.exists()) {
            main.getLogger().warning("Le fichier NomMob.yml n'existe pas !");



            try {
                file.createNewFile();
                main.getLogger().info("Fichier NomMob.yml créé (vide) !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String type = entity.getType().toString(); // -> "ZOMBIE" ou "SKELETON"

        // Vérifie si le type existe dans le YAML
        if (!config.contains(type)) {
            main.getLogger().warning("Type de mob inconnu dans le YAML: " + type);
            return;
        }


        //on va recupérer le tyype du mob si c'est un zombie ou un skellete etc..
        ConfigurationSection section = config.getConfigurationSection(type);
                if(section == null) return;

        //on va faire un boucle qui va rrecuperer tous les nom que le Mobs peut avoir
        for(String key : section.getKeys(false)){
            String name = entity.getCustomName();

            
            if(name.equalsIgnoreCase(key)){
               

                int exp = section.getInt(key + ".exp");

                player.sendMessage("                                                                                                                      ");
                player.sendMessage("_____________________________");
                player.sendMessage(ChatColor.DARK_AQUA +" Vous avez tuer un(e) " + ChatColor.GOLD + name + ChatColor.DARK_AQUA +" de type " + ChatColor.GOLD + type);
                player.sendMessage("_____________________________");
                player.sendMessage("                                                                                                                      ");


                connection.Ajouter(player.getUniqueId(),exp,"xp","Combat",player);


                player.sendMessage("§4 Le mob tuer à rapporter " + "§5" +exp);
                player.sendMessage("                 ");
                player.sendMessage(ChatColor.AQUA + "Votre Xp à été mis à jour ! ");
                //String message = "Fonction ADD Classe Combat";


                //connection.recupererCompetence(player);

                connection.recuperer_1_Competence(player,"Combat");



            }

        }


        //on va comparer avec ce que le joueur a tuer si le un des nom correspond a un nom qui il y a dans le
        //yml et bien on va recupérer qui associer a ce nom et l'ajouter dans l'exp du joueur





    }
    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        String type = entity.getType().toString(); // ex: ZOMBIE, SKELETON, etc.

        File file = new File(main.getDataFolder(), "NomMob.yml");

        // Crée le fichier et le dossier s’ils n’existent pas
        if (!main.getDataFolder().exists()) main.getDataFolder().mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
                main.getLogger().warning("Fichier NomMob.yml créé, ajoutez-y vos paramètres !");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (!config.contains(type)) return; // Si le mob n’a pas de configuration, on sort.


        Set<String> subKeys = config.getConfigurationSection(type).getKeys(false);
        for (String key : subKeys) {
            String base = type + "." + key;

            int chance = config.getInt(base + ".chance", 0);
            double health = config.getDouble(base + ".health", 20.0);
            double speed = config.getDouble(base + ".speed", 0.25);
            double damage = config.getDouble(base + ".damage", 2.0);

            // Tire une probabilité aléatoire
            if (random.nextInt(100) < chance) {
                applyStats(entity, key, health, speed, damage);
                return; // on en applique qu'un seul
            }
        }
    }

    private void applyStats(LivingEntity entity, String name, double health, double speed, double damage) {
        // Nom et affichage
        entity.setCustomName(name);
        entity.setCustomNameVisible(true);

        // Santé
        if (entity.getAttribute(Attribute.GENERIC_MAX_HEALTH) != null) {
            entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
            entity.setHealth(health);
        }

        // Vitesse
        if (entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED) != null) {
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
        }

        // Dégâts
        if (entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null) {
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
        }


        if (entity.getAttribute(Attribute.GENERIC_ARMOR) != null) {
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
        }





        // Effets visuels et sonores
        entity.getWorld().spawnParticle(Particle.FLAME, entity.getLocation(), 30, 4.5, 3, 4.5, 0.31);
        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0f, 1.4f);
    }
}







