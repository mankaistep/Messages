package mk.plugin.messages.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import mk.plugin.messages.main.MainMessages;

public class AdminCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		try {
			if (args[0].equalsIgnoreCase("reload")) {
				MainMessages.get().reloadConfigs();
				sender.sendMessage("§aConfiguration reloaded!");
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			sendHelp(sender);
		}
		
		return false;
	}

	public void sendHelp(CommandSender sender) {
		sender.sendMessage("");
		sender.sendMessage("§2/messages reload: §aReload plugin");
		sender.sendMessage("");
	}
	
}
