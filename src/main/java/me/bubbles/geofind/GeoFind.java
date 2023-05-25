package me.bubbles.geofind;

import me.bubbles.geofind.commands.manager.CommandManager;
import me.bubbles.geofind.configs.ConfigManager;
import me.bubbles.geofind.events.manager.EventManager;
import me.bubbles.geofind.messages.Messages;
import me.bubbles.geofind.requests.RequestManager;
import me.bubbles.geofind.ticker.Ticker;
import me.bubbles.geofind.users.UserManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class GeoFind extends JavaPlugin {

    private CommandManager commandManager;
    private EventManager eventManager;
    private ConfigManager configManager;
    private UserManager userManager;
    private RequestManager requestManager;
    private Ticker ticker;
    private Messages messages;

    @Override
    public void onEnable() {

        // PLUGIN IS NOT FINISHED

        //// INSTANCE VARIABLES

        // Configs
        configManager=new ConfigManager(this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        configManager.addConfig(
                "config.yml",
                "messages.yml",
                "blocked.yml",
                "whitelist.yml"
        );

        // MANAGERS
        commandManager=new CommandManager(this);
        eventManager=new EventManager(this);
        userManager=new UserManager(this);

        // MESSAGES
        messages=(new Messages(configManager.getConfig("messages.yml").getFileConfiguration()));

        // Ticker
        ticker=new Ticker(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // RELOAD CONFIGS
    public void reload() {
        getConfigManager().reloadAll();
        this.messages=(new Messages(configManager.getConfig("messages.yml").getFileConfiguration()));
    }

    // TICKER
    public void onTick() {
        requestManager.onTick();
    }

    // GETTERS
    public CommandManager getCommandManager() {
        return commandManager;
    }
    public EventManager getEventManager() {
        return eventManager;
    }
    public ConfigManager getConfigManager() {
        return configManager;
    }
    public RequestManager getRequestManager() {
        return requestManager;
    }
    public UserManager getUserManager() {
        return userManager;
    }
    public Ticker getTicker() {
        return ticker;
    }

}
