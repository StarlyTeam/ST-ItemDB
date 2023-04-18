package net.starly.itemdb.message;

import net.starly.itemdb.message.enums.MessageType;

import java.util.function.Function;

public interface MessageContext {

    STMessage get(MessageType messageType, String key, String def);
    STMessage get(MessageType messageType, String key);
    String getOnlyString(MessageType messageType, String key);
    STMessage get(MessageType messageType, String key, String def, Function<String, String> replacer);
    STMessage get(MessageType messageType, String key, Function<String, String> replacer);
    void set(MessageType messageType, String key, String value);
    void reset();
}
