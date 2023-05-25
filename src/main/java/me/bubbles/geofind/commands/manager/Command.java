package me.bubbles.geofind.commands.manager;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.messages.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    public GeoFind plugin;
    public String no_perms;
    private String command;
    private String permission;

    public Command(String command, GeoFind plugin) {
        this.command=command;
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return true;
    }

    public String getCommand() {
        return command;
    }

    public boolean hasPermission(Player player) {
        if(permission==null)
            return true;
        if(!player.hasPermission(permission)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',no_perms));
            return false;
        }else{
            return true;
        }
    }

    public void setPermission(String permission) {
        String node = "geofind." + permission;
        this.permission=node;
        this.no_perms=Messages.Message.NO_PERMS.getStr().replace("%node%",node);
    }

}
