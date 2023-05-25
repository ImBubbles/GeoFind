package me.bubbles.geofind.users;

import me.bubbles.geofind.GeoFind;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class UserManager {

    private GeoFind plugin;
    private HashSet<User> userList = new HashSet<>();

    public UserManager(GeoFind plugin) {
        this.plugin=plugin;
    }

    public User addPlayer(Player p) {
        for(User user : userList) {
            if(user.getPlayer().getUniqueId().equals(p.getUniqueId()))
                return null;
        }
        User user = new User(p,plugin);
        userList.add(user);
        return user;
    }

    public User getUser(Player p) {
        for(User user : userList) {
            if(user.getPlayer().getUniqueId().equals(p.getUniqueId()))
                return user;
        }
        return null;
    }

}
