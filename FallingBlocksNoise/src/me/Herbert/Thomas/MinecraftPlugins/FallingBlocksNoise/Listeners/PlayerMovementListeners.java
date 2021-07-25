package me.Herbert.Thomas.MinecraftPlugins.FallingBlocksNoise.Listeners;

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
import org.bukkit.util.noise.PerlinNoiseGenerator;

import me.Herbert.Thomas.MinecraftPlugins.FallingBlocksNoise.Main.Main;

public class PlayerMovementListeners implements Listener {

	Main plugin;

	PerlinNoiseGenerator pn;

	private static int maxYUpdate = 16;
	private static int updateRadius = 16;
	private static boolean enabled = true;
	private static final double shrinkFactor = 10;

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
		this.pn = new PerlinNoiseGenerator(this.plugin.getServer().getWorlds().get(0));
	}

	/*
	 * The noise value received from the PerlinNoiseGenerator by default is in the
	 * range [-1, 1], but I need it in the range [0, maxYUpdate]
	 */
	private static int mapNoise(double noise) {
		int out = 0;
		noise = ((noise + 1) * maxYUpdate) / 2;
		out = (int) Math.round(noise);
		return out;
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
		int verticalUpdate;
		for (int x = origin.getBlockX() - updateRadius; x < origin.getBlockX() + updateRadius; x++) {
			for (int z = origin.getBlockZ() - updateRadius; z < origin.getBlockZ() + updateRadius; z++) {
				update = false;
				verticalUpdate = mapNoise(
						this.pn.noise((double) x / shrinkFactor, (double) z / shrinkFactor, 1, 2, 1, true));
				for (int y = origin.getBlockY() - verticalUpdate; y < origin.getBlockY() + verticalUpdate; y++) {
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
		int verticalUpdate = mapNoise(
				this.pn.noise(loc.getBlockX() / shrinkFactor, loc.getBlockZ() / shrinkFactor, 1, 2, 1, true));
		for (int y = loc.getBlockY() + 1; y < loc.getBlockY() + 1 + verticalUpdate; y++) {
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
		int verticalUpdate = mapNoise(
				this.pn.noise(loc.getBlockX() / shrinkFactor, loc.getBlockZ() / shrinkFactor, 1, 2, 1, true));
		for (int y = loc.getBlockY() - verticalUpdate; y < loc.getBlockY() + verticalUpdate; y++) {
			Block block = loc.getWorld().getBlockAt(loc.getBlockX(), y, loc.getBlockZ());
			if (!block.isLiquid() && !block.isEmpty() && !noUpdate.contains(block.getType())) {
				applyGravity(block);
			}
		}
	}
}
