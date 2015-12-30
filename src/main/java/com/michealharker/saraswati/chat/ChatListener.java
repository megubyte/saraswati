package com.michealharker.saraswati.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.michealharker.saraswati.Plugin;
import com.michealharker.saraswati.messages.BungeeMessage;
import com.michealharker.saraswati.messages.BungeeMessageType;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;

public class ChatListener implements Listener {
	private Plugin plugin;

	public ChatListener(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		
		String message = this.format(e.getMessage(), e.getPlayer());
		
		this.plugin.getBungeeLink().sendMessage(BungeeMessage.buildMessage(e.getPlayer().getUniqueId(), message, BungeeMessageType.PLAYER_MESSAGE));
		
		for (final Player pl : Bukkit.getOnlinePlayers()) {
			// TODO: Player /ignores, /mutes
			pl.sendMessage(message);
		}
	}

	private String format(String message, Player player) {
		String format = this.plugin.getConfig().getString("msg-format", "{prefix}{player}{suffix}: {message}");
		Chat chat = this.plugin.getVaultChat().getProvider();
		
		format = format.replace("{player}", player.getName());
		
		if (chat != null) {
			format = format.replace("{prefix}", chat.getPlayerPrefix(player));
			format = format.replace("{suffix}", chat.getPlayerSuffix(player));
		} else {
			format = format.replace("{prefix}", "");
			format = format.replace("{suffix}", "");
		}
		
		if (!player.hasPermission("saraswati.format.colors")) {
			message = message.replace("&0", "");
			message = message.replace("&1", "");
			message = message.replace("&2", "");
			message = message.replace("&3", "");
			message = message.replace("&4", "");
			message = message.replace("&5", "");
			message = message.replace("&6", "");
			message = message.replace("&7", "");
			message = message.replace("&8", "");
			message = message.replace("&9", "");
			message = message.replace("&a", "");
			message = message.replace("&b", "");
			message = message.replace("&c", "");
			message = message.replace("&d", "");
			message = message.replace("&e", "");
			message = message.replace("&f", "");
		}
		
		if (!player.hasPermission("saraswati.format.special")) {
			message = message.replace("&l", "");
			message = message.replace("&m", "");
			message = message.replace("&n", "");
			message = message.replace("&o", "");
			message = message.replace("&r", "");
		}
		
		if (!player.hasPermission("saraswati.format.obfuscator")) {
			message = message.replace("&k", "");
		}
		
		format = format.replace("{message}", message);
		
		return ChatColor.translateAlternateColorCodes('&', format);
	}
}
