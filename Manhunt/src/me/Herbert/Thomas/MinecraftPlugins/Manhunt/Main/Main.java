package me.Herbert.Thomas.MinecraftPlugins.Manhunt.Main;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Herbert.Thomas.MinecraftPlugins.Manhunt.Commands.CommandHandler;
import me.Herbert.Thomas.MinecraftPlugins.Manhunt.Listeners.CompassListener;

public class Main extends JavaPlugin {

	public void onEnable() {
		System.out.println("(!) Enabling Manhunt (!)");
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new CompassListener(this), this);
		this.getCommand("setHunter").setExecutor((CommandExecutor) new CommandHandler(this));
		this.getCommand("setRunner").setExecutor((CommandExecutor) new CommandHandler(this));
		this.getCommand("compass").setExecutor((CommandExecutor) new CommandHandler(this));
	}

	public void onDisable() {
		System.out.println("(!) Disabling Manhunt (!)");

	}

}