package com.michealharker.saraswati.messages;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.michealharker.saraswati.Plugin;

public class BungeeCommunicator implements PluginMessageListener {
	private Plugin plugin;
	private ArrayList<BungeeMessage> received;

	public BungeeCommunicator(Plugin plugin) {
		this.plugin = plugin;
		this.received = new ArrayList<BungeeMessage>();
	}
	
	public void sendMessage(byte[] message) {
		Player pl = Bukkit.getOnlinePlayers().iterator().next();
		pl.sendPluginMessage(this.plugin, "BungeeCord", message);
	}

	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		try {
			if (Bukkit.getOnlinePlayers().size() > 0) {
				DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
				String subchannel = in.readUTF();
				
				if (subchannel.equals("Saraswati")) {
					short len = in.readShort();
					byte[] data = new byte[len];
					in.readFully(data);
					
					DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(data));
					
					String json = msgin.readUTF();
					
					Bukkit.getLogger().log(Level.INFO, json);
					
					BungeeMessage msg = new BungeeMessage(json);
					
					Bukkit.getLogger().log(Level.INFO, msg.uuid.toString());
					Bukkit.getLogger().log(Level.INFO, msg.message);
					
					if (!this.received.contains(msg)) {	
						this.plugin.getChat().sendMessageToAllPlayers(msg.message);
						
						this.received.add(msg);
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
