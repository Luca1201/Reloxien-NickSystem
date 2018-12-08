package de.luca.nicksystem;

import com.mojang.authlib.GameProfile;
import de.luca.api.NickManager;
import de.luca.commands.NickCMD;
import de.luca.commands.NickListCMD;
import de.luca.listener.*;
import de.luca.mysql.MySQL;
import de.luca.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin {

    public static boolean isNickEnabled;
    public static boolean isNickCommandEnabled;

    public static HashMap<String, String> nick = new HashMap<>();

    @Override
    public void onEnable() {

        NickManager.field = NickManager.getField(GameProfile.class, "name");

        getCommand("nick").setExecutor(new NickCMD());
        getCommand("nicklist").setExecutor(new NickListCMD());

        Bukkit.getConsoleSender().sendMessage("§cNickSystem§8: §aEnabled");

        FileManager fileManager = new FileManager();

        fileManager.setConfigStandards();

        fileManager.readConfig();

        MySQL.connect();

        MySQL.createTable();

        Bukkit.getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new NickGui(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickEvent(), this);

    }

    @Override
    public void onDisable() {

    }

}