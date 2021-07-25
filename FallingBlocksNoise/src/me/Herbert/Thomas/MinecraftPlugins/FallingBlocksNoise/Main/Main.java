package me.Herbert.Thomas.MinecraftPlugins.FallingBlocksNoise.Main;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Herbert.Thomas.MinecraftPlugins.FallingBlocksNoise.Commands.CommandExecutors;
import me.Herbert.Thomas.MinecraftPlugins.FallingBlocksNoise.Listeners.PlayerMovementListeners;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		System.out.println("(!) Enabling FallingBlocksNoise (!)");
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PlayerMovementListeners(this), this);
		this.getCommand("enableGravity").setExecutor((CommandExecutor) new CommandExecutors(this));
		this.getCommand("disableGravity").setExecutor((CommandExecutor) new CommandExecutors(this));
		this.getCommand("getGravity").setExecutor((CommandExecutor) new CommandExecutors(this));
		this.getCommand("setRange").setExecutor((CommandExecutor) new CommandExecutors(this));
		this.getCommand("getRange").setExecutor((CommandExecutor) new CommandExecutors(this));
	}

	public void onDisable() {
		System.out.println("(!) Disabling FallingBlocksNoise (!)");
	}
}
