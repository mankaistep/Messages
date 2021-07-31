package mk.plugin.messages.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import mk.plugin.messages.autom.AutoMessages;
import mk.plugin.messages.autom.AutoTask;
import mk.plugin.messages.command.AdminCommand;
import mk.plugin.messages.history.Histories;
import mk.plugin.messages.listener.EventListener;
import mk.plugin.messages.update.Update;
import mk.plugin.messages.yaml.YamlFile;

import java.util.List;
import java.util.stream.Collectors;

public class MainMessages extends JavaPlugin {
	
	private Update update;

	private double welcomeRadius;
	private List<String> titles;
	
	@Override
	public void onEnable() {
		this.reloadConfigs();
		this.getCommand("messages").setExecutor(new AdminCommand());
		Bukkit.getPluginManager().registerEvents(new EventListener(), this);
	}
	
	public void reloadConfigs() {
		YamlFile.reloadAll(this);
		
		// Config
		AutoMessages.COOLDOWN = YamlFile.CONFIG.get().getInt("auto-message.cooldown");
		AutoMessages.PREFIX = YamlFile.CONFIG.get().getString("auto-message.prefix").replace("&", "§");
		AutoMessages.MESSAGES = YamlFile.CONFIG.get().getStringList("auto-message.messages");
		
		// Update
		this.update = new Update(YamlFile.CONFIG.get().getString("update.version"), YamlFile.CONFIG.get().getString("update.server-name"), YamlFile.CONFIG.get().getStringList("update.info"));
		
		// Write history
		System.out.println("[Messages] Writing update v" + this.update.getVersion() + "...");
		Histories.write(this, this.update);
		
		// Run auto message
		AutoTask.start(this);

		// Welcome
		this.welcomeRadius = getConfig().getInt("welcome-title.radius");
		this.titles = getConfig().getStringList("welcome-title.titles").stream().map(s -> s.replaceAll("&", "§")).collect(Collectors.toList());
	}
	
	public void showUpdate(Player player) {
		this.update.show(player);
	}

	public double getWelcomeRadius() {
		return welcomeRadius;
	}

	public List<String> getTitles() {
		return titles;
	}

	public static MainMessages get() {
		return MainMessages.getPlugin(MainMessages.class);
	}
	
}
