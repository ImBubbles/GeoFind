package me.bubbles.geofind.users;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.requests.Request;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class User {

    private UUID uuid;
    private GeoFind plugin;

    public User(Player player, GeoFind plugin) {
        this(player.getUniqueId(), plugin);
    }

    public User(UUID uuid, GeoFind plugin) {
        this.uuid=uuid;
        this.plugin=plugin;
    }

    public void sendMessage(String message) {
        Player player = Bukkit.getPlayer(uuid);
        if(player==null) {
            return;
        }
        String newStr = ChatColor.translateAlternateColorCodes('&',message
                .replace("%name%",player.getName())
                .replace("%prefix%",plugin.getMessages().getPrefix())
                .replace("%primary%", plugin.getMessages().getPrimary())
                .replace("%secondary%", plugin.getMessages().getSecondary()));
        player.sendMessage(newStr);
    }

    // GETTERS

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public Request getOutgoingRequest() {
        for(Request request : plugin.getRequestManager().getRequests()) {
            if(request.getSender().equals(this)) {
                return request;
            }
        }
        return null;
    }

    public HashSet<Request> getIncomingRequest() {
        HashSet<Request> result = new HashSet<>();
        for(Request request : plugin.getRequestManager().getRequests()) {
            if(request.getRecipient().equals(this)) {
                result.add(request);
            }
        }
        return result;
    }

    public boolean hasIncomingRequest() {
        for(Request request : plugin.getRequestManager().getRequests()) {
            if(request.getRecipient().equals(this)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasOutgoingRequest() {
        return getOutgoingRequest()!=null;
    }

    public HashSet<UUID> getWhitelist() {
        return plugin.getWhitelistDB().getWhitelist(uuid);
    }

    public HashSet<UUID> getBlocklist() {
        return plugin.getBlockDB().getBlocklist(uuid);
    }

    // WHITELIST

    public void addToWhitelist(Player player) {
        plugin.getWhitelistDB().writeEntry(uuid, player.getUniqueId());
    }

    public void removeFromWhitelist(Player player) {
        plugin.getWhitelistDB().deleteEntry(uuid, player.getUniqueId());
    }

    // BLOCK LIST

    public void addToBlockList(Player player) {
        plugin.getBlockDB().writeEntry(uuid, player.getUniqueId());
    }

    public void removeFromBlocklist(Player player) {
        plugin.getBlockDB().deleteEntry(uuid, player.getUniqueId());
    }

}
