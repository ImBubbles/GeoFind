package me.bubbles.geofind.sqlite;

public class Database {

    public SQLite sqLite;
    public String table;

    public Database(SQLite sqLite, String table) {
        this.sqLite=sqLite;
        this.table=table;
    }

}
