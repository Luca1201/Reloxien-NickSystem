package de.luca.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerNickEvent extends Event {

    public static HandlerList handlerList = new HandlerList();
    private Player player;
    private String nickname;

    public PlayerNickEvent(Player player, String nickname) {

        this.player = player;
        this.nickname = nickname;

    }

    public HandlerList getHandlers() {

        return handlerList;

    }

    public static HandlerList getHandlerList() {

        return handlerList;

    }

    public Player getPlayer() {

        return this.player;

    }

    public String getNickname() {

        return this.nickname;

    }

}