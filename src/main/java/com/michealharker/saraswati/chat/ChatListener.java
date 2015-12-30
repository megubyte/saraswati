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
		
		message = ChatFormatter.translate(message, player);
		
		format = format.replace("{message}", message);
		
		return ChatColor.translateAlternateColorCodes('&', format);
	}
}
