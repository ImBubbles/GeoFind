package me.bubbles.geofind.commands.base;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.commands.manager.Argument;
import me.bubbles.geofind.requests.Request;
import me.bubbles.geofind.users.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Decline extends Argument {

    public Decline(GeoFind plugin, int index) {
        super(plugin, "decline", "decline <player>", index);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            return;
        }
        Player player = (Player) sender;
        User user = plugin.getUserManager().getUser(player);
        if(!user.hasIncomingRequest()) {
            user.sendMessage("%prefix% %primary%You do not have any incoming requests!");
            return;
        }
        if(args.length==index) {
            user.sendMessage(getArgsMessage());
            return;
        }
        if(Bukkit.getPlayer(args[index])==null) {
            user.sendMessage("%prefix% %primary%Could not find player %secondary%"+args[index]+"%primary%.");
            return;
        }
        for(Request request : user.getIncomingRequest()) {
            if(request.getSender().getPlayer().getUniqueId().equals(Bukkit.getPlayer(args[index]).getUniqueId())) {
                request.decline();
            }
        }
    }

}
