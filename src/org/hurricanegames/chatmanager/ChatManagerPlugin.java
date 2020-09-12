package org.hurricanegames.chatmanager;

import org.bukkit.plugin.java.JavaPlugin;
import org.hurricanegames.chatmanager.commands.ChatManagerAdminCommands;
import org.hurricanegames.chatmanager.commands.ChatManagerCommandHelper;
import org.hurricanegames.chatmanager.integrations.PlaceholderAPIIntergration;
import org.hurricanegames.chatmanager.integrations.VaultIntegration;
import org.hurricanegames.commandlib.commands.BukkitCommandExecutor;

public class ChatManagerPlugin extends JavaPlugin {

	private final ChatContainer container = new ChatContainer(this);

	@Override
	public void onEnable() {
		VaultIntegration.Hook.hook(this);
		PlaceholderAPIIntergration.Hook.hook(this);

		container.getConfig().reload();
		container.init();

		ChatManagerCommandHelper commandhelper = new ChatManagerCommandHelper(container);
		getCommand("chatmanageradmin").setExecutor(new BukkitCommandExecutor(new ChatManagerAdminCommands(commandhelper), ChatManagerPermissions.ADMIN));
	}

}
