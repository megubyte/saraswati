package com.michealharker.saraswati.chat;

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
		
		if (this.plugin.getMuteManager().isMuted(e.getPlayer().getUniqueId())) {
			e.getPlayer().sendMessage(ChatColor.DARK_GRAY + "You've been muted from speaking publically. You can /msg other players and server operators.");
			return;
		} else if (this.plugin.getChat().isModerated()) {
			e.getPlayer().sendMessage(ChatColor.DARK_GRAY + "This server is currently in moderation mode. You can't speak, but can /msg other players and server operators.");
			return;
		}
		
		String message = this.format(e.getMessage(), e.getPlayer());
		
		this.plugin.getBungeeLink().sendMessage(BungeeMessage.buildMessage(e.getPlayer().getUniqueId(), message, BungeeMessageType.PLAYER_MESSAGE));
		
		this.plugin.getChat().sendMessageToAllPlayers(message);
	}

	private String format(String message, Player player) {
		String format = this.plugin.getConfig().getString("msg-format", "{prefix}{player}{suffix}&r: {message}");
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
