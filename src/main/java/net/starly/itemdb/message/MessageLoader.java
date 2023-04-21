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

        loadMessageSection(messagesSection.getConfigurationSection("itemdb"), MessageType.DEFAULT);
        loadMessageSection(errorMessagesSection.getConfigurationSection("itemdb"), MessageType.ERROR);

        loaded = true;
    }

    private static void loadMessageSection(ConfigurationSection section, MessageType type) {
        if (section == null) return;
        MessageContext context = ItemDBMessageContextImpl.getInstance();
        section.getKeys(false).forEach((key)-> context.set(type, key, section.getString(key)));
    }
}