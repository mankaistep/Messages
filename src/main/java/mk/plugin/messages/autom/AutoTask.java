package mk.plugin.messages.autom;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoTask extends BukkitRunnable {
	
	public static void start(Plugin plugin) {
		new AutoTask(plugin);
	}
	
	private AutoTask(Plugin plugin) {
		this.runTaskTimerAsynchronously(plugin, 0, AutoMessages.COOLDOWN * 20);
	}

	int i = -1;
	
	@Override
	public void run() {
		i++;
		int index = i % AutoMessages.MESSAGES.size();
		Bukkit.getOnlinePlayers().forEach(player -> {
			AutoMessages.show(player, index);
		});

	}
	
}
