package org.hurricanegames.chatmanager;

import java.io.File;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hurricanegames.pluginlib.configurations.annotated.AnnotatedConfiguration;
import org.hurricanegames.pluginlib.configurations.annotated.AnnotatedRootYamlConfiguration;
import org.hurricanegames.pluginlib.configurations.typeserializers.ColorizedStringTypeSerializer;
import org.hurricanegames.pluginlib.configurations.typeserializers.StringTypeSerializer;
import org.hurricanegames.pluginlib.configurations.typeserializers.TypeSerializer;

public class ChatManagerConfig extends AnnotatedRootYamlConfiguration {

	public ChatManagerConfig(File storageFile) {
		super(storageFile);
	}

	@ConfigurationFieldDefinition(fieldName = "displayname.format.default", fieldType = SimpleColorizedStringConfigurationField.class)
	protected String displayNameFormatDefault = "{vault_prefix}{player_name}{vault_suffix}&r";

	@ConfigurationFieldDefinition(fieldName = "displayname.format.group", fieldType = MapStringColorizedStringConfigurationField.class)
	protected final Map<String, String> displayNameFormatGroup = new HashMap<>();

	@ConfigurationFieldDefinition(fieldName = "chat.allowed_unicode_blocks", fieldType = SetUnicodeBlockConfigurationField.class)
	protected Set<UnicodeBlock> chatAllowedUnicodeBlocks = new HashSet<>(Arrays.asList(UnicodeBlock.BASIC_LATIN));

	@ConfigurationFieldDefinition(fieldName = "chat.format.default", fieldType = SimpleColorizedStringConfigurationField.class)
	protected String chatFormatDefault = "%s: %s";

	@ConfigurationFieldDefinition(fieldName = "chat.format.group", fieldType = MapStringColorizedStringConfigurationField.class)
	protected final Map<String, String> chatFormatGroup = new HashMap<>();

	public String getDisplayNameFormat(String group) {
		return displayNameFormatGroup.getOrDefault(group, displayNameFormatDefault);
	}

	public String getChatFormat(String group) {
		return chatFormatGroup.getOrDefault(group, chatFormatDefault);
	}

	public String filterMessage(String message) {
		return
			message.chars()
			.filter(cp -> chatAllowedUnicodeBlocks.contains(UnicodeBlock.of(cp)))
			.collect(
				StringBuilder::new,
				StringBuilder::appendCodePoint,
				StringBuilder::append
			)
			.toString();
	}

	protected static class MapStringColorizedStringConfigurationField<O extends AnnotatedConfiguration> extends SimpleMapConfigurationField<O, String, String> {

		public MapStringColorizedStringConfigurationField(O configuration, Field field, String path) {
			super(configuration, field, path, StringTypeSerializer.INSTANCE, ColorizedStringTypeSerializer.INSTANCE);
		}

	}

	protected static class SetUnicodeBlockConfigurationField<O extends AnnotatedConfiguration> extends SimpleSetConfigurationField<O, UnicodeBlock> {

		public SetUnicodeBlockConfigurationField(O configuration, Field field, String path) {
			super(configuration, field, path, new TypeSerializer<UnicodeBlock>() {
				@Override
				public UnicodeBlock deserialize(Object object) {
					if (object instanceof UnicodeBlock) {
						return (UnicodeBlock) object;
					}
					if (object instanceof String) {
						try {
							return UnicodeBlock.forName((String) object);
						} catch (IllegalArgumentException e) {
							return null;
						}
					}
					return null;
				}
				@Override
				public Object serialize(UnicodeBlock type) {
					return type.toString();
				}
			});
		}

	}

}
