package de.luca.commands;

import de.luca.api.NickAPI;
import de.luca.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NickListCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cDu musst ein Spieler sein!");
        } else {

            Player player = (Player) sender;

            if (player.hasPermission("nicksystem.nicklist")) {

                ResultSet rs = MySQL.getResult("SELECT * FROM NickedPlayer");

                NickAPI api = new NickAPI();

                try {

                    if (rs.next()) {

                        player.sendMessage("");
                        player.sendMessage("                §7Genickte Spieler§8:");
                        player.sendMessage("");

                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if(api.isNicked(all)) {
                                player.sendMessage("§8[§5NickSystem§8] §6§l" + api.getRealname(all) + " §7➟§5§l " + api.getNick(all));
                            }
                        }
                        player.sendMessage("");

                    } else {

                        player.sendMessage("§8[§5NickSystem§8] §cEs ist aktuell kein Spieler genickt!");

                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
        return true;

    }

}