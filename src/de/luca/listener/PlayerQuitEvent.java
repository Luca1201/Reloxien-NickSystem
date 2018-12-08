package de.luca.listener;

import de.luca.api.NickAPI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;

public class PlayerQuitEvent implements Listener {

    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent event) {

        Player player = event.getPlayer();

        File file = new File("plugins//NickSystem", "config.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        NickAPI api = new NickAPI();

        if(!cfg.getString("Config.Server.Name").equals("Lobby")) {
            api.unnick(player);
        }

    }

}