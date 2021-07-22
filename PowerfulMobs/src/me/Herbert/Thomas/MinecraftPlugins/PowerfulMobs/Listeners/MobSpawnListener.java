package me.Herbert.Thomas.MinecraftPlugins.PowerfulMobs.Listeners;

import org.bukkit.Chunk;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import me.Herbert.Thomas.MinecraftPlugins.PowerfulMobs.Main.Main;

public class MobSpawnListener implements Listener {

    Main plugin;

    /*
     * without some kind of limit you end up with thousands of vexes spawning in a
     * matter of a couple of minutes, due to bats not counting towards the hostile
     * mob cap
     */
    private int vex_count = 0;
    private final int VEX_LIMIT = 20;

    public MobSpawnListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void creatureSpawn(CreatureSpawnEvent event) {
        // mobs spawned by the plugin enter here
        if (event.getSpawnReason().equals(SpawnReason.CUSTOM)) {
            return;
        }
        switch (event.getEntity().getType()) {
            case ZOMBIE:
                Zombie z = (Zombie) event.getEntity();
                z.setBaby();
                break;
            case SKELETON:
                Skeleton skele = (Skeleton) event.getEntity();
                event.setCancelled(true);
                skele.getWorld().spawnEntity(skele.getLocation(), EntityType.STRAY);
                break;
            case CREEPER:
                Creeper c = (Creeper) event.getEntity();
                c.setPowered(true);
                break;
            case SPIDER:
                // TODO: add spider jockeys
                /*
                 * CraftWorld cw = (CraftWorld) event.getEntity().getWorld(); World w =
                 * cw.getHandle(); EntitySpider spider = new EntitySpider(EntityTypes.SPIDER,
                 * w); EntitySkeleton skeleton = new EntitySkeleton(EntityTypes.SKELETON, w);
                 * skeleton.spawnIn(w); spider.spawnIn(w); skeleton.startRiding(spider);
                 */

                /*
                 * Spider s = (Spider) event.getEntity(); Skeleton passenger = (Skeleton)
                 * s.getWorld().spawnEntity(s.getLocation(), EntityType.SKELETON);
                 * s.addPassenger(passenger);
                 */
                break;
            case BAT:
                if (this.vex_count < this.VEX_LIMIT) {
                    this.vex_count++;
                    Bat b = (Bat) event.getEntity();
                    event.setCancelled(true);
                    b.getWorld().spawnEntity(b.getLocation(), EntityType.VEX);
                }
                break;
            default:
                break;
        }
    }

    @EventHandler
    public void creatureDeath(EntityDeathEvent event) {
        if (event.getEntityType().equals(EntityType.VEX)) {
            this.vex_count--;
        }
    }

    @EventHandler
    public void unloadChunk(ChunkUnloadEvent event) {
        Chunk c = event.getChunk();
        int vexes = 0;
        for (Chunk ch : c.getWorld().getLoadedChunks()) {
            for (Entity e : ch.getEntities()) {
                if (e.getType().equals(EntityType.VEX)) {
                    vexes++;
                }
            }
        }
        this.vex_count = vexes;
    }
}
