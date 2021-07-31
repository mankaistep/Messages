package mk.plugin.messages.autom;

import java.util.List;

import org.bukkit.entity.Player;

public class AutoMessages {
	
	public static int COOLDOWN;
	public static String PREFIX;
	public static List<String> MESSAGES;
	
	public static void show(Player player, int index) {
		player.sendMessage(PREFIX + MESSAGES.get(index));
	}
	
}
