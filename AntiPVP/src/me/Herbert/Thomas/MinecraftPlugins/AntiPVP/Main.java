package me.Herbert.Thomas.MinecraftPlugins.AntiPVP;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Herbert.Thomas.MinecraftPlugins.AntiPVP.Updates.Commands;
import me.Herbert.Thomas.MinecraftPlugins.AntiPVP.Updates.MyListeners;

public class Main extends JavaPlugin {

	public void onEnable() {
		System.out.println("(!) Enabling AntiPVP (!)");
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new MyListeners(this), this);
		this.getCommand("togglePVP").setExecutor((CommandExecutor) new Commands(this));
		this.getCommand("getPVP").setExecutor((CommandExecutor) new Commands(this));
		this.getCommand("hit").setExecutor((CommandExecutor) new Commands(this));
	}

	public void onDisable() {
		System.out.println("(!) Disabling AntiPVP (!)");
	}
}
