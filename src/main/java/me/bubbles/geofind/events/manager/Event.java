package me.bubbles.geofind.events.manager;

import me.bubbles.geofind.GeoFind;
import org.bukkit.event.Listener;

public class Event implements Listener {

    public GeoFind plugin;

    public Event(GeoFind plugin) {
        this.plugin=plugin;
    }

}
