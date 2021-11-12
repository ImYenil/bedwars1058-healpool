package com.andrei1058.bedwars.healpool.versionsupport;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Newer implements VersionSupport {
    @Override
    public void playEffect(Player player, Location loc) {
        player.spawnParticle(Particle.VILLAGER_HAPPY, loc, 1);
    }
}
