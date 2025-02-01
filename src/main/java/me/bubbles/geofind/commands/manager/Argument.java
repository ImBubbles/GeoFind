package me.bubbles.geofind.commands.manager;

import me.bubbles.geofind.GeoFind;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Argument {

    public GeoFind plugin;
    public int index;
    private List<Argument> arguments = new ArrayList<>();
    private String arg;
    private String display;
    private String permission;
    private Argument base;

    public Argument(GeoFind plugin, String arg, String display, int index) {
        this.plugin=plugin;
        this.index=index+1;
        this.arg=arg;
        this.display=display;
    }

    public Argument(GeoFind plugin, String arg, String display, int index, Argument base) {
        this(plugin,arg,display,index);
        this.base=base;
    }

    public void run(CommandSender sender, String[] args) {

    }

    public void addArguments(Argument... args) {
        arguments.addAll(Arrays.asList(args));
    }

    public String getArg() {
        return arg;
    }
    public String getDisplay() {
        return display;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public void setPermission(String permission) {
        String node = "geofind." + permission;
        this.permission=node;
    }

    public boolean hasPermission(Player player) {
        if(permission==null)
            return true;
        if(!player.hasPermission(permission)) {
            plugin.getUserManager().getUser(player).sendMessage(plugin.getMessages().getNoPerms().replace("%node%",permission));
            return false;
        }else{
            return true;
        }
    }

    public String getPermission() {
        return permission;
    }

    public String getPermissionMsg() {
        return plugin.getMessages().getNoPerms().replace("%node%",permission);
    }

    public String getArgsMessage() {

        String prefix = plugin.getMessages().getPrefix(); // prefix
        String pri = plugin.getMessages().getPrimary(); // primary color
        String sec = plugin.getMessages().getSecondary(); // secondary color

        StringBuilder stringBuilder = new StringBuilder();
        String topLine = prefix + pri + " " + display;
        stringBuilder.append(topLine);

        for(Argument argument : arguments) {
            String command = "\n" + pri + argument.getDisplay();
            stringBuilder.append(command);
        }

        return stringBuilder.toString();

    }

    public Argument getBase() {
        return base;
    }

}
