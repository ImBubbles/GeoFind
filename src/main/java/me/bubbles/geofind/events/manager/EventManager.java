package me.bubbles.geofind.events.manager;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.events.Join;
import me.bubbles.geofind.events.Leave;

public class EventManager {
    private GeoFind plugin;

    public EventManager(GeoFind plugin) {
        this.plugin=plugin;
        registerEvents();
    }

    public void addEvent(Event... events) {
        for(Event event : events) {
            plugin.getServer().getPluginManager().registerEvents(event,plugin);
        }
    }

    public void registerEvents() {
        addEvent(new Join(plugin),new Leave(plugin));
    }

}
