package me.bubbles.geofind.commands.manager;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.messages.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command implements CommandExecutor {

    public GeoFind plugin;
    public String no_perms;
    private String command;
    private String permission;
    private List<Argument> arguments = new ArrayList<>();
    public final int INDEX=0;

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
        this.no_perms=plugin.getMessages().getNoPerms().replace("%node%",node);
    }

    public String getArgsMessage() {

        String prefix = plugin.getMessages().getPrefix();
        String pri = plugin.getMessages().getPrimary(); // primary color
        String sec = plugin.getMessages().getSecondary(); // secondary color

        StringBuilder stringBuilder = new StringBuilder();
        String topLine = prefix + pri + " Commands:";
        stringBuilder.append(topLine);

        // IF YOU ARE COPYING MY CODE, REMOVE THIS LINE - THE LINE BELOW IS SPECIFIC TO THIS PLUGIN
        stringBuilder.append("\n").append(pri).append("/").append(getCommand()).append(" ").append(sec).append("<player>");

        for(Argument arg : arguments) {
            String command = "\n" + pri + "/" + getCommand() + sec + " " + arg.getDisplay() + "\n";
            stringBuilder.append(command);
        }

        return stringBuilder.toString();

    }

    public void addArguments(Argument... args) {
        arguments.addAll(Arrays.asList(args));
    }

    public List<Argument> getArguments() {
        return arguments;
    }

}
