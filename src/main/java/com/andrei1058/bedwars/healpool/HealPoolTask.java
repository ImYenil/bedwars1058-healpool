package com.andrei1058.bedwars.healpool;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class HealPoolTask extends BukkitRunnable {

    private BedWarsTeam bwt;
    private int maxX, minX;
    private int maxY, minY;
    private int maxZ, minZ;
    private int blocks;
    private Arena arena;

    private static List<HealPoolTask> healPoolTasks = new ArrayList<>();

    public HealPoolTask(BedWarsTeam bwt) {
        this.bwt = bwt;
        int radius = bwt.getArena().getCm().getInt(ConfigPath.ARENA_ISLAND_RADIUS);
        this.maxX = bwt.getSpawn().getBlockX() + radius;
        this.minX = bwt.getSpawn().getBlockX() - radius;
        this.maxY = bwt.getSpawn().getBlockY() + radius;
        this.minY = bwt.getSpawn().getBlockY() - radius;
        this.maxZ = bwt.getSpawn().getBlockZ() + radius;
        this.minZ = bwt.getSpawn().getBlockZ() - radius;
        this.blocks = radius ^ radius;
        this.arena = bwt.getArena();
        this.runTaskTimer(Main.plugin, 0, 20L);
        healPoolTasks.add(this);
    }

    @Override
    public void run() {
        Bukkit.broadcastMessage("TASK RUN");
        for (int x = blocks; x > 0; x--) {
            Random r = new Random();
            Random r2 = new Random();
            Random r3 = new Random();
            Bukkit.broadcastMessage("TASK EFFECT");
            bwt.getSpawn().getWorld().playEffect(new Location(bwt.getSpawn().getWorld(), r2.nextInt(maxX) + minX, r3.nextInt(maxY) + minY, r.nextInt(maxZ) + minZ), Effect.VILLAGER_PLANT_GROW, 1f);
        }
    }

    public static boolean exists(Arena arena, BedWarsTeam bwt) {
        for (HealPoolTask hpt : healPoolTasks) {
            if (hpt.getArena() == arena && hpt.getBwt() == bwt) return true;
        }
        return false;
    }

    public static void removeForArena(Arena a){
        for (HealPoolTask hpt : healPoolTasks) {
            if (hpt.getArena() == a){
                healPoolTasks.remove(hpt);
                hpt.cancel();
            }
        }
    }

    public static void removeForArena(String a){
        for (HealPoolTask hpt : healPoolTasks) {
            if (hpt.getArena().getWorldName().equals(a)){
                healPoolTasks.remove(hpt);
                hpt.cancel();
            }
        }
    }

    public BedWarsTeam getBwt() {
        return bwt;
    }

    public Arena getArena() {
        return arena;
    }
}
