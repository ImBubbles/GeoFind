package me.bubbles.geofind.events;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.events.manager.Event;
import me.bubbles.geofind.requests.Request;
import me.bubbles.geofind.users.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave extends Event {

    public Leave(GeoFind plugin) {
        super(plugin);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {

        User user = plugin.getUserManager().getUser(e.getPlayer());

        if(user.hasRequest()) {
            Request request = user.getRequest();
            request.cancel();
        }

    }

}
