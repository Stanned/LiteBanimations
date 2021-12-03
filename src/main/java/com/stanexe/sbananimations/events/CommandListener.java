package com.stanexe.sbananimations.events;

import com.stanexe.sbananimations.SBanAnimations;
import com.stanexe.sbananimations.util.AnimationPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandListener implements Listener {
    private static final SBanAnimations plugin = SBanAnimations.getInstance();

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        List<?> commands = plugin.getConfig().getList("commands");
        String sentCommand = e.getMessage().split(" ")[0].substring(1);
        String targetName = e.getMessage().split(" ")[1];
        Player target = Bukkit.getServer().getPlayerExact(targetName);
        if (target == null) {
            return;
        }
        commands.forEach((command) -> {
            if (sentCommand.equalsIgnoreCase((String) command)) {
                e.getPlayer().sendMessage("Match!");
                AnimationPlayer.playAnimation(e.getPlayer(), target);
            }
        });
    }

}
