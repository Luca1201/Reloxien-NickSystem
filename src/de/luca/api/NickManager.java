package de.luca.api;

import com.mojang.authlib.GameProfile;
import de.luca.mysql.MySQL;
import de.luca.nicksystem.Main;
import de.luca.utils.GameProfileBuilder;
import de.luca.utils.UUIDFetcher;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class NickManager {

    public static Field field;

    public static void sendPacket(Player player, Packet<?> packet) {

        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);

    }

    public static void removeFromTab(CraftPlayer cp) {

        NickAPI api = new NickAPI();

        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, cp.getHandle());

        for(Player all : Bukkit.getOnlinePlayers()) {

            sendPacket(all, packet);

        }

    }

    public static void addToTab(CraftPlayer cp) {

        NickAPI api = new NickAPI();

        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, cp.getHandle());

        for(Player all : Bukkit.getOnlinePlayers()) {

            sendPacket(all, packet);

        }

    }

    public static Field getField(Class<?> clazz, String name) {

        try {

            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;

        } catch (NoSuchFieldException | SecurityException e) {
            return null;
        }

    }

    public static void nick(final Player player, String name) {

        CraftPlayer cp = (CraftPlayer) player;

        NickAPI api = new NickAPI();

        try {

            field.set(cp.getProfile(), name);

        } catch (IllegalArgumentException|IllegalAccessException e) {
            e.printStackTrace();
        }

        removeFromTab(cp);
        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(new int[] { cp.getEntityId()});
        for(Player all : Bukkit.getOnlinePlayers()) {

            if(all != player) {
                sendPacket(all, destroy);
            }

        }

        new BukkitRunnable()
        {
            public void run()
            {
                addToTab(cp);

                PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(cp.getHandle());

                for (Player all : Bukkit.getOnlinePlayers()) {

                    if (all != player) {

                        NickManager.sendPacket(all, spawn);

                    }
                }
            }
        }.runTaskLater(Main.getPlugin(Main.class), 4L);

    }

    public static void unnick(Player player, String uuid) {

        CraftPlayer cp = (CraftPlayer) player;

        NickAPI api = new NickAPI();

        removeFromTab(cp);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
            @Override
            public void run() {

                addToTab(cp);

            }
        }, 4L);

        ResultSet rs = MySQL.getResult("SELECT * FROM NickedPlayer WHERE UUID='" + uuid + "'");

        if(api.isNicked(player)) {

            try {

                if(rs.next()) {

                    String realname = rs.getString("Realname");

                    try {

                        field.set(cp.getProfile(), realname);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

}