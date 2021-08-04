package mk.plugin.messages.listener;

import com.google.common.collect.Sets;
import mk.plugin.messages.main.MainMessages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class EventListener implements Listener {

	public Set<String> freezed;

	private final long WAIT = 55;

	public EventListener() {
		this.freezed = Sets.newHashSet();
	}

	@EventHandler
	public void onJump(PlayerToggleFlightEvent e) {
		var p = e.getPlayer();
		if (freezed.contains(p.getName())) {
			e.setCancelled(true);
		}
	}

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

						freezed.add(player.getName());
						int time = Long.valueOf(plugin.getTitles().size() * WAIT).intValue();

						Bukkit.getScheduler().runTaskLater(plugin, () -> {
							freezed.remove(player.getName());
						}, time);

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
							}, WAIT * c);
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
