package me.bubbles.geofind.users;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.messages.Messages;
import me.bubbles.geofind.requests.Request;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class User {

    private Player player;
    private GeoFind plugin;
    private ConfigurationSection data;

    public User(Player player, GeoFind plugin) {
        this.player=player;
        this.plugin=plugin;

        FileConfiguration data = plugin.getConfigManager().getConfig("data.yml")
                .getFileConfiguration();

        try {
            data.getConfigurationSection(player.getUniqueId().toString()).getStringList("whitelist");
        } catch (NullPointerException e) {
            data.set(player.getUniqueId().toString(), new ArrayList<>());
        }

        try {
            data.getConfigurationSection(player.getUniqueId().toString()).getStringList("blocklist");
        } catch (NullPointerException e) {
            data.set(player.getUniqueId().toString(),new ArrayList<>());
        }

        this.data=data.getConfigurationSection(player.getUniqueId().toString());

        plugin.getConfigManager().saveAll();

        this.data=data;

    }

    public void sendMessage(String message) {
        String newStr = ChatColor.translateAlternateColorCodes('&',message
                .replace("%name%",player.getName())
                .replace("%prefix%",plugin.getMessages().getPrefix())
                .replace("%primary%", plugin.getMessages().getPrimary())
                .replace("%secondary%", plugin.getMessages().getSecondary()));
        player.sendMessage(newStr);
    }

    // GETTERS

    public Player getPlayer() {
        return player;
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

    public List<OfflinePlayer> getWhitelist() {
        List<String> stringList = data.getStringList("whitelist");
        List<OfflinePlayer> playerList = new ArrayList<>();
        for(String uuid : stringList) {
            playerList.add(Bukkit.getOfflinePlayer(UUID.fromString(uuid)));
        }
        return playerList;
    }

    public List<OfflinePlayer> getBlocklist() {
        List<String> stringList = data.getStringList("blocklist");
        List<OfflinePlayer> playerList = new ArrayList<>();
        for(String uuid : stringList) {
            playerList.add(Bukkit.getOfflinePlayer(UUID.fromString(uuid)));
        }
        return playerList;
    }

    // WHITELIST

    public void addToWhitelist(Player player) {
        List<String> result = data.getStringList("whitelist");
        result.add(player.getUniqueId().toString());
        data.set("whitelist",result);
        plugin.getConfigManager().saveAll();
    }

    public void removeFromWhitelist(Player player) {
        List<String> result = data.getStringList("whitelist");
        result.remove(player.getUniqueId().toString());
        data.set("whitelist",result);
        plugin.getConfigManager().saveAll();
    }

    // BLOCK LIST

    public void addToBlockList(Player player) {
        List<String> result = data.getStringList("blocklist");
        result.add(player.getUniqueId().toString());
        data.set("blocklist",result);
        plugin.getConfigManager().saveAll();
    }

    public void removeFromBlocklist(Player player) {
        List<String> result = data.getStringList("blocklist");
        result.remove(player.getUniqueId().toString());
        data.set("blocklist",result);
        plugin.getConfigManager().saveAll();
    }

}
