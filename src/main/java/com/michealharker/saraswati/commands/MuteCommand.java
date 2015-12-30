package com.michealharker.saraswati.commands;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.michealharker.saraswati.Plugin;

import net.md_5.bungee.api.ChatColor;

public class MuteCommand implements CommandExecutor {

	private Plugin plugin;

	public MuteCommand(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		OfflinePlayer pl;
		
		if (args.length >= 1) {
			if (!sender.hasPermission("saraswati.mute")) {
				sender.sendMessage(ChatColor.GRAY + "You don't have permission to do this.");
				return false;
			}
			
			if ((pl = Bukkit.getOfflinePlayer(args[0])) != null) {
				this.plugin.getLogger().log(Level.INFO, pl.getUniqueId().toString());
				if (this.plugin.getMuteManager().isMuted(pl.getUniqueId())) {
					this.plugin.getMuteManager().removeMute(pl.getUniqueId());
					sender.sendMessage(ChatColor.GRAY + pl.getName() + " has been unmuted.");
				} else {
					this.plugin.getMuteManager().addMute(pl.getUniqueId());
					sender.sendMessage(ChatColor.GRAY + pl.getName() + " has been muted.");
				}
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "This user can't be found.");
				return false;
			}
		}
		
		return false;
	}

}
