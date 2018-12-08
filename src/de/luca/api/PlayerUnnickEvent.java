package de.luca.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerUnnickEvent extends Event {

    public static HandlerList handlers = new HandlerList();
    private Player player;

    public PlayerUnnickEvent(Player player) {

        this.player = player;

    }

    public HandlerList getHandlers() {

        return handlers;

    }

    public static HandlerList getHandlerList() {

        return handlers;

    }

    public Player getPlayer() {

        return this.player;

    }

}