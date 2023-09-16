package me.bubbles.geofind.commands.base;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.commands.manager.Argument;
import me.bubbles.geofind.commands.manager.Command;
import me.bubbles.geofind.messages.Messages;
import me.bubbles.geofind.users.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BaseCommand extends Command {
    private final int index=0;

    public BaseCommand(GeoFind plugin) {
        super("geofind", plugin);
        addArguments(
                new Block(plugin,index),
                new Whitelist(plugin,index),
                new Accept(plugin,index),
                new Decline(plugin,index),
                new Cancel(plugin,index),
                new Reload(plugin,index)
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(!(args.length==0)) { // IF PLAYER SENDS ARGUMENTS
            if(Bukkit.getPlayer(args[0])!=null) { // IF PLAYER HAS A PLAYER'S NAME AS THE FIRST ARGUMENT
                if(!(sender instanceof Player)) {
                    return true;
                }
                Player player = ((Player) sender);
                User user = plugin.getUserManager().getUser(player);
                if(!player.hasPermission("geofind.find")) {
                    user.sendMessage(plugin.getMessages().getNoPerms().replace("%node%","geofind.find"));
                    return true;
                }
                if(player.equals(Bukkit.getPlayer(args[0]))) {
                    user.sendMessage("%prefix% %primary%You can't send yourself a request silly!");
                    return true;
                }
                if(user.hasOutgoingRequest()) {
                    user.sendMessage("%prefix% %primary%You already have an outgoing request!");
                    return true;
                }
                User user2 = plugin.getUserManager().getUser(Bukkit.getPlayer(args[0]));
                plugin.getRequestManager().createRequest(player,user2.getPlayer());
                return true;
            }
            for(Argument argument : getArguments()) { // ARGUMENTS
                if(argument.getArg().equalsIgnoreCase(args[index])) {
                    argument.run(sender, args);
                    return true;
                }
            }
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getArgsMessage()));

        return true;
    }

}
