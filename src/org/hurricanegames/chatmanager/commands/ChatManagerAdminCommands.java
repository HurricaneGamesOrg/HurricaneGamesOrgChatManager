package org.hurricanegames.chatmanager.commands;

import org.hurricanegames.pluginlib.commands.CommandRouter;
import org.hurricanegames.pluginlib.commands.builtin.ListPluginPermissionsCommand;
import org.hurricanegames.pluginlib.commands.builtin.RootConfigurationReloadCommand;

public class ChatManagerAdminCommands extends CommandRouter<ChatManagerCommandHelper> {

	public ChatManagerAdminCommands(ChatManagerCommandHelper helper) {
		super(helper);
		addCommand("permissions", new ListPluginPermissionsCommand<>(helper));
		addCommand("reloadcfg", new RootConfigurationReloadCommand<>(helper, helper.getContainer().getConfig(), () -> "main"));
	}

}
