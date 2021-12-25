package com.stanexe.sbananimations.commands;

import com.google.common.collect.Lists;
import com.stanexe.sbananimations.BanAnimation;
import com.stanexe.sbananimations.SBanAnimations;
import com.stanexe.sbananimations.util.Cache;
import com.stanexe.sbananimations.util.Database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BanAnimationsCommand implements CommandExecutor, TabCompleter {
    private static final SBanAnimations plugin = SBanAnimations.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String tablePrefix = plugin.getConfig().getString("table-prefix");
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by a player!");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(ChatColor.GREEN + "Please select one of the animation types: " + ChatColor.RED + "/bananimation <animation>");
        } else {
            if (!(player.hasPermission("sbananimations." + args[0]))) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use that animation!");
            }
            BanAnimation animation;
            try {
                animation = BanAnimation.valueOf(args[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.RED + "You specified an invalid animation type.");
                return true;
            }

            // Save animation in cache & database
            Cache.putInAnimationsCache(player.getUniqueId(), animation);
            player.sendMessage(ChatColor.GREEN + "You have selected the " + animation + " animation!");
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
                    Connection conn = Database.getConn();
                    PreparedStatement stmt = conn.prepareStatement("REPLACE INTO `" + tablePrefix + "animations` (`uuid`, `animation`) VALUES (?,?);");
                    stmt.setString(1, String.valueOf(player.getUniqueId()));
                    stmt.setString(2, animation.toString());
                    stmt.execute();
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    plugin.getLogger().warning("An error has occurred in the database. Please report this to the plugin author if this keeps happening.");
                }
            });

        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<BanAnimation> animations = Arrays.asList(BanAnimation.class.getEnumConstants());
        List<String> animationsList = new ArrayList<>();
        animations.forEach((animation) -> animationsList.add(animation.toString()));
        List<String> returnList = Lists.newArrayList();
        if (args.length == 1) {
            for (String s : animationsList) {
                if (s.toUpperCase().startsWith(args[0].toUpperCase()) && sender.hasPermission("sbananimations." + s)) {
                    returnList.add(s);
                }
            }
            return returnList;
        }
        return null;
    }
}
