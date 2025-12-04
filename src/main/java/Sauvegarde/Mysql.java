package Sauvegarde;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {



    public static String host = "localhost";
    public static String port = "3306";
    public static String database = "minecraft";
    public static String username = "minecraft";
    //Indentifiant de cette machine c'étéiat root
    public static String password = "K#v8bgh75@Ggv34Xtyh@bbIOL#";// mot de passe du serv
     ///Autre mot de passe de cette machine  //"P6PSgpqxKYPNKK@3os35D5L6XMs";
    public static Connection connection;

    public static ConsoleCommandSender console = Bukkit.getConsoleSender();



    public static void connect() {
        reconnect();
    }

    public static void reconnect() {
        try {
            if (connection != null && !connection.isClosed()) return;

            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true",
                    username,
                    password
            );
            console.sendMessage("[MySQL] Connexion ouverte / reconnectée !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                console.sendMessage("[MySQL] Déconnexion réussie");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnected() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }

    public static Connection getConnection() {
        try {
            if (!isConnected()) {
                reconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }


}
