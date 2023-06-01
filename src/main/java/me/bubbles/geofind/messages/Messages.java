package me.bubbles.geofind.messages;

import org.bukkit.configuration.file.FileConfiguration;

public class Messages {
    private FileConfiguration config;

    public Messages(FileConfiguration configuration) {
        config=configuration;
    }

    public String getNoPerms() {
        return config.getString("NO_PERMS");
    }

    public String getPrefix() {
        return config.getString("PREFIX");
    }

    public String getPrimary() {
        return config.getString("COLOR_PRIMARY");
    }

    public String getSecondary() {
        return config.getString("COLOR_SECONDARY");
    }

}
