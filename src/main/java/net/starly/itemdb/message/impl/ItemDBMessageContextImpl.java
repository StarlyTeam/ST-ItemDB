package net.starly.itemdb.message.impl;

import net.starly.core.jb.util.Pair;
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

    private final Map<Pair<MessageType, String>, String> map = new HashMap<>();

    private ItemDBMessageContextImpl() {}

    @Override
    public STMessage get(MessageType type, String key, String def) {
        return new STMessage(map.getOrDefault(new Pair<>(MessageType.DEFAULT, "prefix"), ""), map.getOrDefault(new Pair<>(type, key), def));
    }

    @Override
    public STMessage get(MessageType type, String key) { return get(type, key, ""); }

    @Override
    public String getOnlyString(MessageType type, String key) { return map.getOrDefault(new Pair<>(MessageType.DEFAULT, key), ""); }

    @Override
    public STMessage get(MessageType type, String key, String def, Function<String, String> replacer) {
        return new STMessage(map.getOrDefault(new Pair<>(MessageType.DEFAULT, "prefix"), ""), replacer.apply(get(type, key, def).getMessage()));
    }

    @Override
    public STMessage get(MessageType type, String key, Function<String, String> replacer) { return get(type, key, "", replacer); }

    @Override
    public void set(MessageType type, String key, String value) { map.put(new Pair<>(type, key), ChatColor.translateAlternateColorCodes('&', value)); }

    @Override
    public void reset() { map.clear(); }
}
