package me.Herbert.Thomas.MinecraftPlugins.AntiPVP.Updates;

import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import me.Herbert.Thomas.MinecraftPlugins.AntiPVP.Main;

public class MyListeners implements Listener {

	@SuppressWarnings("unused")
	private Main plugin;

	private static boolean disabled = true; // When true PVP is disabled

	int count = 0;

	public String[] lines = new String[] { "{attacker} tried to hit {victim}, but missed and hit themself instead.",
			"{attacker} swung for {victim}, but fell flat on their face in the process.",
			"{attacker} couldn't handle {victim}'s tekkers.", "{attacker} went for {victim}, but broke their wrist.",
			"{attacker} is never going to make it as a professional fighter after a performance like that against {victim}.",
			"{attacker} is never going to recover from the humiliation {victim} just caused them.",
			"{victim} hit {attacker} with subtle mind games.",
			"{attacker}'s performance against {victim} is going to get them both £250.",
			"{attacker} went for {victim} but {victim} was too quick.", "Stop hitting yourself, {attacker}.",
			"{attacker} gave themself a good spanking to save {victim} the effort.",
			"{attacker} may not have enjoyed that, but {victim} certainly did!",
			"Isn't {attacker} just embarrassing, {victim}?", "Nice try, {attacker}!",
			"{attacker} experienced {victim}'s magical forcefield.",
			"{attacker} experienced a perfectly elastic collision.",
			"{attacker}'s fist rebounded with no loss in kinetic energy.",
			"{attacker} got blocked while trying to steal {victim}'s $19 Fortnite card",
			"{attacker} McFailed to hurt {victim}", "You've got to McStop, {attacker}",
			"{attacker} tried to break Mc{victim}", "{attacker} experienced idiotic energy",
			"{attacker}'s ego fell from a high place",
			"{attacker} attempted to hurt {victim} but hit themself in their confusion",
			"{attacker} was slain by their own ambition", "{attacker} spiked their own drink by mistake",
			"{victim} judo-flipped {attacker}", "{victim} dabbed on {attacker}",
			"oWo, what's this? {attacker} stabbed themself", "{attacker} was glue, {victim} was rubber",
			"do u has brain, {attacker}", "{attacker} was the impostor", "{attacker} wuz teh impostor",
			"wut r u doin, {attacker}?", "{attacker} rolled a nat 1 to hit",
			"{victim} rolled a nat 20 to dodge {attacker}'s swing", "{attacker} iz nurd",
			"{attacker} took a calculated risk, but failed their GCSEs", "{attacker} yeeted themself",
			"{attacker} sent themself to the gulag",
			ChatColor.YELLOW + "{plugin}" + ChatColor.BLUE + " stopped {attacker} from hurting {victim}",
			"{attacker} is not pog", "{attacker} needs to be sent to tier 1 support",
			"{victim} sent shivers down {attacker}'s spine", "{victim} had the power of God AND anime on their side" };

	public static LinkedList<String> hit_messages = new LinkedList<String>();

	public MyListeners(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public String generateMessage(String attacker_name, String victim_name) {
		String out = attacker_name;
		int randomNum = (int) (Math.random() * lines.length);
		out = ChatColor.BLUE + lines[randomNum];
		out = out.replace("{attacker}", ChatColor.RED + attacker_name + ChatColor.BLUE).replace("{victim}",
				ChatColor.GREEN + victim_name + ChatColor.BLUE);
		if (hit_messages.size() == 8) {
			hit_messages.removeFirst();
		}
		hit_messages.addLast(out);
		return out;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void attack(EntityDamageByEntityEvent event) {
		if (disabled) {
			if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
				event.setCancelled(true);
				double damage = event.getDamage();
				Player attacker = (Player) event.getDamager();
				Player victim = (Player) event.getEntity();
				attacker.damage(damage);
				double new_health = victim.getHealth() + damage;
				if (new_health > victim.getMaxHealth()) {
					victim.setMaxHealth(new_health);
				}
				try {
					victim.setHealth(new_health);
				} catch (IllegalArgumentException e) {
					System.out.println("Encountered a non-fatal error: ");
					System.out.println(e.getMessage());
				}
				if (count % 2 == 0) {
					Bukkit.broadcastMessage(generateMessage(attacker.getDisplayName(), victim.getDisplayName()));
				}
				count++;
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void damage(EntityDamageEvent event) {
		if (disabled) {
			if (event.getEntity() instanceof Player && !(event instanceof EntityDamageByEntityEvent)) {
				Player p = (Player) event.getEntity();
				double damage = event.getFinalDamage();
				double new_health = p.getHealth() - damage;
				if (new_health < p.getMaxHealth()) {
					p.setMaxHealth(20.0 > new_health ? 20.0 : new_health);
				}
			}
		}
	}

	public static boolean isDisabled() {
		return disabled;
	}

	public static void setDisabled(boolean disabled) {
		MyListeners.disabled = disabled;
	}
}
