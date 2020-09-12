package org.hurricanegames.chatmanager.commands;

import org.hurricanegames.commandlib.commands.CommandRouter;
import org.hurricanegames.commandlib.providers.commands.SimpleConfigurationReloadCommand;

public class ChatManagerAdminCommands extends CommandRouter<ChatManagerCommandHelper> {

	public ChatManagerAdminCommands(ChatManagerCommandHelper helper) {
		super(helper);
		addCommand("reloadcfg", new SimpleConfigurationReloadCommand<>(helper, helper.getContainer().getConfig(), () -> "main"));
	}

}
