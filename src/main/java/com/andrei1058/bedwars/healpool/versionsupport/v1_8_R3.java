package com.andrei1058.bedwars.healpool.versionsupport;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_8_R3 implements VersionSupport {
    @Override
    public void playEffect(Player p, Location loc) {
        PacketPlayOutWorldParticles pwp = new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_HAPPY, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0, (float) 0, (float) 0, (float) 0, 1);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(pwp);
    }
}
