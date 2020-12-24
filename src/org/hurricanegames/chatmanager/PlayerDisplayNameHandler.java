package org.hurricanegames.chatmanager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.hurricanegames.chatmanager.integrations.PlaceholderAPIIntergration;
import org.hurricanegames.chatmanager.integrations.VaultIntegration;
import org.hurricanegames.pluginlib.utils.bukkit.MiscBukkitUtils;

public class PlayerDisplayNameHandler implements Listener {

	protected final ChatManagerConfig config;

	public PlayerDisplayNameHandler(ChatManagerConfig config) {
		this.config = config;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	protected void onJoin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		player.setDisplayName(PlaceholderAPIIntergration.setBracketPlaceholders(player, MiscBukkitUtils.colorize(config.getDisplayNameFormat(VaultIntegration.<String>withPermissions(perms -> perms.getPrimaryGroup(player), null)))));
	}

}
