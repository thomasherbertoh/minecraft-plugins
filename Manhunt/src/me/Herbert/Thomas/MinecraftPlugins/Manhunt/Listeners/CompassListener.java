package me.Herbert.Thomas.MinecraftPlugins.Manhunt.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.Herbert.Thomas.MinecraftPlugins.Manhunt.Main.Main;

public class CompassListener implements Listener {

	Main plugin;

	private static String runner_name;
	private static String hunter_name;

	public CompassListener(Main plugin) {
		this.plugin = plugin;
	}

	public static String getRunnerName() {
		return runner_name;
	}

	public static String getHunterName() {
		return hunter_name;
	}

	public static void setRunnerName(String name) {
		runner_name = name;
	}

	public static void setHunterName(String name) {
		hunter_name = name;
	}

	public static void giveCompass() {
		Bukkit.getPlayer(hunter_name).getInventory().addItem(new ItemStack(Material.COMPASS));
	}

	@EventHandler
	public void compassClick(PlayerInteractEvent event) {
		// check that the player is using a compass
		if (event.getMaterial().equals(Material.COMPASS)) {
			String message;
			// if runner and hunter in same dimension
			if (Bukkit.getPlayer(getRunnerName()).getWorld().getEnvironment()
					.equals(Bukkit.getPlayer(getHunterName()).getWorld().getEnvironment())) {
				event.getPlayer().setCompassTarget(Bukkit.getPlayerExact(getRunnerName()).getLocation());
				message = String.format(ChatColor.GREEN + "The compass is now pointing to " + ChatColor.BLUE + "%s"
						+ ChatColor.GREEN + "!", getRunnerName());
			} else {
				// different dimensions
				message = ChatColor.RED + "There is no one in this dimension to track!";
			}
			event.getPlayer().sendMessage(message);
		}
	}

}
