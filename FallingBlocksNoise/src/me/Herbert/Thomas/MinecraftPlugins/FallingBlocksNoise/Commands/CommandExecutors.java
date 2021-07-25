package me.Herbert.Thomas.MinecraftPlugins.FallingBlocksNoise.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.Herbert.Thomas.MinecraftPlugins.FallingBlocksNoise.Listeners.PlayerMovementListeners;
import me.Herbert.Thomas.MinecraftPlugins.FallingBlocksNoise.Main.Main;

public class CommandExecutors implements CommandExecutor {
	
	Main plugin;

	public CommandExecutors(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("enablegravity")) {
			PlayerMovementListeners.setGravity(true);
			sender.sendMessage(
					ChatColor.BLUE + "Gravity has been " + ChatColor.GREEN + "ENABLED" + ChatColor.BLUE + "!");
			return true;
		} else if (label.equalsIgnoreCase("disablegravity")) {
			PlayerMovementListeners.setGravity(false);
			sender.sendMessage(
					ChatColor.BLUE + "Gravity has been " + ChatColor.RED + "DISABLED" + ChatColor.BLUE + "!");
			return true;
		} else if (label.equalsIgnoreCase("setrange")) {
			PlayerMovementListeners.setUpdateRadius(Integer.parseInt(args[0]));
			sender.sendMessage(ChatColor.BLUE + "The update range has been set to " + ChatColor.GREEN + args[0]
					+ ChatColor.BLUE + " blocks.");
			return true;
		} else if (label.equalsIgnoreCase("getgravity")) {
			if (PlayerMovementListeners.getGravity()) {
				sender.sendMessage(
						ChatColor.BLUE + "Gravity is currently " + ChatColor.GREEN + "ENABLED" + ChatColor.BLUE + "!");
			} else {
				sender.sendMessage(
						ChatColor.BLUE + "Gravity is currently " + ChatColor.RED + "DISABLED" + ChatColor.BLUE + "!");
			}
			return true;
		} else if (label.equalsIgnoreCase("getrange")) {
			sender.sendMessage(ChatColor.BLUE + "The update range is currently set to " + ChatColor.GREEN
					+ PlayerMovementListeners.getUpdateRadius() + ChatColor.BLUE + " blocks.");
		}
		return false;
	}
}
