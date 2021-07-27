package me.Herbert.Thomas.MinecraftPlugins.TreeFeller.Listeners;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

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

	private int distanceToNearestLog(Block leafBlock) {
		int minDistToLog = 7;
		int distTravelled = 0;
		Queue<Location> q = new LinkedList<>();
		q.add(leafBlock.getLocation());
		int blocks;
		Block newBlock;
		// while we are still in range of the original block
		while (distTravelled + 1 < minDistToLog) {
			distTravelled++;
			// while we have blocks left at this level
			blocks = q.size();
			for (int i = 0; i < blocks; i++) {
				newBlock = q.poll().getBlock();
				// for each face of the current block
				for (BlockFace bf : faces) {
					// get the neighbour in that direction
					Block neighbour = newBlock.getRelative(bf);
					// if neighbour is a log
					if (logs.contains(neighbour.getType())) {
						// using BFS, so must be closest, can return
						return distTravelled;
					} else if (neighbour.getBlockData() instanceof Leaves) {
						// not log, but could take us to one, so add to the queue of blocks to be looked at
						q.add(neighbour.getLocation());
					}
				}
			}
		}
		return minDistToLog;
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
				loc.getBlock().breakNaturally();
			}

			// this is the current closest block to the original trunk, therefore seeing as
			// the next block would despawn naturally we don't need to continue any further
			// in the priority queue
			if (dist + 1 > max_dist) {
				break;
			}

			Block leafBlock;
			int leafDist;
			// for every direction
			for (BlockFace bf : faces) {
				// if block in that direction isn't leaves
				if (!(loc.getBlock().getRelative(bf).getBlockData() instanceof Leaves)) {
					continue;
				}

				leafBlock = loc.getBlock().getRelative(bf);
				leafBlock.getState().update(true);

				// this block is close enough to another tree that it wouldn't decay naturally
				leafDist = distanceToNearestLog(leafBlock);
				if (leafDist <= max_dist) {
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
