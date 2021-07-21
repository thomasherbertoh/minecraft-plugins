package me.Herbert.Thomas.MinecraftPlugins.PowerfulMobs.Listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import me.Herbert.Thomas.MinecraftPlugins.PowerfulMobs.Main.Main;

public class MobSpawnListener implements Listener {

    Main plugin;

    public MobSpawnListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void mobSpawn(EntitySpawnEvent event) {
        if (event.getEntity().getType().equals(EntityType.ZOMBIE)) {
            Zombie z = (Zombie) event.getEntity();
            z.setBaby();
        }
    }

}
