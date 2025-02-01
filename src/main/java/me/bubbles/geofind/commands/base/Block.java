package me.bubbles.geofind.commands.base;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.commands.manager.Argument;
import me.bubbles.geofind.users.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Block extends Argument {

    public Block(GeoFind plugin, int index) {
        super(plugin, "block", "block <player>", index);
        setPermission("block");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            return;
        }
        Player player = (Player) sender;
        User user = plugin.getUserManager().getUser(player);
        if(!hasPermission(player)) {
            player.sendMessage(getPermissionMsg());
            return;
        }
        if(args.length==index) {
            user.sendMessage(getArgsMessage());
            return;
        }
        Player player2 = Bukkit.getPlayer(args[index]);
        if(player2==null) {
            user.sendMessage("%prefix% %primary%Could not find player %secondary%"+args[index]+"%primary%.");
            return;
        }
        if(player2.equals(player)) {
            user.sendMessage("%prefix% %primary%You can't block yourself silly!");
            return;
        }
        if(user.getWhitelist().contains(player2.getUniqueId())) {
            user.sendMessage("%prefix% %primary%Remove player from your block list first.");
            return;
        }
        if(user.getBlocklist().contains(player2.getUniqueId())) {
            user.sendMessage("%prefix% %primary%Unblocked player %secondary%"+args[index]+"%primary%.");
            user.removeFromBlocklist(player2);
            return;
        }
        user.sendMessage("%prefix% %primary%Blocked player %secondary%"+args[index]+"%primary%.");
        user.addToBlockList(player2);
    }

}
