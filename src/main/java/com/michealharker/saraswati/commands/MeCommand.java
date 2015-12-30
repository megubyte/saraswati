package com.michealharker.saraswati.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.michealharker.saraswati.Plugin;
import com.michealharker.saraswati.chat.ChatFormatter;
import com.michealharker.saraswati.messages.BungeeMessage;
import com.michealharker.saraswati.messages.BungeeMessageType;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;

public class MeCommand implements CommandExecutor {
	
	private Plugin plugin;

	public MeCommand(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Chat chat = this.plugin.getVaultChat().getProvider();
			String message = ChatFormatter.strip(StringUtils.join(args, " "));
			String format = this.plugin.getConfig().getString("me-format");
			
			format = format.replace("{player}", sender.getName());
			
			if (chat != null) {
				format = format.replace("{prefix}", chat.getPlayerPrefix((Player) sender));
				format = format.replace("{suffix}", chat.getPlayerSuffix((Player) sender));
			} else {
				format = format.replace("{prefix}", "");
				format = format.replace("{suffix}", "");
			}
			
			format = format.replace("{message}", message);
			format = ChatColor.translateAlternateColorCodes('&', format);
			
			byte[] data = BungeeMessage.buildMessage(((Player) sender).getUniqueId(), format, BungeeMessageType.PLAYER_ME);
			this.plugin.getBungeeLink().sendMessage(data);
			
			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.sendMessage(format);
			}
			
			return true;
		} else {
			sender.sendMessage("Please, don't try this from the console.");
			
			return false;
		}
	}

}
