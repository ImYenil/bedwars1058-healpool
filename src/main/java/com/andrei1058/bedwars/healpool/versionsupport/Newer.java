package com.andrei1058.bedwars.healpool.versionsupport;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Newer implements VersionSupport {
    @Override
    public void playEffect(Player player, Location loc) {
        player.spawnParticle(org.bukkit.Particle.VILLAGER_HAPPY, loc, 1);
    }
}
