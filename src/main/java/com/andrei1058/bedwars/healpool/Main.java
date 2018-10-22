package com.andrei1058.bedwars.healpool;

import com.andrei1058.bedwars.api.events.ArenaDisableEvent;
import com.andrei1058.bedwars.api.events.GameEndEvent;
import com.andrei1058.bedwars.api.events.UpgradeBuyEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.healpool.versionsupport.Newer;
import com.andrei1058.bedwars.healpool.versionsupport.VersionSupport;
import com.andrei1058.bedwars.healpool.versionsupport.v1_8_R3;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private static VersionSupport versionSupport;
    public static Main plugin;

    @Override
    public void onEnable() {

        plugin = this;

        //Check dependencies
        if (Bukkit.getPluginManager().getPlugin("BedWars1058") == null) {
            this.getLogger().severe("I can't run without BedWars1058 Plugin!");
            this.setEnabled(false);
            return;
        }

        boolean supported = true;
        switch (com.andrei1058.bedwars.Main.getServerVersion()) {
            case "v1_8_R2":
            case "v1_8_R1":
                supported = false;
                break;
            case "v1_8_R3":
                versionSupport = new v1_8_R3();
                break;
            default:
                versionSupport = new Newer();
                break;
        }

        if (!supported){
            getLogger().severe("Your server version is not supported!");
            setEnabled(false);
            return;
        }

        Bukkit.getPluginManager().registerEvents(this, this);

    }

    @EventHandler
    public void onTeamUpgrade(UpgradeBuyEvent e) {
        Bukkit.broadcastMessage("UPGRADE EVENT TRIGGERED!");
        if (e.getTeamUpgrade().getName().equalsIgnoreCase("healPool")) {
            Bukkit.broadcastMessage("UPGRADE EVENT TRIGGERED IS HEAL POOL");
            Arena a = Arena.getArenaByPlayer(e.getBuyer());
            if (a == null) return;
            BedWarsTeam bwt = a.getTeam(e.getBuyer());
            if (bwt == null) return;
            if (!HealPoolTask.exists(a, bwt)) {
                Bukkit.broadcastMessage("UPGRADE EVENT TRIGGERED NEW TASK");
                new HealPoolTask(bwt);
            }
        }
    }

    @EventHandler
    public void onDisable(ArenaDisableEvent e) {
        HealPoolTask.removeForArena(e.getArenaName());
    }

    @EventHandler
    public void onEnd(GameEndEvent e) {
        HealPoolTask.removeForArena(e.getArena());
    }

    public static VersionSupport getVersionSupport() {
        return versionSupport;
    }
}
