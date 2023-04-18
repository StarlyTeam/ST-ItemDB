package net.starly.itemdb.message;

import net.starly.itemdb.message.enums.MessageType;
import net.starly.itemdb.message.impl.ItemDBMessageContextImpl;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class MessageLoader {

    private static boolean loaded = false;

    public static void load(FileConfiguration config) {
        if (loaded) {
            ItemDBMessageContextImpl.getInstance().reset();
            loaded = false;
        }

        ConfigurationSection messagesSection = Objects.requireNonNull(config.getConfigurationSection("messages"));
        ConfigurationSection errorMessagesSection = Objects.requireNonNull(config.getConfigurationSection("errorMessages"));

        loadMessageSection(messagesSection.getConfigurationSection("itemdb"), MessageType.DEFAULT, false);
        loadMessageSection(errorMessagesSection.getConfigurationSection("itemdb"), MessageType.ERROR, false);
    }

    private static void loadMessageSection(ConfigurationSection section, MessageType messageType, boolean itemdb) {
        if (section == null) return;
        MessageContext messageContext;
        if (itemdb) messageContext = ItemDBMessageContextImpl.getInstance();
        else messageContext = ItemDBMessageContextImpl.getInstance();
        section.getKeys(false).forEach((key) -> messageContext.set(messageType, key, section.getString(key)));
    }
}
