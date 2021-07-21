package me.Herbert.Thomas.MinecraftPlugins.PowerfulMobs.Listeners;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import me.Herbert.Thomas.MinecraftPlugins.PowerfulMobs.Main.Main;

public class MobSpawnListener implements Listener {

    Main plugin;

    public MobSpawnListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void creatureSpawn(CreatureSpawnEvent event) {
        if (!event.getSpawnReason().equals(SpawnReason.CUSTOM)) {
            EntityType et = event.getEntity().getType();
            if (et.equals(EntityType.ZOMBIE)) {
                Zombie z = (Zombie) event.getEntity();
                z.setBaby();
            } else if (et.equals(EntityType.SKELETON)) {
                Skeleton skele = (Skeleton) event.getEntity();
                event.setCancelled(true);
                skele.getWorld().spawnEntity(skele.getLocation(), EntityType.STRAY);
            } else if (et.equals(EntityType.CREEPER)) {
                Creeper c = (Creeper) event.getEntity();
                c.setPowered(true);
            } else if (et.equals(EntityType.SPIDER)) {
                // TODO: add spider jockeys
                /*
                 * CraftWorld cw = (CraftWorld) event.getEntity().getWorld();
                 * World w = cw.getHandle();
                 * EntitySpider spider = new EntitySpider(EntityTypes.SPIDER, w);
                 * EntitySkeleton skeleton = new EntitySkeleton(EntityTypes.SKELETON, w);
                 * skeleton.spawnIn(w);
                 * spider.spawnIn(w);
                 * skeleton.startRiding(spider);
                 */

                /*
                 * Spider s = (Spider) event.getEntity();
                 * Skeleton passenger = (Skeleton) s.getWorld().spawnEntity(s.getLocation(), EntityType.SKELETON);
                 * s.addPassenger(passenger);
                 */
            }
        }
    }
}
