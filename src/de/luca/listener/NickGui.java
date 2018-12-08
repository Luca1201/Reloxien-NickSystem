package de.luca.listener;

import de.luca.api.NickAPI;
import de.luca.mysql.MySQL;
import de.luca.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NickGui implements Listener {

    public static Inventory inv = Bukkit.createInventory(null, 5 * 9, "§5NickSystem §8- §cEinstellungen");

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        NickAPI api = new NickAPI();

        try {

            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {

                if (event.getItem().getItemMeta().getDisplayName().contains("§5Nicktool §8| §7Rechtsklick")) {

                    for (int i = 0; i < 10; i++) {
                        inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).build());
                    }
                    inv.setItem(13, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).build());
                    inv.setItem(17, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).build());
                    inv.setItem(18, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).build());
                    inv.setItem(22, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).build());
                    inv.setItem(26, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).build());
                    inv.setItem(27, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).build());
                    inv.setItem(31, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).build());
                    inv.setItem(35, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).build());
                    inv.setItem(36, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).build());
                    inv.setItem(40, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).build());
                    for (int i = 35; i < 45; i++) {
                        inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).build());
                    }

                    inv.setItem(10, new ItemBuilder(Material.NAME_TAG).setDisplayName("§c§lAutoNick").setAmount(1).build());
                    inv.setItem(16, new ItemBuilder(Material.BLAZE_ROD).setDisplayName("§6§lPremium-Nick").setAmount(1).build());
                    inv.setItem(28, new ItemBuilder(Material.PAPER).setDisplayName("§b§lCustom-Nick").setAmount(1).build());
                    inv.setItem(34, new ItemBuilder(Material.ANVIL).setDisplayName("§b§lSet Custom-Nick").setAmount(1).build());

                            if(api.hasAutoNickEnabled(player)) {

                                inv.setItem(11, new ItemBuilder(351, (short) 10).setDisplayName("§aAktiviert").setAmount(1).build());

                            }

                            if(!api.hasAutoNickEnabled(player)) {

                                inv.setItem(11, new ItemBuilder(351, (short) 1).setDisplayName("§cDeaktiviert").setAmount(1).build());

                            }

                            if(!api.hasPremiumNickOn(player)) {

                                inv.setItem(15, new ItemBuilder(351, (short) 1).setDisplayName("§cDeaktivert").setAmount(1).build());

                            }

                            if(api.hasPremiumNickOn(player)) {

                                inv.setItem(15, new ItemBuilder(351, (short) 10).setDisplayName("§aAktiviert").setAmount(1).build());

                            }

                    player.openInventory(inv);

                }
            }

        } catch (Exception e) {

        }
    }

}