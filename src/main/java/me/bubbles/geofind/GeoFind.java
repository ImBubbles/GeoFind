package me.bubbles.geofind;

import me.bubbles.geofind.commands.manager.CommandManager;
import me.bubbles.geofind.configs.ConfigManager;
import me.bubbles.geofind.events.manager.EventManager;
import me.bubbles.geofind.messages.Messages;
import me.bubbles.geofind.requests.RequestManager;
import me.bubbles.geofind.ticker.Ticker;
import me.bubbles.geofind.users.UserManager;
import org.bukkit.entity.Player;
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

        //// INSTANCE VARIABLES

        // Configs
        configManager=new ConfigManager(this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        configManager.addConfig(
                "config.yml",
                "messages.yml",
                "data.yml"
        );

        // MANAGERS
        commandManager=new CommandManager(this);
        eventManager=new EventManager(this);
        userManager=new UserManager(this);
        requestManager=new RequestManager(this);

        // MESSAGES
        messages=(new Messages(configManager.getConfig("messages.yml").getFileConfiguration()));

        // Ticker
        ticker=new Ticker(this);

        //// USERS (in case of plugin is loaded after startup)
        if(!(getServer().getOnlinePlayers().size()==0)) {
            for(Player player : getServer().getOnlinePlayers()) {
                userManager.addPlayer(player);
            }
        }

        ticker.setEnabled(true);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ticker.setEnabled(false);
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
