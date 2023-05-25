package me.bubbles.geofind.requests;

import me.bubbles.geofind.GeoFind;

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

    public boolean addRequest(Request request) {
        return requests.add(request);
    }

    public boolean removeRequest(Request request) {
        return requests.remove(request);
    }

    public int getRequestTimeout() {
        return plugin.getConfig().getInt("requestTimeout");
    }

    public void onTick() {
        requests.forEach(Request::onTick);
    }

}
