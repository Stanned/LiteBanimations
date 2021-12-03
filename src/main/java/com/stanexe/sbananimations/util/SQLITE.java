package com.stanexe.sbananimations.util;

import com.stanexe.sbananimations.SBanAnimations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLITE {
    private final SBanAnimations plugin = SBanAnimations.getInstance();

    public Connection openConnection() throws SQLException {
        Connection conn;
        conn = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + "/" + plugin.getName() + ".db");
        return conn;
    }
}
