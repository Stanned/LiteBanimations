package com.stanexe.litebanimations.util;

import com.stanexe.litebanimations.LiteBanimations;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MYSQL {
    private final LiteBanimations plugin = LiteBanimations.getInstance();

    public Connection openConnection() throws SQLException {
        Connection conn;
        FileConfiguration config = plugin.getConfig();
        String host = config.getString("host");
        int port = config.getInt("port");
        String database = config.getString("database");
        String username = config.getString("username");
        String password = config.getString("password");
        conn = DriverManager.getConnection("jdbc:mysql://" +
                host + ":" +
                port + "/" +
                database, username, password
        );
        return conn;

    }
}
