package me.bubbles.geofind.requests;

import me.bubbles.geofind.GeoFind;
import me.bubbles.geofind.users.User;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.text.DecimalFormat;

public class Request {

    private GeoFind plugin;
    private User sender;
    private User recipient;
    private int ticks;

    public Request(GeoFind plugin, User sender, User recipient) {
        this.plugin=plugin;
        this.sender=sender;
        this.recipient=recipient;
        this.ticks=0;
        if(sender.getPlayer().hasPermission("geofind.bypass")) {
            this.complete(true,false);
        }
        // TODO
        // if blocked, end(false)
        // if whitelisted, complete(true, true)
    }

    public void onTick() {
        ticks=clamp(ticks,ticks+1,0,plugin.getRequestManager().getRequestTimeout());
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    private int clamp(int old, int now, int min, int max) {
        if(now<min) {
            return Math.max(old, min);
        }
        if(now>max) {
            return Math.min(old, max);
        }
        return now;
    }

    public boolean contains(User user) {
        return recipient.equals(user) || sender.equals(user);
    }

    private String getLocationMessage() {
        Location d1 = sender.getPlayer().getLocation();
        Location d2 = recipient.getPlayer().getLocation();
        double a = d1.getBlockX() - d2.getBlockX();
        double b = d1.getBlockZ() - d2.getBlockZ();
        double distance = Math.sqrt((a*a)+(b*b));
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
        String text = ChatColor.translateAlternateColorCodes('&',
                "%prefix% %primary%" + sender.getPlayer().getName() +
                        "\n%primary%Distance: %secondary%" + df.format(distance) + "m" +
                        "\n%primary%World: %secondary%" + d2.getWorld().getName() +
                        "\n%primary%X: %secondary%" + d2.getBlockX() + " %primary%Y: %secondary%" + d2.getBlockY() + " %primary%Z: %secondary%" + d2.getBlockZ());
        return text;
    }

    public void complete(boolean forced, boolean notify) {
        sender.sendMessage(getLocationMessage());
        if(forced) {
            recipient.
        }
    }

    public void cancel() {
        plugin.getRequestManager().removeRequest(this);
    }

}
