package com.michealharker.saraswati;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.michealharker.saraswati.chat.ChatListener;
import com.michealharker.saraswati.chat.DisableJoinQuit;
import com.michealharker.saraswati.commands.MeCommand;
import com.michealharker.saraswati.commands.MuteCommand;
import com.michealharker.saraswati.messages.BungeeCommunicator;

import net.milkbowl.vault.chat.Chat;

public class Plugin extends JavaPlugin {
	private RegisteredServiceProvider<Chat> chat;
	private BungeeCommunicator bungee;
	private MuteManager mutes;
	
	private boolean setupVault() {
		RegisteredServiceProvider<Chat> provider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
		if (provider != null) this.chat = provider;
		return (this.chat != null);
	}
	
	public RegisteredServiceProvider<Chat> getVaultChat() {
		return this.chat;
	}
	
	public MuteManager getMuteManager() {
		return this.mutes;
	}
	
	public BungeeCommunicator getBungeeLink() {
		return this.bungee;
	}
	
	public File getFile(String filename) throws IOException {
		File f = new File(getDataFolder(), filename);
		
		if (!getDataFolder().exists()) getDataFolder().mkdirs();
		if (!f.exists()) f.createNewFile();
		
		return f;
	}
	
	public void onEnable() {
		if (!this.setupVault()) {
			Bukkit.getLogger().log(Level.INFO, "You don't have Vault installed. Because of this i'll not hook into permissions.");
		}
		
		try {
			this.getFile("mutes.json");
			// this.createFile("ignores.json");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(this.getFile("mutes.json")))) {
			String data = "";
			String line;
			
			while ((line = br.readLine()) != null) {
				data += line;
			}
			
			this.mutes = new MuteManager(data);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		this.bungee = new BungeeCommunicator(this);
		
		getCommand("me").setExecutor(new MeCommand(this));
		getCommand("mute").setExecutor(new MuteCommand(this));
		
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this.getBungeeLink());
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		getServer().getPluginManager().registerEvents(new DisableJoinQuit(), this);
	}
	
	public void onDisable() {
		String jsonMutes = this.mutes.serialize();
		
		try (PrintWriter fw = new PrintWriter(this.getFile("mutes.json"), "UTF-8")) {
			fw.println(jsonMutes);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
