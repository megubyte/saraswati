package com.michealharker.saraswati.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DisableJoinQuit implements Listener {
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage("");
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage("");
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onKick(PlayerKickEvent e) {
		e.setLeaveMessage("");
	}
}
