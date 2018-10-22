package com.andrei1058.bedwars.healpool.versionsupport;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_8_R3 implements VersionSupport {
    @Override
    public void playEffect(Player player) {

        EntityPlayer p = ((CraftPlayer)player).getHandle();
        PacketPlayOutWorldParticles pwp = new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_HAPPY, true, 1, 1,1 ,1, 1, 1, 1, 1, 1);
        p.playerConnection.sendPacket(pwp);
    }
}
