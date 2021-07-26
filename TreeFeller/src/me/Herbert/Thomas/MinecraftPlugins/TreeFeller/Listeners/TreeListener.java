package me.Herbert.Thomas.MinecraftPlugins.TreeFeller.Listeners;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.Herbert.Thomas.MinecraftPlugins.TreeFeller.Main.Main;
import me.Herbert.Thomas.MinecraftPlugins.TreeFeller.Utils.IntegerLocationPair;

public class TreeListener implements Listener {

	Main plugin;

	private static final HashSet<Material> logs = new HashSet<Material>() {
		{
			add(Material.ACACIA_LOG);
			add(Material.BIRCH_LOG);
			add(Material.DARK_OAK_LOG);
			add(Material.JUNGLE_LOG);
			add(Material.OAK_LOG);
			add(Material.SPRUCE_LOG);
		}
	};

	private static final ArrayList<BlockFace> faces = new ArrayList<BlockFace>() {
		{
			add(BlockFace.UP);
			add(BlockFace.DOWN);
			add(BlockFace.NORTH);
			add(BlockFace.EAST);
			add(BlockFace.SOUTH);
			add(BlockFace.WEST);
		}
	};

	public TreeListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void treeBreak(BlockBreakEvent event) {
		Block block = event.getBlock();

		if (!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_AXE)) {
			return;
		}

		if (!logs.contains(block.getType())) {
			return;
		}

		PriorityQueue<IntegerLocationPair> pq = new PriorityQueue<IntegerLocationPair>();
		// add logs to queue, breaking them in the process
		Location check = block.getLocation();
		while (logs.contains(check.getBlock().getType())) {
			check.getBlock().breakNaturally();
			pq.add(new IntegerLocationPair(0,
					new Location(check.getWorld(), check.getBlockX(), check.getBlockY(), check.getBlockZ())));
			check.setY(check.getY() + 1);
		}

		int max_dist = 6;
		int dist = 0;
		Location loc;
		IntegerLocationPair next_pair;
		// while there are locations to be checked
		while (!pq.isEmpty()) {
			next_pair = pq.poll();
			dist = next_pair.getKey();
			loc = next_pair.getValue();

			if (!loc.getBlock().getType().equals(Material.AIR)) {
				Bukkit.broadcastMessage(String.format("breaking leaf block at location %s", loc.toString()));
				loc.getBlock().breakNaturally();
			}

			// this is the current closest block to the original trunk, therefore seeing as
			// the next block would despawn naturally we don't need to continue any further
			// in the priority queue
			if (dist + 1 > max_dist) {
				break;
			}

			Block leafBlock;
			Leaves leaves;
			// for every direction
			for (BlockFace bf : faces) {
				// if block in that direction isn't leaves
				if (!(loc.getBlock().getRelative(bf).getBlockData() instanceof Leaves)) {
					continue;
				}

				leafBlock = loc.getBlock().getRelative(bf);
				Bukkit.broadcastMessage(
						String.format("before update: %d", ((Leaves) leafBlock.getBlockData()).getDistance()));
				leafBlock.getState().update(true);
				leaves = (Leaves) leafBlock.getBlockData();

				// this block is close enough to another tree that it wouldn't decay naturally
				if (leaves.getDistance() <= max_dist && leaves.getDistance() != dist) {
					continue;
				}

				// adding the location of the block to the queue along with it's distance
				// have to explicitly create a copy because Java
				pq.add(new IntegerLocationPair(dist + 1,
						new Location(leafBlock.getWorld(), leafBlock.getX(), leafBlock.getY(), leafBlock.getZ())));
			}
		}
	}
}
