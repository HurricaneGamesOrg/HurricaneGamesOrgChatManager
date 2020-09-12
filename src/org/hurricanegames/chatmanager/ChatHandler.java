package org.hurricanegames.chatmanager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.hurricanegames.chatmanager.integrations.PlaceholderAPIIntergration;
import org.hurricanegames.chatmanager.integrations.VaultIntegration;
import org.hurricanegames.commandlib.utils.MiscBukkitUtils;

public class ChatHandler implements Listener {

	protected final ChatManagerConfig config;

	public ChatHandler(ChatManagerConfig config) {
		this.config = config;
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		event.setFormat(PlaceholderAPIIntergration.setBracketPlaceholders(player, MiscBukkitUtils.colorize(config.getChatFormat(VaultIntegration.withPermissions(perms -> perms.getPrimaryGroup(player), null)))));
		if (!player.hasPermission(ChatManagerPermissions.CHAT_UNICODE)) {
			event.setMessage(config.filterMessage(event.getMessage()));
		}
		if (player.hasPermission(ChatManagerPermissions.CHAT_COLOR)) {
			event.setMessage(MiscBukkitUtils.colorize(event.getMessage()));
		}
	}

}
