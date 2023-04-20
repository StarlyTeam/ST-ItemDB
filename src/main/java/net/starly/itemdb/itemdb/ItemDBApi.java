package net.starly.itemdb.itemdb;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ItemDBApi {
    void saveItem(ItemStack itemStack, String id);
    ItemStack getItem(String id);
    List<ItemStack> getItems();
    void deleteItem(String id);
    List<String> getIds();
}
