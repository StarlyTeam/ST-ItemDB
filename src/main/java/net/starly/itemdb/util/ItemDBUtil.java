package net.starly.itemdb.util;

import net.starly.itemdb.ItemDB;
import net.starly.itemdb.itemdb.ItemDBInventory;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemDBUtil {

    public static void openItemDBGui(Player player) {

        Server server = ItemDB.getInstance().getServer();
        Inventory inventory = server.createInventory(null, 6 * 9, "아이템 저장소");

        for (int i = 45; i < 54; i ++) {

            try {
                inventory.setItem(i, new ItemStack(Material.STAINED_GLASS));
            } catch (NoSuchFieldError error) {
                inventory.setItem(i, new ItemStack(Material.valueOf("GLASS_PANE")));
            }

            if (i == 48) inventory.setItem(48, new ItemStack(Material.PAPER));
            if (i == 50) inventory.setItem(50, new ItemStack(Material.PAPER));
        }

        for (int i = 0; i < 45; i++) {
            inventory.setItem(i, ItemDB.getApi().getItems().get(i));
        }

        ItemDBInventory itemDBInventory = new ItemDBInventory(inventory);
        player.openInventory(inventory);

        Listener listener = new Listener() {};

        server.getPluginManager().registerEvent(InventoryClickEvent.class, listener, EventPriority.LOWEST, (listeners, event) -> {
            if (player.getUniqueId().equals(((InventoryClickEvent) event).getWhoClicked().getUniqueId()))
                itemDBInventory.onClick((InventoryClickEvent) event);
        }, ItemDB.getInstance());

        server.getPluginManager().registerEvent(InventoryCloseEvent.class, listener, EventPriority.LOWEST, (listeners, event) -> {
            if (player.getUniqueId().equals(((InventoryCloseEvent) event).getPlayer().getUniqueId())) {
                InventoryCloseEvent.getHandlerList().unregister(listener);
                InventoryClickEvent.getHandlerList().unregister(listener);
            }
        }, ItemDB.getInstance());
    }
}
