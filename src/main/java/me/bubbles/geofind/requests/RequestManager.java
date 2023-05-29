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

    public void createRequest(User sender, User recipient) {
        requests.add(new Request(plugin,sender,recipient));
    }

    public void createRequest(Player sender, Player recipient) {
        createRequest(plugin.getUserManager().getUser(sender),plugin.getUserManager().getUser(recipient));
    }

    public boolean removeRequest(Request request) {
        return requests.remove(request);
    }

    public int getRequestTimeout() {
        return plugin.getConfig().getInt("requestTimeout")*20;
    }

    public void onTick() {
        requests.forEach(Request::onTick);
    }

}