package com.andrei1058.bedwars.healpool.versionsupport;

import org.bukkit.entity.Player;

public class Newer implements VersionSupport {
    @Override
    public void playEffect(Player player) {
        player.spawnParticle(org.bukkit.Particle.VILLAGER_HAPPY, player.getLocation(), 1);
    }
}
