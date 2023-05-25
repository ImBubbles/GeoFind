package me.bubbles.geofind.users;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.messages.Messages;
import me.bubbles.geofind.requests.Request;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class User {

    private Player player;
    private GeoFind plugin;
    private Request request;
    private ConfigurationSection whitelist;
    private ConfigurationSection blocked;

    public User(Player player, GeoFind plugin) {
        this.player=player;
        this.plugin=plugin;

        ConfigurationSection whitelist = plugin.getConfigManager().getConfig("whitelist.yml")
                .getFileConfiguration().getConfigurationSection(player.getUniqueId().toString());
        ConfigurationSection blocked = plugin.getConfigManager().getConfig("blocked.yml")
                .getFileConfiguration().getConfigurationSection(player.getUniqueId().toString());

        /*try {
            if(!(configurationSection
                    .getKeys(false)
                    .contains(player.getUniqueId().toString()))) {
                configurationSection.set(player.getUniqueId().toString()+".wins",0);
            }
        } catch (NullPointerException e) {
            data.set("players."+player.getUniqueId()+".wins",0);
        }*/

        plugin.getConfigManager().saveAll();

        this.whitelist=whitelist;
        this.blocked=blocked;

    }

    public void sendMessage(Messages.Message message) {
        String newStr = ChatColor.translateAlternateColorCodes('&',message.getStr()
                .replace("%name%",player.getName())
                .replace("%prefix%",Messages.Message.PREFIX.getStr())
                .replace("%primary%", Messages.Message.PRIMARY.getStr())
                .replace("%secondary%", Messages.Message.SECONDARY.getStr()));
        player.sendMessage(newStr);
    }

    public void sendMessage(String message) {
        String newStr = ChatColor.translateAlternateColorCodes('&',message
                .replace("%name%",player.getName())
                .replace("%prefix%",Messages.Message.PREFIX.getStr())
                .replace("%primary%", Messages.Message.PRIMARY.getStr())
                .replace("%secondary%", Messages.Message.SECONDARY.getStr()));
        player.sendMessage(newStr);
    }

    // GETTERS

    public Player getPlayer() {
        return player;
    }

    public Request getRequest() {
        return request;
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
        for(Request request : plugin.getRequestManager().getRequests()) {
            if(request.getSender().equals(this)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRequest() {
        return hasOutgoingRequest()||hasIncomingRequest();
    }

    private List<User> getWhitelist() {
        // TODO and also public isWhitelisted
    }

    private List<User> getBlocked() {
        // TODO and also public isBlocked
    }

    // SETTERS
    public void setRequest(Request request) {
        this.request = request;
    }

    /*public void setWins(int wins) {
        data.set("wins",wins);
    }*/

    // QUEUE

    /*public boolean inGame() {
        for(Game game : plugin.getGameManager().getGames()) {
            if(game.getUsers().contains(this)) {
                return true;
            }
        }
        return false;
    }*/

}
