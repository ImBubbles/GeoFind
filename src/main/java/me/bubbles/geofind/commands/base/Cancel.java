package me.bubbles.geofind.commands.base;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.commands.manager.Argument;
import me.bubbles.geofind.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cancel extends Argument {

    public Cancel(GeoFind plugin, int index) {
        super(plugin, "cancel", "cancel", index);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            return;
        }
        Player player = (Player) sender;
        User user = plugin.getUserManager().getUser(player);
        if(!user.hasOutgoingRequest()) {
            user.sendMessage("%prefix% %primary%You do not have any outgoing requests!");
            return;
        }
        user.getOutgoingRequest().cancel();
    }

}
