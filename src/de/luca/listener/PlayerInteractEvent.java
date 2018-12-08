package de.luca.listener;

import de.luca.api.NickAPI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import java.io.File;

public class PlayerInteractEvent implements Listener {

    @EventHandler
    public void onInteract(org.bukkit.event.player.PlayerInteractEvent event) {

        Player player = event.getPlayer();

        NickAPI api = new NickAPI();

        File file = new File("plugins//NickSystem", "config.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if(cfg.getString("Config.Server.Name").equals("Lobby")) {

            try {

                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    if (event.getItem().getItemMeta().getDisplayName().contains("§5Nicktool §8| §7Rechtsklick")) {

                        api.toggleAutoNick(player);

                    }

                }
            } catch (Exception e) {
            }
        }

    }

}