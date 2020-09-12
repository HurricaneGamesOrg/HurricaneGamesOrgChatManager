package org.hurricanegames.chatmanager;

public class ChatManagerPermissions {

	private static final String root = "hgo.chatmanager";
	private static final String suffix_admin = ".admin";

	public static final String ADMIN = root + suffix_admin;

	private static final String section_chat = root + ".chat";

	public static final String CHAT_UNICODE = section_chat + ".unicode";
	public static final String CHAT_COLOR = section_chat + ".color";

}
