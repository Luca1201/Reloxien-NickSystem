package de.luca.commands;

import de.luca.api.NickAPI;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class NickCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        NickAPI api = new NickAPI();
        String nick = api.getRandomNickName();

        if(sender instanceof ConsoleCommandSender) {

            sender.sendMessage("§cDu musst ein Spieler sein!");

        } else {

            Player player = (Player) sender;

            if(player.hasPermission("nicksystem.nick")) {

                if(args.length == 0) {

                    File file = new File("plugins//NickSystem", "config.yml");
                    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

                    if(!cfg.getString("Config.Server.Name").equals("Lobby")) {

                        if (!api.isNicked(player)) {

                            try {

                                api.nick(player, nick);

                                player.setDisplayName(nick);

                                player.sendMessage("§8[§5NickSystem§8] §7Du spielst nun als§8: §6" + api.getNick(player));

                                player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 3);

                            } catch (Exception e) {

                                player.sendMessage("§8[§5NickSystem§8] §cEs ist ein Fehler beim Nicken aufgetreten. Bitte melde dies bei einem Administratoren!");

                            }

                        } else {

                            try {

                                player.setDisplayName(api.getRealname(player));

                                api.unnick(player);

                                player.sendMessage("§8[§5NickSystem§8] §7Du hast dich entnickt!");

                                player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 3);

                            } catch (Exception e) {

                                player.sendMessage("§8[§5NickSystem§8] §cEs ist ein Fehler beim Entnicken aufgetreten. Bitte melde dies bei einem Administratoren!");

                            }

                        }
                    } else {

                        player.sendMessage("§8[§5NickSystem§8] §7Du kannst dich nicht in der Lobby nicken!");

                    }
                }

            }

        }


        return true;

    }

}