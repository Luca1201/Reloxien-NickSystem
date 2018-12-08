package de.luca.utils;

import de.luca.mysql.MySQL;
import de.luca.nicksystem.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static File getFile() {

        return new File("plugins//NickSystem", "config.yml");

    }

    private static YamlConfiguration getConfiguration() {

        return YamlConfiguration.loadConfiguration(getFile());

    }

    public void setConfigStandards() {

        FileConfiguration cfg = getConfiguration();

        cfg.options().copyDefaults(true);

        cfg.addDefault("Config.NickEnabled", Boolean.valueOf(true));
        cfg.addDefault("Config.NickCommandEnabled", Boolean.valueOf(true));

        cfg.addDefault("Config.MySQL.host", "localhost");
        cfg.addDefault("Config.MySQL.port", "3306");
        cfg.addDefault("Config.MySQL.database", "nicksystem");
        cfg.addDefault("Config.MySQL.username", "username");
        cfg.addDefault("Config.MySQL.password", "passwort");

        cfg.addDefault("Config.Server.Name", "Servername");

        cfg.addDefault("Config.Nicktool.Slot", Integer.valueOf(4));

        List<String> l = new ArrayList<>();

        l.add("Lvcki_Luca");
        l.add("GommeHD");
        l.add("Paluten");
        l.add("GermanLetsPlay");
        l.add("OfficialCodex");

        cfg.addDefault("Config.NickList", l);

        try {

            cfg.save(getFile());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readConfig() {

        YamlConfiguration cfg = getConfiguration();

        Main.isNickCommandEnabled = cfg.getBoolean("Config.NickCommandEnabled");
        Main.isNickEnabled = cfg.getBoolean("Config.NickEnabled");

        MySQL.host = cfg.getString("Config.MySQL.host");
        MySQL.port = cfg.getString("Config.MySQL.port");
        MySQL.database = cfg.getString("Config.MySQL.database");
        MySQL.username = cfg.getString("Config.MySQL.username");
        MySQL.password = cfg.getString("Config.MySQL.password");

    }
}