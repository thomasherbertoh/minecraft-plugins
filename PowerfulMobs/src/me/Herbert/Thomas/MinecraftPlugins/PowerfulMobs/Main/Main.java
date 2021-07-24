package me.Herbert.Thomas.MinecraftPlugins.PowerfulMobs.Main;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Herbert.Thomas.MinecraftPlugins.PowerfulMobs.Listeners.MobSpawnListener;

public class Main extends JavaPlugin {

    public void onEnable() {
        System.out.println("(!) Enabling PowerfulMobs (!)");
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new MobSpawnListener(this), this);
    }

    public void onDisable() {
	System.out.println("(!) Disabling PowerfulMobs (!)");
    }

}
