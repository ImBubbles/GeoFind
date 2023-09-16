package me.bubbles.geofind.sqlite;

import java.sql.*;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;

public class WhitelistDB extends Database {

    public WhitelistDB(SQLite sqLite) {
        super(sqLite, "whitelist");
        try {
            Statement s = sqLite.getSQLConnection().createStatement();
            s.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS whitelist (" +
                            "`id` INT AUTO_INCREMENT," +
                            "`player` varchar(32) NOT NULL," +
                            "`whitelisted` varchar(32) NOT NULL," +
                            "PRIMARY KEY ('id')" +
                            ");"
            );
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqLite.plugin.getLogger().log(Level.SEVERE,"Could not create table for whitelist!");
        }
    }

    public HashSet<UUID> getWhitelist(UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = sqLite.getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE player = ?;");
            ps.setString(1,uuid.toString());
            rs = ps.executeQuery();
            HashSet<UUID> result = new HashSet<>();
            while(rs.next()){
                result.add(UUID.fromString(rs.getString("whitelisted")));
            }
            return result;
        } catch (SQLException ex) {
            sqLite.plugin.getLogger().log(Level.SEVERE, "SQL Connection Execute: ", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                sqLite.plugin.getLogger().log(Level.SEVERE, "SQL Connection Close: ", ex);
            }
        }
        return new HashSet<UUID>();
    }

    public boolean writeEntry(UUID player, UUID whitelisted) {

        if(getWhitelist(player).contains(whitelisted)) {
            return false;
        }

        try {
            PreparedStatement ps = sqLite.getSQLConnection().prepareStatement("INSERT INTO whitelist(player, whitelisted) " +
                    "VALUES(" +
                    "?,?" +
                    ");"
            );
            ps.setString(1,player.toString());
            ps.setString(2,whitelisted.toString());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteEntry(UUID player, UUID whitelisted) {
        if(!getWhitelist(player).contains(whitelisted)) {
            return false;
        }

        try {
            PreparedStatement ps = sqLite.getSQLConnection().prepareStatement("DELETE FROM whitelist WHERE player = ? AND whitelisted = ?;");
            ps.setString(1,player.toString());
            ps.setString(2,whitelisted.toString());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
