package com.michealharker.saraswati.chat;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.michealharker.saraswati.Plugin;

import net.md_5.bungee.api.ChatColor;

public class ChatManager {
	private Plugin plugin;

	public ChatManager(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public void sendMessageToAllPlayers(String message) {
		for (final Player pl : this.plugin.getServer().getOnlinePlayers()) {
			String msg;
		
			if (message.toLowerCase().contains("@" + pl.getName().toLowerCase())) {
				msg = message.replaceAll("(?i)@" + pl.getName(), ChatColor.YELLOW + "@" + pl.getName() + ChatColor.RESET);
				
				this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
					@Override
					public void run() {
						pl.playSound(pl.getLocation(), Sound.SUCCESSFUL_HIT, 1.5f, 0.8f);
					}
				}, 2L);
				this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
					@Override
					public void run() {
						pl.playSound(pl.getLocation(), Sound.SUCCESSFUL_HIT, 1.5f, 0.9f);
					}
				}, 4L);
				this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
					@Override
					public void run() {
						pl.playSound(pl.getLocation(), Sound.SUCCESSFUL_HIT, 1.5f, 1.0f);
					}
				}, 6L);
			} else {
				msg = message;
			}
			
			pl.sendMessage(msg);
		}
	}
}
