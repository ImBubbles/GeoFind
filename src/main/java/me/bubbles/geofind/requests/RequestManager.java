package me.bubbles.geofind.requests;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.users.User;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class RequestManager {

    private GeoFind plugin;
    private HashSet<Request> requests;

    public RequestManager(GeoFind plugin) {
        this.plugin=plugin;
        this.requests=new HashSet<>();
    }

    public HashSet<Request> getRequests() {
        return requests;
    }

    public void registerRequest(Request request) {
        this.requests.add(request);
        plugin.getTicker().setEnabled(!requests.isEmpty());
    }

    public Request createRequest(User sender, User recipient) {
        return new Request(plugin, this, sender,recipient);
    }

    public Request createRequest(Player sender, Player recipient) {
        return createRequest(plugin.getUserManager().getUser(sender),plugin.getUserManager().getUser(recipient));
    }

    public void removeRequest(Request request) {
        requests.remove(request);
        plugin.getTicker().setEnabled(!requests.isEmpty());
    }

    public int getRequestTimeout() {
        return plugin.getConfig().getInt("requestTimeout")*20;
    }

    public void onTick() {
        requests.forEach(Request::onTick);
    }

}