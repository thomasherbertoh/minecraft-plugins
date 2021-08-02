package me.Herbert.Thomas.MinecraftPlugins.Manhunt.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Herbert.Thomas.MinecraftPlugins.Manhunt.Listeners.CompassListener;
import me.Herbert.Thomas.MinecraftPlugins.Manhunt.Main.Main;

public class CommandHandler implements CommandExecutor {

	Main plugin;

	public CommandHandler(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (label.equalsIgnoreCase("sethunter")) {
				CompassListener.setHunterName(args[0]);
			} else if (label.equalsIgnoreCase("setrunner")) {
				CompassListener.setRunnerName(args[0]);
			} else if (label.equalsIgnoreCase("compass") && CompassListener.getHunterName() != null) {
				CompassListener.giveCompass();
			}
			return true;
		} else {
			return false;
		}
	}
	
}
