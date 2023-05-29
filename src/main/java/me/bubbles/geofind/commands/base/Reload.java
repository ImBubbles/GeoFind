package me.bubbles.geofind.commands.base;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.commands.manager.Argument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload extends Argument {
    public Reload(GeoFind plugin, int index) {
        super(plugin,"reload","Reload",index);
        setPermission("reload");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(!hasPermission(p))
                return;
            plugin.getUserManager().getUser(p).sendMessage("%prefix% %primary%Config reloaded.");
        }
        plugin.reload();
    }

}
