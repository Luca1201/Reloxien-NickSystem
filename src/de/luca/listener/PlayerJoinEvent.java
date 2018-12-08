package de.luca.listener;

import de.luca.api.NickAPI;
import de.luca.mysql.MySQL;
import de.luca.nicksystem.Main;
import de.luca.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {

        NickAPI api = new NickAPI();

        Player player = event.getPlayer();
        String nick = api.getRandomNickName();

        File file = new File("plugins//NickSystem", "config.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if(!cfg.getString("Config.Server.Name").equals("Lobby")) {

            if (api.hasAutoNickEnabled(player)) {

                api.nick(player, nick);

                player.sendMessage("§8[§5NickSystem§8] §7Du spielst nun als§8: §6" + api.getNick(player));

            }
        } else {

            int slot = cfg.getInt("Config.Nicktool.Slot");

            if(!api.hasAutoNickEnabled(player)) {

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                    @Override
                    public void run() {

                        player.getInventory().setItem(slot, new ItemBuilder(Material.NAME_TAG).setDisplayName("§5Nicktool §8| §7Rechtsklick §8[§4§l✖§8]").build());

                    }
                }, 20*1);



            } else {

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                    @Override
                    public void run() {

                        player.getInventory().setItem(slot, new ItemBuilder(Material.NAME_TAG).setDisplayName("§5Nicktool §8| §7Rechtsklick §8[§a§l✔§8]").addEnchantment(Enchantment.ARROW_DAMAGE, 2).HideFlags().build());

                    }
                }, 20*1);

            }

        }
    }
}