package me.bubbles.geofind.sqlite;

import java.sql.*;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;

public class BlockDB extends Database {

    public BlockDB(SQLite sqLite) {
        super(sqLite, "block");
        try {
            Statement s = sqLite.getSQLConnection().createStatement();
            s.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS block (" +
                            "`id` INT AUTO_INCREMENT," +
                            "`player` varchar(32) NOT NULL," +
                            "`blocked` varchar(32) NOT NULL," +
                            "PRIMARY KEY (`id`)" +
                            ");"
            );
            s.close();
        } catch (SQLException e) {
            sqLite.plugin.getLogger().log(Level.SEVERE,"Could not create table for blocklist!");
        }
    }

    public HashSet<UUID> getBlocklist(UUID uuid) {
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
                result.add(UUID.fromString(rs.getString("blocked")));
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

    public boolean writeEntry(UUID player, UUID blocked) {

        if(getBlocklist(player).contains(blocked)) {
            return false;
        }

        try {
            PreparedStatement ps = sqLite.getSQLConnection().prepareStatement("INSERT INTO block(player, blocked) " +
                    "VALUES(" +
                    "?,?" +
                    ");"
            );
            ps.setString(1,player.toString());
            ps.setString(2,blocked.toString());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteEntry(UUID player, UUID blocked) {

        if(!getBlocklist(player).contains(blocked)) {
            return false;
        }

        try {
            PreparedStatement ps = sqLite.getSQLConnection().prepareStatement("DELETE FROM block WHERE player = ? AND blocked = ?;");
            ps.setString(1,player.toString());
            ps.setString(2,blocked.toString());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
