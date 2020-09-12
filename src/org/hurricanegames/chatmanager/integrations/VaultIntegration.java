package org.hurricanegames.chatmanager.integrations;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServiceRegisterEvent;
import org.bukkit.event.server.ServiceUnregisterEvent;
import org.bukkit.plugin.Plugin;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultIntegration implements Listener {

	private static final VaultIntegration instance = new VaultIntegration();

	public static VaultIntegration getInstance() {
		return instance;
	}

	public static void withPermissions(Consumer<Permission> c) {
		if (instance.permission != null) {
			c.accept(instance.permission);
		}
	}

	public static <T> T withPermissions(Function<Permission, T> f, T defaultValue) {
		if (instance.permission != null) {
			return f.apply(instance.permission);
		} else {
			return defaultValue;
		}
	}

	public static void withEconomy(Consumer<Economy> c) {
		if (instance.economy != null) {
			c.accept(instance.economy);
		}
	}

	public static <T> T withEconomy(Function<Economy, T> f, T defaultValue) {
		if (instance.economy != null) {
			return f.apply(instance.economy);
		} else {
			return defaultValue;
		}
	}

	public static void withChat(Consumer<Chat> c) {
		if (instance.chat != null) {
			c.accept(instance.chat);
		}
	}

	public static <T> T withChat(Function<Chat, T> f, T defaultValue) {
		if (instance.chat != null) {
			return f.apply(instance.chat);
		} else {
			return defaultValue;
		}
	}

	private Permission permission;
	private Economy economy;
	private Chat chat;

	public Permission getPermission() {
		return permission;
	}

	public Economy getEconomy() {
		return economy;
	}

	public Chat getChat() {
		return chat;
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
			try {
				Economy economy = Bukkit.getServicesManager().load(Economy.class);
				if (economy != instance.economy) {
					instance.economy = economy;
					plugin.getLogger().log(Level.INFO, "Vault Economy integration set to " + economy.getClass().getName());
				}
			} catch (Throwable t) {
				if (instance.economy != null) {
					instance.economy = null;
					plugin.getLogger().log(Level.INFO, "Vault Economy integration disabled because of error " + t.getMessage());
				}
			}

			try {
				Permission permission = Bukkit.getServicesManager().load(Permission.class);
				if (!permission.hasGroupSupport()) {
					throw new IllegalStateException("Permission implementation " + permission.getClass().getName() + " doesn't have groups support");
				}
				if (permission != instance.permission) {
					instance.permission = permission;
					plugin.getLogger().log(Level.INFO, "Vault Permission integration set to " + permission.getClass().getName());
				}
			} catch (Throwable t) {
				if (instance.permission != null) {
					instance.permission = null;
					plugin.getLogger().log(Level.INFO, "Vault Permission integration disabled because of error " + t.getMessage());
				}
			}

			try {
				Chat chat = Bukkit.getServicesManager().load(Chat.class);
				if (chat != instance.chat) {
					instance.chat = chat;
					plugin.getLogger().log(Level.INFO, "Vault Chat integration set to " + chat.getClass().getName());
				}
			} catch (Throwable t) {
				if (instance.chat != null) {
					instance.chat = null;
					plugin.getLogger().log(Level.INFO, "Vault Chat integration disabled because of error " + t.getMessage());
				}
			}
		}

		@EventHandler
		protected void onPluginEnable(ServiceRegisterEvent event) {
			updateHook();
		}

		@EventHandler
		protected void onPluginDisable(ServiceUnregisterEvent event) {
			updateHook();
		}

	}

}
