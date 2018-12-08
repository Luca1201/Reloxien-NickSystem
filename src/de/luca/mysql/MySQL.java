package de.luca.mysql;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    public static String host;
    public static String port;
    public static String database;
    public static String username;
    public static String password;
    public static Connection con;

    public static void connect() {

        if(!isConnected()) {

            try {

                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", username, password);

                Bukkit.getConsoleSender().sendMessage("§cMySQL §8--> §aVerbunden!");

            } catch (SQLException e) {

                e.printStackTrace();

                Bukkit.getConsoleSender().sendMessage("§cMySQL §8--> §cFehler!");

            }

        }

    }

    public static void disconnect() {

        if(isConnected()) {

            try {

                Bukkit.getConsoleSender().sendMessage("§cMySQL §8--> §cVerbindung getrent!");

                con.close();

            } catch (SQLException e) {

                Bukkit.getConsoleSender().sendMessage("§cMySQL §8--> §cVerbindung konnte nicht geschlossen werden!");

            }

        }

    }

    public static boolean isConnected() {

        return con != null;

    }

    public static void reconnect() {

        if(!isConnected()) {

            connect();

        } else {
            return;
        }

    }

    public static void createTable() {

        try {

            con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS AutoNick (UUID VARCHAR (100))");
            con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS NickedPlayer (UUID VARCHAR (100), Realname VARCHAR (100), NickName VARCHAR (100))");
            con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS PremiumNick(UUID VARCHAR (100))");

        } catch (SQLException e) {

            Bukkit.getConsoleSender().sendMessage("§cMySQL §8--> §cFehler!");

            e.printStackTrace();
        }

    }

    public static void update(String qry) {

        try {

            con.createStatement().executeUpdate(qry);

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public static ResultSet getResult(String qry) {

        try {
            return con.createStatement().executeQuery(qry);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

}