package com.stanexe.litebanimations.util;

import com.stanexe.litebanimations.LiteBanimations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLITE {
    private final LiteBanimations plugin = LiteBanimations.getInstance();

    public Connection openConnection() throws SQLException {
        Connection conn;
        conn = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + "/" + plugin.getName() + ".db");
        return conn;
    }
}
