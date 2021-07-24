package me.Herbert.Thomas.MinecraftPlugins.PowerfulMobs.Listeners;

import org.bukkit.Chunk;
import org.bukkit.Difficulty;
import org.bukkit.entity.Bat;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Wolf;
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

    private Player getNearestPlayer(Entity e) {
        double min_dist = Double.MAX_VALUE;
        double dist = Double.MAX_VALUE;
        Player target = null;
        for (Player p : e.getWorld().getPlayers()) {
            dist = p.getLocation().distance(e.getLocation());
            if (dist < min_dist) {
                min_dist = dist;
                target = p;
            }
        }
        return target;
    }

    @EventHandler
    public void creatureSpawn(CreatureSpawnEvent event) {
        // mobs spawned by the plugin enter here
        if (event.getSpawnReason().equals(SpawnReason.CUSTOM)) {
            return;
        }
        Player target;
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
                Spider s = (Spider) event.getEntity();
                s.getWorld().setDifficulty(Difficulty.HARD);
                event.setCancelled(true);
                CaveSpider cs = (CaveSpider) s.getWorld().spawnEntity(s.getLocation(), EntityType.CAVE_SPIDER);
                target = getNearestPlayer(s);
                if (target != null) {
                    cs.setTarget(target);
                }
                break;
            case BAT:
                if (this.vex_count < this.VEX_LIMIT) {
                    this.vex_count++;
                    Bat b = (Bat) event.getEntity();
                    event.setCancelled(true);
                    b.getWorld().spawnEntity(b.getLocation(), EntityType.VEX);
                }
                break;
            case WOLF:
                Wolf w = (Wolf) event.getEntity();
                w.setAngry(true);
                target = getNearestPlayer(w);
                if (target != null) {
                    w.setTarget(target);
                }
                break;
            case PIGLIN:
            case ZOMBIFIED_PIGLIN:
                Entity p = event.getEntity();
                event.setCancelled(true);
                PiglinBrute pb = (PiglinBrute) p.getWorld().spawnEntity(p.getLocation(), EntityType.PIGLIN_BRUTE);
                target = getNearestPlayer(pb);
                if (target != null) {
                    pb.setTarget(target);
                }
                break;
            case ENDERMAN:
                Enderman e = (Enderman) event.getEntity();
                target = getNearestPlayer(e);
                if (target != null) {
                    e.setTarget(target);
                }
                break;
            case RABBIT:
                Rabbit r = (Rabbit) event.getEntity();
                r.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
                target = getNearestPlayer(r);
                if (target != null) {
                    r.setTarget(target);
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
