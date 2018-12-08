package de.luca.listener;

import de.luca.api.NickAPI;
import de.luca.mysql.MySQL;
import de.luca.nicksystem.Main;
import de.luca.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.ResultSet;
import java.util.ArrayList;

public class InventoryClickEvent implements Listener {

    private static ArrayList<Player> time = new ArrayList<>();

    @EventHandler
    public void onClick(org.bukkit.event.inventory.InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        NickAPI api = new NickAPI();

        try {

            if (event.getClickedInventory().getTitle().equals("§5NickSystem §8- §cEinstellungen")) {

                if(event.getSlot() == 11) {

                    ResultSet rs = MySQL.getResult("SELECT * FROM NickedPlayer WHERE UUID='" + player.getUniqueId().toString() + "'");

                    player.playSound(player.getLocation(), Sound.CLICK, 2, 3);

                    if(api.hasAutoNickEnabled(player)) {

                        if(time.contains(player)) {
                            player.sendMessage("§8[§5NickSystem§8] §cBitte warte kurz...");
                        } else {

                            time.add(player);

                            NickGui.inv.setItem(11, new ItemBuilder(351, (short) 1).setDisplayName("§cDeaktiviert").setAmount(1).build());

                            api.toggleAutoNick(player);

                        }

                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                            @Override
                            public void run() {

                                time.remove(player);

                            }
                        }, 3*20);

                    }

                    if(!api.hasAutoNickEnabled(player)) {

                        if(time.contains(player)) {
                            player.sendMessage("§8[§5NickSystem§8] §cBitte warte kurz...");
                        } else {

                            NickGui.inv.setItem(11, new ItemBuilder(351, (short) 10).setDisplayName("§aAktiviert").setAmount(1).build());

                            api.toggleAutoNick(player);

                            time.add(player);

                        }

                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                            @Override
                            public void run() {

                                time.remove(player);

                            }
                        }, 3*20);

                    }


                }

                if(event.getSlot() == 15) {

                    if(api.hasPremiumNickOn(player)) {

                        if(time.contains(player)) {

                            player.sendMessage("§8[§5NickSystem§8] §cBitte warte kurz...");

                        } else {

                            Bukkit.broadcastMessage("§aDEBUG");

                            NickGui.inv.setItem(15, new ItemBuilder(351, (short) 1).setDisplayName("§cDeaktivert").setAmount(1).build());
                            player.playSound(player.getLocation(), Sound.CLICK, 2, 3);

                            Bukkit.broadcastMessage("§cDEBUG");

                            time.add(player);

                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                                @Override
                                public void run() {

                                    time.remove(player);

                                }
                            }, 3*20);

                        }



                    }

                    if(!api.hasPremiumNickOn(player)) {

                        if(time.contains(player)) {

                            player.sendMessage("§8[§5NickSystem§8] §cBitte warte kurz...");

                        } else {


                            NickGui.inv.setItem(15, new ItemBuilder(351, (short) 10).setDisplayName("§aAktiviert").setAmount(1).build());
                            player.playSound(player.getLocation(), Sound.CLICK, 2, 3);

                            time.add(player);

                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                                @Override
                                public void run() {

                                    time.remove(player);

                                }
                            }, 3*20);

                        }

                    }

                }

                event.setCancelled(true);

            }
        } catch (Exception e) {

        }
    }

}