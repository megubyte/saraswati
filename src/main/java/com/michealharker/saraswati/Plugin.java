package com.michealharker.saraswati;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.michealharker.saraswati.chat.ChatListener;
import com.michealharker.saraswati.chat.DisableJoinQuit;
import com.michealharker.saraswati.commands.MeCommand;
import com.michealharker.saraswati.messages.BungeeCommunicator;

import net.milkbowl.vault.chat.Chat;

public class Plugin extends JavaPlugin {
	private RegisteredServiceProvider<Chat> chat;
	private BungeeCommunicator bungee;
	
	private boolean setupVault() {
		RegisteredServiceProvider<Chat> provider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
		if (provider != null) this.chat = provider;
		return (this.chat != null);
	}
	
	public RegisteredServiceProvider<Chat> getVaultChat() {
		return this.chat;
	}
	
	public BungeeCommunicator getBungeeLink() {
		return this.bungee;
	}
	
	public void onEnable() {
		if (!this.setupVault()) {
			Bukkit.getLogger().log(Level.INFO, "You don't have Vault installed. Because of this i'll not hook into permissions.");
		}
		
		this.bungee = new BungeeCommunicator(this);
		
		getCommand("me").setExecutor(new MeCommand(this));
		
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this.getBungeeLink());
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		getServer().getPluginManager().registerEvents(new DisableJoinQuit(), this);
	}
	
	public void onDisable() {
		
	}
}
