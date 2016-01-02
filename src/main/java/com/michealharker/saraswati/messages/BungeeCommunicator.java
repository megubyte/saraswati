package com.michealharker.saraswati.messages;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
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
					BungeeMessage msg = new BungeeMessage(json);
					
					this.plugin.getLogger().log(Level.INFO, json.toString());
					this.plugin.getLogger().log(Level.INFO, msg.uuid.toString());
					this.plugin.getLogger().log(Level.INFO, msg.message);
					this.plugin.getLogger().log(Level.INFO, msg.type.toString());
					
					switch (msg.type) {
					default:
						this.plugin.getLogger().log(Level.WARNING, "I've received a message on the plugin channel that I'm not set up to handle. Have you updated me?");
						break;
					case PLAYER_MESSAGE:
					case PLAYER_ME:
						if (!this.received.contains(msg) && msg.ts >= System.currentTimeMillis() - 250) {	
							this.plugin.getChat().sendMessageToAllPlayers(msg.message);
							
							this.received.add(msg);
						}
						break;
					case PLAYER_MUTE:
						if ((boolean) msg.extra) {
							this.plugin.getMuteManager().addMute(msg.uuid);
						} else {
							this.plugin.getMuteManager().removeMute(msg.uuid);
						}
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
