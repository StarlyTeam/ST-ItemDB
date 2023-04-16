package net.starly.itemdb.itemdb;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ItemDBApi {
    void save(ItemStack itemStack, String id);
    ItemStack getItem(String id);
    List<ItemStack> getItems();
    void delete(String id);
    List<String> getIds();
}
