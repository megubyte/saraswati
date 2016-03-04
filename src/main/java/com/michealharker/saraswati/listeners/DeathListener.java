package com.michealharker.saraswati.listeners;

import com.michealharker.saraswati.Plugin;
import com.michealharker.saraswati.messages.BungeeMessage;
import com.michealharker.saraswati.messages.BungeeMessageType;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    private Plugin plugin;

    public DeathListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority= EventPriority.NORMAL)
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity().getGameMode() == GameMode.CREATIVE) {
            e.setDeathMessage(e.getEntity().getName() + " suicided");
        }

        BungeeMessage bm = new BungeeMessage(e.getEntity().getUniqueId(), e.getDeathMessage(), BungeeMessageType.MISC, null);
        this.plugin.getBungeeLink().sendMessage(bm.buildMessage());
    }
}
