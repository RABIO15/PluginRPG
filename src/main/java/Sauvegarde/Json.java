package Sauvegarde;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.rabio.rPGPlugin.RPGPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Json {
    RPGPlugin main;

    int finale = 0;
    int unity;
    int Big_Stokage;

    public static java.sql.Connection con;

    public Json(RPGPlugin main) {
        this.main = main;

    }

    public boolean Get_Competence_Json(Player player, String competence, String ability)
    {


            Connection connection = new Connection(main);

        try {
            PreparedStatement ps = Mysql.getConnection().prepareStatement(
                    "SELECT abilities_unlocked FROM joueurs_competences WHERE uuid = ? AND competence = ?"
            );
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, competence);

            ResultSet resultSet = ps.executeQuery();


            if (resultSet.next()) {
                String jsonString = resultSet.getString("abilities_unlocked");



                if (jsonString == null || jsonString.trim().isEmpty()) {
                    player.sendMessage(" NULL ");
                    connection.createCompetenceIfNotExists(player,competence);


                }




                JsonObject abilities = JsonParser.parseString(jsonString).getAsJsonObject();



                // boolean abilities_modes = abilities.get(ability).getAsBoolean();

                //erreur ici
                //l'erreur est du au fait que le truc doit retourner un truc qui est déjà sino crash
                //donc si par exemple je fait rage_mode ça marchera mais sinon BOUM

                /*

                11/11/25

                truc a faire à cause du bug j'ai supprimer la table mais maintenan bah c'est à cause de çaque ça bug
                donc faut regler ça puiis se truc la si dessu


                 */



                if(ability.equalsIgnoreCase("ALL")){


                    File file = new File(main.getDataFolder(), "Competence.yml");

                    if (!main.getDataFolder().exists()) {
                        player.sendMessage("CREation dossier ");
                        main.getDataFolder().mkdirs();
                    }


                    if (!file.exists()) {
                        main.getLogger().warning("Le fichier Competence.yml n'existe pas !");


                        try {
                            file.createNewFile();
                            main.getLogger().info("Fichier Competence.yml créé (vide) !");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    player.sendMessage("fin verification fichier compétence all");
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);


                    ConfigurationSection section = config.getConfigurationSection(competence);



                    for (String key : section.getKeys(false)) {



                        JsonObject abilitiess = JsonParser.parseString(jsonString).getAsJsonObject();
                        boolean abilities_modess = abilitiess.get(key).getAsBoolean();

                        player.sendMessage("§2 l'ability :  " + key + " est en mode : " + abilities_modess);

                    }











                }





                else {
                    boolean abilities_modes = abilities.get(ability).getAsBoolean();
                    player.sendMessage("§2 l'ability :  " + ability + " est en mode : " + abilities_modes);
                }

                return true;

            } else {
                // 🔍 Aucune ligne trouvée → on la crée
                connection.createCompetenceIfNotExists(player, competence);
                player.sendMessage("on essaye de crée quelque chose ??² ");
            }

            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage("❌ Erreur SQL : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("❌ Erreur JSON : " + e.getMessage());
        }


        return false;
    }


    public void setCompetence_Json(Player player, String competence, String abilityName, boolean newValue) {

        Connection connection = new Connection(main);
        try {
            // 1️⃣ Récupération de la ligne correspondante
            PreparedStatement ps = Mysql.getConnection().prepareStatement(
                    "SELECT abilities_unlocked FROM joueurs_competences WHERE uuid = ? AND competence = ?"
            );
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, competence);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String jsonString = rs.getString("abilities_unlocked");

                if (jsonString == null || jsonString.trim().isEmpty()) {
                    // Si pas de JSON → en créer un propre
                    connection.createCompetenceIfNotExists(player, competence);
                }

                // 2️⃣ Parse du JSON existant
                JsonObject abilities = JsonParser.parseString(jsonString).getAsJsonObject();

                // 3️⃣ Mise à jour ou création de la compétence demandée
                abilities.addProperty(abilityName, newValue);

                // 4️⃣ Sauvegarde dans la base
                PreparedStatement update = Mysql.getConnection().prepareStatement(
                        "UPDATE joueurs_competences SET abilities_unlocked = ? WHERE uuid = ? AND competence = ?"
                );
                update.setString(1, abilities.toString());
                update.setString(2, player.getUniqueId().toString());
                update.setString(3, competence);
                update.executeUpdate();
                update.close();

                player.sendMessage("✅ " + abilityName + " a été mis à jour : " + newValue);

            } else {
                // La compétence n'existe pas encore → on la crée
                connection.createCompetenceIfNotExists(player, competence);
                player.sendMessage("⚙️ Compétence créée, réessaie la commande.");
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage("❌ Erreur SQL : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("❌ Erreur JSON : " + e.getMessage());
        }
    }





}
