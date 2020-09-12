package org.hurricanegames.chatmanager;

import java.io.File;

import org.bukkit.plugin.Plugin;

public class ChatContainer {

	protected final Plugin plugin;
	protected final ChatConfig config;

	public ChatContainer(Plugin plugin) {
		this.plugin = plugin;
		this.config = new ChatConfig(new File(plugin.getDataFolder(), "config.yml"));
	}

	private boolean init;

	public void init() {
		if (init) {
			throw new IllegalStateException("Already initialized");
		}
		init = true;

		plugin.getServer().getPluginManager().registerEvents(new PlayerDisplayNameHandler(config), plugin);
		plugin.getServer().getPluginManager().registerEvents(new ChatHandler(config), plugin);
	}

	public ChatConfig getConfig() {
		return config;
	}

}