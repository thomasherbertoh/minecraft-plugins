package me.Herbert.Thomas.MinecraftPlugins.TreeFeller.Main;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Herbert.Thomas.MinecraftPlugins.TreeFeller.Listeners.TreeListener;

public class Main extends JavaPlugin {

	public void onEnable() {
		System.out.println("(!) Enabling TreeFeller (!)");
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new TreeListener(this), this);
	}

	public void onDisable() {
		System.out.println("(!) Disabling TreeFeller (!)");
	}

}