package mk.plugin.messages.listener;

import mk.plugin.messages.main.MainMessages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class EventListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();

		if (!player.hasPlayedBefore()) {
			var plugin = MainMessages.get();
			new BukkitRunnable() {

				Location l = null;

				@Override
				public void run() {
					if (l == null) l = e.getPlayer().getLocation();
					if (l.getWorld() != player.getWorld()) {
						this.cancel();
						return;
					}
					if (l.distance(player.getLocation()) >= plugin.getWelcomeRadius()) {
						this.cancel();

						int time = plugin.getTitles().size() * 35;
						Bukkit.getScheduler().runTask(plugin, () -> {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, 10, false, false, false));
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, 10, false, false, false));
						});


						int c = 0;
						for (String s : plugin.getTitles()) {
							Bukkit.getScheduler().runTaskLaterAsynchronously(MainMessages.get(), () -> {
								var t = s.split(";")[0];
								var st = s.split(";")[1];
								player.sendTitle(t.replace("%player%", player.getName()), st.replace("%player%", player.getName()), 0, 200, 0);
								player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
							}, 55L * c);
							c++;
						}
					}
				}
			}.runTaskTimerAsynchronously(plugin, 5, 10);
		}

		else Bukkit.getScheduler().runTaskLaterAsynchronously(MainMessages.get(), () -> {
			MainMessages.get().showUpdate(player);
		}, 100);
	}
	
}
