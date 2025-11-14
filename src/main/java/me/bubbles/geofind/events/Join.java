package me.bubbles.geofind.events;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.events.manager.Event;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join extends Event {

    public Join(GeoFind plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(plugin.getUserManager().getUser(player)==null) {
            plugin.getUserManager().addPlayer(player);
        }
    }

}
