package com.andrei1058.bedwars.healpool;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HealPoolTask extends BukkitRunnable {

    private BedWarsTeam bwt;
    private int maxX, minX;
    private int maxY, minY;
    private int maxZ, minZ;
    private Arena arena;
    private Random r = new Random();

    private static List<HealPoolTask> healPoolTasks = new ArrayList<>();

    public HealPoolTask(BedWarsTeam bwt) {
        this.bwt = bwt;
        int radius = bwt.getArena().getCm().getInt(ConfigPath.ARENA_ISLAND_RADIUS);
        this.maxX = Math.max(bwt.getSpawn().clone().add(radius, 0, 0).getBlockX(), bwt.getSpawn().clone().subtract(radius, 0, 0).getBlockX());
        this.minX = Math.min(bwt.getSpawn().clone().add(radius, 0, 0).getBlockX(), bwt.getSpawn().clone().subtract(radius, 0, 0).getBlockX());
        this.maxY = Math.max(bwt.getSpawn().clone().add(0, radius, 0).getBlockY(), bwt.getSpawn().clone().subtract(0, radius, 0).getBlockY());
        this.minY = Math.min(bwt.getSpawn().clone().add(0, radius, 0).getBlockY(), bwt.getSpawn().clone().subtract(0, radius, 0).getBlockY());
        this.maxZ = Math.max(bwt.getSpawn().clone().add(0, 0, radius).getBlockZ(), bwt.getSpawn().clone().subtract(0, 0, radius).getBlockZ());
        this.minZ = Math.min(bwt.getSpawn().clone().add(0, 0, radius).getBlockZ(), bwt.getSpawn().clone().subtract(0, 0, radius).getBlockZ());
        this.arena = bwt.getArena();
        this.runTaskTimer(Main.plugin, 0, 40L);
        healPoolTasks.add(this);
    }

    @Override
    public void run() {
        Block b;
        for (int x = minX; x < maxX; x++){
            for (int y = minY; y < maxY; y++){
                for (int z = minZ; z < maxZ; z++){
                    b = new Location(bwt.getSpawn().getWorld(), x, y, z).getBlock();
                    if (b.getType() != Material.AIR) continue;
                    //int chance = r.nextInt(2);
                    //if (chance == 0){
                        for (Player p : bwt.getMembers()){
                            Main.getVersionSupport().playEffect(p);
                        }
                    //}
                }
            }
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
        for (HealPoolTask hpt : new ArrayList<>(healPoolTasks)) {
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
