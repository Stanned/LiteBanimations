package com.stanexe.sbananimations.util;

import com.stanexe.sbananimations.SBanAnimations;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MYSQL {
    private final SBanAnimations plugin = SBanAnimations.getInstance();

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
