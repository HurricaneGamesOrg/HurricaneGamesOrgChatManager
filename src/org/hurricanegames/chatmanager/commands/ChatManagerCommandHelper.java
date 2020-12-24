package org.hurricanegames.chatmanager.commands;

import org.hurricanegames.chatmanager.ChatContainer;
import org.hurricanegames.chatmanager.ChatManagerPlugin;
import org.hurricanegames.pluginlib.commands.CommandHelper;
import org.hurricanegames.pluginlib.commands.CommandMessages;
import org.hurricanegames.pluginlib.configurations.builtin.DefaultCommandMessages;
import org.hurricanegames.pluginlib.playerinfo.BukkitPlayerInfo;
import org.hurricanegames.pluginlib.playerinfo.BukkitPlayerInfoProvider;

public class ChatManagerCommandHelper extends CommandHelper<ChatManagerPlugin, CommandMessages, BukkitPlayerInfo, BukkitPlayerInfoProvider> {

	protected final ChatContainer container;

	public ChatManagerCommandHelper(ChatContainer container) {
		super(container.getPlugin(), DefaultCommandMessages.IMMUTABLE, BukkitPlayerInfoProvider.INSTANCE);
		this.container = container;
	}

	public ChatContainer getContainer() {
		return container;
	}

}
