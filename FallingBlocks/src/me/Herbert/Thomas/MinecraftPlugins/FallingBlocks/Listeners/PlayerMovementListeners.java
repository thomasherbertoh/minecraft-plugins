package me.Herbert.Thomas.MinecraftPlugins.FallingBlocks.Listeners;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import me.Herbert.Thomas.MinecraftPlugins.FallingBlocks.Main.Main;

public class PlayerMovementListeners implements Listener {

	Main plugin;

	private static int updateRadius = 7;
	private static int yUpdateRadius = 25;
	private static boolean enabled = false;

	private static final HashSet<Material> noUpdate = new HashSet<Material>() {
		{
			add(Material.OBSIDIAN);
			add(Material.BEDROCK);
			add(Material.VINE);
			add(Material.LILY_PAD);
			add(Material.END_PORTAL);
			add(Material.END_PORTAL_FRAME);
			add(Material.NETHER_PORTAL);
			add(Material.END_STONE);
		}
	};

	public PlayerMovementListeners(Main plugin) {
		this.plugin = plugin;
	}

	public static void setGravity(boolean val) {
		enabled = val;
	}

	public static boolean getGravity() {
		return enabled;
	}

	public static void setUpdateRadius(int val) {
		updateRadius = val;
	}

	public static int getUpdateRadius() {
		return updateRadius;
	}

	private void applyGravity(Block b) {
		Location newLoc = b.getLocation();
		newLoc.setX(newLoc.getX() + 0.5);
		newLoc.setZ(newLoc.getZ() + 0.5);
		BlockData newDat = b.getBlockData();
		FallingBlock fb = b.getWorld().spawnFallingBlock(newLoc, newDat);
		b.setType(Material.AIR);
		fb.setVelocity(new Vector(0, 0, 0));
	}

	@EventHandler
	public void playerMoves(PlayerMoveEvent event) {
		if (!enabled) {
			return;
		}
		Location origin = event.getTo();
		boolean update;
		for (int x = origin.getBlockX() - updateRadius; x < origin.getBlockX() + updateRadius; x++) {
			for (int z = origin.getBlockZ() - updateRadius; z < origin.getBlockZ() + updateRadius; z++) {
				update = false;
				for (int y = origin.getBlockY() - yUpdateRadius; y < origin.getBlockY() + yUpdateRadius; y++) {
					Block block = origin.getWorld().getBlockAt(x, y, z);
					if (block.isLiquid() || block.isEmpty()) {
						update = true;
					} else if (update && !block.isLiquid() && !block.isEmpty() && !noUpdate.contains(block.getType())) {
						applyGravity(block);
					}
				}
			}
		}
	}

	@EventHandler
	public void blockBroken(BlockBreakEvent event) {
		if (!enabled) {
			return;
		}
		Location loc = event.getBlock().getLocation();
		for (int y = loc.getBlockY() + 1; y < loc.getBlockY() + 1 + yUpdateRadius; y++) {
			Block block = loc.getWorld().getBlockAt(loc.getBlockX(), y, loc.getBlockZ());
			if (!block.isLiquid() && !block.isEmpty() && !noUpdate.contains(block.getType())) {
				applyGravity(block);
			}
		}
	}

	@EventHandler
	public void blockPlaced(BlockPlaceEvent event) {
		if (!enabled) {
			return;
		}
		Location loc = event.getBlockPlaced().getLocation();
		for (int y = loc.getBlockY() - yUpdateRadius; y < loc.getBlockY() + yUpdateRadius; y++) {
			Block block = loc.getWorld().getBlockAt(loc.getBlockX(), y, loc.getBlockZ());
			if (!block.isLiquid() && !block.isEmpty() && !noUpdate.contains(block.getType())) {
				applyGravity(block);
			}
		}
	}
}
