package org.hurricanegames.chatmanager.commands;

import org.hurricanegames.chatmanager.ChatContainer;
import org.hurricanegames.commandlib.commands.CommandHelper;
import org.hurricanegames.commandlib.commands.CommandMessages;
import org.hurricanegames.commandlib.providers.messages.DefaultMessages;
import org.hurricanegames.commandlib.providers.playerinfo.BukkitPlayerInfo;
import org.hurricanegames.commandlib.providers.playerinfo.BukkitPlayerInfoProvider;

public class ChatManagerCommandHelper extends CommandHelper<CommandMessages, BukkitPlayerInfo, BukkitPlayerInfoProvider> {

	protected final ChatContainer container;

	public ChatManagerCommandHelper(ChatContainer container) {
		super(DefaultMessages.IMMUTABLE, BukkitPlayerInfoProvider.INSTANCE);
		this.container = container;
	}

	public ChatContainer getContainer() {
		return container;
	}

}
