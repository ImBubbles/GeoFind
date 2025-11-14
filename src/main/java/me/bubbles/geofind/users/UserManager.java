package me.bubbles.geofind.users;

import me.bubbles.geofind.GeoFind;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

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

    public void removeUser(Player p) {
        User user = getUser(p);
        if (user == null) {
            return;
        }
        removeUser(user);
    }

    public void removeUser(User user) {
        userList.remove(user);
    }

    public User getUser(Player p) {
        return getUser(p.getUniqueId());
    }

    public User getUser(UUID uuid) {
        for(User user : userList) {
            if(user.getUUID().equals(uuid))
                return user;
        }
        return null;
    }

}
