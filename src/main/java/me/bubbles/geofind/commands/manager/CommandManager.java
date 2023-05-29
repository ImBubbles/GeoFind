package me.bubbles.geofind.commands.manager;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.commands.base.BaseCommand;

import java.util.ArrayList;

public class CommandManager {
    private GeoFind plugin;
    private ArrayList<Command> commandsList = new ArrayList<>();

    public CommandManager(GeoFind plugin) {
        this.plugin=plugin;
        registerCommands();
    }

    public void addCommand(Command... commands) {
        for(Command command : commands) {
            plugin.getCommand(command.getCommand()).setExecutor(command);
            commandsList.add(command);
        }
    }

    public void registerCommands() {
        addCommand(new BaseCommand(plugin));
    }

}
