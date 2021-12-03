package com.stanexe.sbananimations;

import com.stanexe.sbananimations.commands.BanAnimationsCommand;
import com.stanexe.sbananimations.events.CommandListener;
import com.stanexe.sbananimations.util.Database;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SBanAnimations extends JavaPlugin {

    private static SBanAnimations instance;

    public static SBanAnimations getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        getLogger().info("SBanAnimations by StanEXE is loading...");
        instance = this;
        this.saveDefaultConfig();
        Database.initDB();

        getServer().getPluginManager().registerEvents(new CommandListener(), this);
        Objects.requireNonNull(getCommand("bananimations")).setExecutor(new BanAnimationsCommand());
        getLogger().info("SBanAnimations has been enabled!");

    }

    @Override
    public void onDisable() {
        getLogger().info("SBanAnimations is disabling...");
    }
}
