package me.bubbles.geofind.ticker;

import me.bubbles.geofind.GeoFind;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class Ticker {

    private boolean enabled;
    private GeoFind plugin;

    public Ticker(GeoFind plugin) {
        this.plugin=plugin;
    }

    private void Count() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(plugin, () -> {
            if(enabled) {
                plugin.onTick();
                Count();
            }
        }, 1);
    }

    public Ticker toggle() {
        enabled=!enabled;
        if(enabled) {
            Count();
        }
        return this;
    }

    public Ticker setEnabled(boolean bool) {
        enabled=bool;
        if(enabled) {
            Count();
        }
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

}
