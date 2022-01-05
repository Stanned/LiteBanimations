package com.stanexe.litebanimations.util;

import com.stanexe.litebanimations.BanAnimation;
import com.stanexe.litebanimations.LiteBanimations;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;


public class AnimationPlayer {
    private static final LiteBanimations plugin = LiteBanimations.getInstance();

    public static void playAnimation(Player moderator, Player target) {
        if (!target.isOnline()) {return;}
        BanAnimation animationType = Cache.getAnimationsCache().get(moderator.getUniqueId());
        Location location = target.getLocation();
        if (location.getWorld() == null) {
            return;
        }
        switch (animationType) {
            case LIGHTNING:
                location.getWorld().strikeLightningEffect(location);
                return;
            case BAT:
                for (int i = 0; i < 10; i++) {
                    Bat bat = (Bat) location.getWorld().spawnEntity(location, EntityType.BAT);
                    bat.setCollidable(false);
                    bat.setInvulnerable(true);
                    bat.setCustomName(target.getDisplayName());
                    Bukkit.getScheduler().runTaskLater(plugin, bat::remove, 200);
                }
                return;
            case FLAME:
                location.getWorld().spawnParticle(Particle.FLAME, location, 150, 0.001, 0.4, 0.001, 0.1, null, true);
                location.getWorld().playSound(location, Sound.ITEM_FIRECHARGE_USE, 1.0f, 0.7f);
                return;
            case BLACK_HOLE:
                location.setY(location.getY() + 0.7);
                location.getWorld().spawnParticle(Particle.SQUID_INK, location, 80, 0.2, 0.5, 0.2, 0.05, null, true);
                return;
            case EXPLOSION:
                location.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, location, 3, 0.0, 0.0, 0.0, 0.0, null, true);
                location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
                return;
            case TELEPORT:
                location.setY(location.getY() + 0.7);
                location.getWorld().spawnParticle(Particle.PORTAL, location, 200, 0.2, 0.5, 0.2, 0.05, null, true);
                location.getWorld().playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                return;
            case VILLAGER_DEATH:
                Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
                villager.setInvulnerable(true);
                villager.setCollidable(false);
                villager.setCustomName(target.getDisplayName());
                Bukkit.getScheduler().runTaskLater(plugin, () -> villager.setHealth(0), 10);

        }

    }
}

