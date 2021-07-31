package mk.plugin.messages.update;

import java.util.List;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Update {
	
	private String version;
	private String server;
	private List<String> info;
	
	public Update(String version, String server, List<String> info) {
		this.version = version;
		this.server = server;
		this.info = info;
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public String getServer() {
		return this.server;
	}
	
	public List<String> getInfo() {
		return this.info;
	}
	
	public void show(Player player) {
		// Sound
		player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
		
		// Title
		player.sendTitle("§2§l§o" + server.toUpperCase(), "Patch " + this.version, 10, 80, 10);
		
		// Message
		player.sendMessage("");
		player.sendMessage("§7§m                                             §r");
		player.sendMessage("§6§lPhiên bản " + this.version);
		int c = 0;
		for (String m : this.info) {
			c++;
			player.sendMessage("§6§l" + c + ". §e" + m);
		}
		player.sendMessage("§7§m                                             §r");
		player.sendMessage("");
	}
	
}
