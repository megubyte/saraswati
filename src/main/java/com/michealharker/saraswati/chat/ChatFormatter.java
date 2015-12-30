package com.michealharker.saraswati.chat;

import org.bukkit.entity.Player;

public class ChatFormatter {
	public static String translate(String message, Player player) {
		if (player == null || !player.hasPermission("saraswati.format.colors")) {
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
		
		if (player == null || !player.hasPermission("saraswati.format.special")) {
			message = message.replace("&l", "");
			message = message.replace("&m", "");
			message = message.replace("&n", "");
			message = message.replace("&o", "");
			message = message.replace("&r", "");
		}
		
		if (player == null || !player.hasPermission("saraswati.format.obfuscator")) {
			message = message.replace("&k", "");
		}
		
		return message;
	}
	
	public static String strip(String message) {
		return ChatFormatter.translate(message, null);
	}
}
