package net.starly.itemdb.message.impl;

import javafx.util.Pair;
import net.starly.itemdb.message.MessageContext;
import net.starly.itemdb.message.STMessage;
import net.starly.itemdb.message.enums.MessageType;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ItemDBMessageContextImpl implements MessageContext {

    private static ItemDBMessageContextImpl instance;

    public static ItemDBMessageContextImpl getInstance() {
        if (instance == null) instance = new ItemDBMessageContextImpl();
        return instance;
    }

    private ItemDBMessageContextImpl() {}

    private final Map<Pair<MessageType, String>, String> map = new HashMap<>();

    @Override
    public STMessage get(MessageType messageType, String key, String def) {
        return new STMessage(map.getOrDefault(new Pair<>(MessageType.DEFAULT, "prefix"), ""), map.getOrDefault(new Pair<>(messageType, key), def));
    }

    @Override
    public STMessage get(MessageType messageType, String key) { return get(messageType, key, ""); }

    @Override
    public String getOnlyString(MessageType messageType, String key) { return map.getOrDefault(new Pair<>(MessageType.DEFAULT, key), ""); }

    @Override
    public STMessage get(MessageType messageType, String key, String def, Function<String, String> replacer) {
        return new STMessage(map.getOrDefault(new Pair<>(MessageType.DEFAULT, "prefix"), ""), replacer.apply(get(messageType, key, def).getMessage()));
    }

    @Override
    public STMessage get(MessageType messageType, String key, Function<String, String> replacer) {
        return get(messageType, key, "", replacer);
    }

    @Override
    public void set(MessageType messageType, String key, String value) {
        map.put(new Pair<>(messageType, key), ChatColor.translateAlternateColorCodes('&', value));
    }

    @Override
    public void reset() { map.clear(); }
}
