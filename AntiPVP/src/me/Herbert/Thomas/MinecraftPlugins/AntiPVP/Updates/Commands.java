package me.Herbert.Thomas.MinecraftPlugins.AntiPVP.Updates;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import me.Herbert.Thomas.MinecraftPlugins.AntiPVP.Main;

public class Commands implements CommandExecutor, Listener {

	@SuppressWarnings("unused")
	private Main plugin;

	public Commands(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("togglepvp")) {
			MyListeners.setDisabled(!MyListeners.isDisabled());
			if (MyListeners.isDisabled()) {
				Bukkit.broadcastMessage("PVP has been disabled!");
			} else {
				Bukkit.broadcastMessage("PVP has been enabled!");
			}
		} else if (label.equalsIgnoreCase("getpvp")) {
			String message = "PVP is currently ";
			if (MyListeners.isDisabled()) {
				message += ChatColor.RED + "disabled!";
			} else {
				message += ChatColor.GREEN + "enabled!";
			}
			Bukkit.broadcastMessage(message);
		} else if (label.equalsIgnoreCase("blame --recent")) {
			sender.sendMessage(ChatColor.BLUE + String.valueOf(MyListeners.hit_messages.size()) + " most recent hits:");
			for (String hit : MyListeners.hit_messages) {
				sender.sendMessage("	" + hit);
			}
		} else {
			return false;
		}
		return true;
	}

}
