package me.bubbles.geofind.events;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.events.manager.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join extends Event {

    public Join(GeoFind plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        plugin.getUserManager().addPlayer(e.getPlayer());
    }

}
