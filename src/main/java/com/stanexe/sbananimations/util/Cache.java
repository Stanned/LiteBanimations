package com.stanexe.sbananimations.util;

import com.stanexe.sbananimations.BanAnimation;
import com.stanexe.sbananimations.SBanAnimations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class Cache {

    private static final HashMap<UUID, BanAnimation> animationsCache = new HashMap<>();
    private static final SBanAnimations plugin = SBanAnimations.getInstance();
    private static final String tablePrefix = plugin.getConfig().getString("table-prefix");

    public static void loadCacheFromDB() {
        Connection conn = Database.getConn();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM `" + tablePrefix + "animations`;");
            ResultSet rsAnimations = stmt.executeQuery();
            while (rsAnimations.next()) {
                animationsCache.put(UUID.fromString(rsAnimations.getString("uuid")), BanAnimation.valueOf(rsAnimations.getString("animation")));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<UUID, BanAnimation> getAnimationsCache() {
        return animationsCache;
    }
    public static void putInAnimationsCache(UUID uuid, BanAnimation animation) {
        animationsCache.put(uuid, animation);
    }


}
