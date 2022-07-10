package com.bernardo.skygrid;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TpSkyGrid extends BukkitRunnable {
    public final Player player;
    public final Location location;
    public final String worldName;

    public TpSkyGrid(Player play, Location loc, String worldName) {
        this.player = play;
        this.location = loc;
        this.worldName = worldName;
    }

    public void run() {
        this.player.sendMessage(ChatColor.GREEN + "Teletransportando para sua casa em " + this.worldName);
        this.player.teleport(this.location);
    }
}
