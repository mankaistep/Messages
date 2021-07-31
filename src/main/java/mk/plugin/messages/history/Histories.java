package mk.plugin.messages.history;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import mk.plugin.messages.update.Update;
import mk.plugin.messages.yaml.YamlFile;

public class Histories {
	
	public static void write(Plugin plugin, Update u) {
		FileConfiguration config = YamlFile.HISTORY.get();
		String vID = "v" + u.getVersion().replace(".", "-");
		config.set(vID, u.getInfo());
		YamlFile.HISTORY.save(plugin);
	}
	
}
