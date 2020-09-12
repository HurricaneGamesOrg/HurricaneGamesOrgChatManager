package org.hurricanegames.chatmanager.integrations;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import me.clip.placeholderapi.PlaceholderAPI;

public class PlaceholderAPIIntergration implements Listener {

	private static final PlaceholderAPIIntergration instance = new PlaceholderAPIIntergration();

	public static final PlaceholderAPIIntergration getInstance() {
		return instance;
	}

	protected boolean enabled = false;

	public static String setBracketPlaceholders(Player player, String text) {
		if (instance.enabled) {
			return PlaceholderAPI.setBracketPlaceholders(player, text);
		} else {
			return text;
		}
	}


	public static class Hook implements Listener {

		private static final AtomicBoolean init = new AtomicBoolean(false);

		public static void hook(Plugin plugin) {
			if (init.compareAndSet(false, true)) {
				Hook hook = new Hook(plugin);
				hook.updateHook();
				plugin.getServer().getPluginManager().registerEvents(hook, plugin);
			}
		}

		protected final Plugin plugin;

		protected Hook(Plugin plugin) {
			this.plugin = plugin;
		}

		protected void updateHook() {
			boolean oldEnabled = instance.enabled;
			try {
				plugin.getClass().getClassLoader().loadClass(PlaceholderAPI.class.getName());
				instance.enabled = true;
			} catch (Throwable t) {
				instance.enabled = false;
			}
			if (oldEnabled != instance.enabled) {
				if (instance.enabled) {
					plugin.getLogger().log(Level.INFO, "Enabled PlaceholderAPI integration");
				} else {
					plugin.getLogger().log(Level.INFO, "Disabled PlaceholderAPI integration");
				}
			}
		}

		@EventHandler
		protected void onPluginEnable(PluginEnableEvent event) {
			updateHook();
		}

		@EventHandler
		protected void onPluginDisable(PluginDisableEvent event) {
			updateHook();
		}

	}

}
