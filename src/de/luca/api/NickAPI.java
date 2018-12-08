package de.luca.api;

import de.luca.mysql.MySQL;
import de.luca.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class NickAPI {

    public String getRandomNickName() {

        File file = new File("plugins//NickSystem", "config.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        List<String> nicklist = cfg.getStringList("Config.NickList");

        return nicklist.get(new Random().nextInt(nicklist.size()));

    }

    public void nick(Player player, String nick) {

        if(!isNicked(player)) {

            MySQL.update("INSERT INTO NickedPlayer (UUID, Realname, NickName) VALUES ('" + player.getUniqueId().toString() + "', '" + player.getName() + "', '" + nick + "')");

            NickManager.nick(player, nick);

        }

    }

    public void unnick(Player player) {

        if(isNicked(player)) {

            NickManager.unnick(player, player.getUniqueId().toString());

            MySQL.update("DELETE FROM NickedPlayer WHERE UUID='" + player.getUniqueId() + "'");

        }

    }

    public String getNick(Player player) {

        ResultSet rs = MySQL.getResult("SELECT * FROM NickedPlayer WHERE UUID='" + player.getUniqueId().toString() + "'");

        try {

            if(rs.next()) {
                return rs.getString("NickName");
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }

    public boolean isNicked(Player player) {

        ResultSet rs = MySQL.getResult("SELECT * FROM NickedPlayer WHERE UUID='" + player.getUniqueId().toString() + "'");

        try {

            if(rs.next()) {

                return rs != null;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public int toggleAutoNick(Player player) {

        if(hasAutoNickEnabled(player)) {

            MySQL.update("DELETE FROM AutoNick WHERE UUID='" + player.getUniqueId().toString() + "'");

            File file = new File("plugins//NickSystem", "config.yml");
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

            int slot = cfg.getInt("Config.Nicktool.Slot");

            player.getInventory().setItem(slot, new ItemBuilder(Material.NAME_TAG).setDisplayName("§5Nicktool §8| §7Rechtsklick §8[§4§l✖§8]").build());

            player.sendMessage("§8[§5NickSystem§8] §7Du hast das §5Nicktool §cdeaktiviert§8.");

            player.playSound(player.getLocation(), Sound.CLICK, 2, 3);

            return 0;

        }

        MySQL.update("INSERT INTO AutoNick (UUID) VALUES ('" + player.getUniqueId().toString() + "')");

        File file = new File("plugins//NickSystem", "config.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        int slot = cfg.getInt("Config.Nicktool.Slot");

        player.getInventory().setItem(slot, new ItemBuilder(Material.NAME_TAG).setDisplayName("§5Nicktool §8| §7Rechtsklick §8[§a§l✔§8]").addEnchantment(Enchantment.ARROW_DAMAGE, 2).HideFlags().build());

        player.sendMessage("§8[§5NickSystem§8] §7Du hast das §5Nicktool §aaktiviert§8.");

        player.playSound(player.getLocation(), Sound.CLICK, 2, 3);

        return 1;

    }

    public int togglePremiumNick(Player player) {

        if(hasPremiumNickOn(player)) {

            MySQL.update("DELETE FROM PremiumNick WHERE UUID='" + player.getUniqueId().toString() + "'");

            return 0;

        }

        MySQL.update("INSERT INTO PremiumNick (UUID) VALUES ('" + player.getUniqueId().toString() + "')");

        return 1;

    }

    public boolean hasPremiumNickOn(Player player) {

        ResultSet rs = MySQL.getResult("SELECT * FROM PremiumNick WHERE UUID='" + player.getUniqueId().toString() + "'");

        try {

            if(rs.next()) {

                return rs != null;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean hasAutoNickEnabled(Player player) {

        ResultSet rs = MySQL.getResult("SELECT * FROM AutoNick WHERE UUID='" + player.getUniqueId().toString() + "'");

        try {

            if(rs.next()) {

                return rs != null;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public String getRealname(Player player) {

        ResultSet rs = MySQL.getResult("SELECT * FROM NickedPlayer WHERE UUID='" + player.getUniqueId().toString() + "'");

        try {

            if(rs.next()) {

                return rs.getString("Realname");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}