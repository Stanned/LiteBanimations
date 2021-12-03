package com.stanexe.sbananimations.util;

import com.stanexe.sbananimations.SBanAnimations;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final SBanAnimations plugin = SBanAnimations.getInstance();
    private static Connection conn;

    public static void initDB() {
        if (getConn() != null) {
            initTables();
            plugin.getLogger().info("Successfully connected to the Database!");
        }
    }

    public static Connection getConn() {
        // If connection is still valid, send it back.
        try {
            if (conn != null && conn.isValid(1)) {
                return conn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            plugin.getLogger().severe("Unable to connect to database. Please check your credentials in the config file. Plugin is shutting down.");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
        // Create new connection
        String dbType = plugin.getConfig().getString("database-type");
        if (dbType == null || (!dbType.equalsIgnoreCase("SQLITE") && !dbType.equalsIgnoreCase("MYSQL"))) {
            plugin.getLogger().info("Invalid database type. Expected SQLITE or MYSQL. Plugin is shutting down.");
            Bukkit.getPluginManager().disablePlugin(plugin);
        } else {
            // SQLITE
            if (dbType.equalsIgnoreCase("SQLITE")) {
                try {
                    conn = new SQLITE().openConnection();
                    if (conn != null) {
                        return conn;
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    Bukkit.getPluginManager().disablePlugin(plugin);
                }
                // MYSQL
            } else if (dbType.equalsIgnoreCase("MYSQL")) {
                try {
                    conn = new MYSQL().openConnection();
                    if (conn != null) {
                        return conn;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    plugin.getLogger().severe("Unable to connect to database. Please check your credentials in the config file. Plugin is shutting down.");
                    Bukkit.getPluginManager().disablePlugin(plugin);
                }
            }
        }
        return conn;
    }

    private static void initTables() {
        Connection conn = getConn();
        String tablePrefix = plugin.getConfig().getString("table-prefix");
        String[] sql = {
                "CREATE TABLE IF NOT EXISTS `" + tablePrefix + "animations` (`uuid` CHAR(36) PRIMARY KEY NOT NULL, `animation` TEXT);"
        };
        int i;
        for (i = 0; i <sql.length; i++) {
            try {
                Statement stmt = conn.createStatement();
                stmt.execute(sql[i]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}


