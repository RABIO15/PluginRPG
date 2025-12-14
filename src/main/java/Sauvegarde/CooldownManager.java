package Sauvegarde;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {

    private static final HashMap<String, Long> cooldowns = new HashMap<>();


    public static boolean hasCooldown(UUID uuid,String competence) {

        String key = uuid.toString() + competence;
        return cooldowns.containsKey(key);

    }

    public static long getRemaining(UUID uuid,String competence) {

        String key = uuid.toString() + competence;

        long end = cooldowns.get(key);
        return (end - System.currentTimeMillis()) / 1000;
    }

    public static void setCooldown(UUID uuid, int seconds,String competence) {

        String key = uuid.toString() + competence;
        cooldowns.put(key, System.currentTimeMillis() + (seconds * 1000L));

    }

    public static void removeCooldown(UUID uuid,String competence) {
        String key = uuid.toString() + competence;
        cooldowns.remove(key);

    }




    public static void handleCooldown(
            Player player,
            String cooldownName,
            Runnable action
    ) {
        UUID uuid = player.getUniqueId();

        if (CooldownManager.hasCooldown(uuid, cooldownName)) {
            long remaining = CooldownManager.getRemaining(uuid, cooldownName);

            if (remaining > 0) {
                player.sendMessage("§cAttends encore " + remaining + " seconde(s) !");
            } else {
                CooldownManager.removeCooldown(uuid, cooldownName);
                action.run(); // 🔥 action spécifique
            }
        } else {
            action.run(); // 🔥 action spécifique
        }
    }




}
