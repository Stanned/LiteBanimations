package com.stanexe.sbananimations;

import com.stanexe.sbananimations.commands.BanAnimationsCommand;
import com.stanexe.sbananimations.util.AnimationPlayer;
import com.stanexe.sbananimations.util.Database;
import litebans.api.Entry;
import litebans.api.Events;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.UUID;

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
        Objects.requireNonNull(getCommand("bananimations")).setExecutor(new BanAnimationsCommand());
        getLogger().info("SBanAnimations has been enabled!");
        registerEvents();
    }

    @Override
    public void onDisable() {
        getLogger().info("SBanAnimations has been disabled.");
    }


    public void registerEvents() {
        Events.get().register(new Events.Listener() {
            @Override
            public void entryAdded(Entry entry) {
                if (entry.getType().equals("ban")) {
                    UUID uuid = UUID.fromString(Objects.requireNonNull(entry.getUuid()));
                    Player target = Bukkit.getPlayer(uuid);
                    if (target == null) {
                        return;
                    }
                    if (!target.isOnline()) {
                        return;
                    }
                    getLogger().info(entry.getExecutorName());
                    String modUUIDString = entry.getExecutorUUID();
                    if (modUUIDString == null) {
                        return;
                    }
                    UUID moderatorUUID = UUID.fromString(modUUIDString);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            AnimationPlayer.playAnimation(Bukkit.getPlayer(moderatorUUID), target);
                        }
                    }.runTask(instance);


                }
            }
        });
    }

}
