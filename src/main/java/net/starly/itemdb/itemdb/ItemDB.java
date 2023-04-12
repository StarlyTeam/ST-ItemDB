package net.starly.itemdb.itemdb;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ItemDB {
    void save(Player player, String id);
    ItemStack getItem(String id);
    List<ItemStack> getItems();
    void delete(String id);
    List<String> getIds();
}
