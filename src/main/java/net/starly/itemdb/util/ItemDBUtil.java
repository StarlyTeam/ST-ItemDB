package net.starly.itemdb.util;

import net.starly.itemdb.ItemDB;
import net.starly.itemdb.itemdb.ItemDBInventory;
import net.starly.itemdb.itemdb.page.holder.PaginationHolder;
import net.starly.itemdb.itemdb.page.manager.PaginationManager;
import net.starly.itemdb.itemdb.page.manager.impl.PaginationManagerImpl;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class ItemDBUtil {

    public static void openItemDBGui(Player player) {
        PaginationManagerImpl paginationManager = new PaginationManagerImpl(ItemDB.getApi().getItems());
        PaginationHolder paginationHolder = new PaginationHolder(paginationManager, 48, 50);

        player.openInventory(paginationHolder.getInventory());
        registerEvent(player.getUniqueId(), paginationHolder.getInventory());
    }

    public static void pageInventory(Player player, PaginationHolder paginationHolder) {
        player.openInventory(paginationHolder.getInventory());
        registerEvent(player.getUniqueId(), paginationHolder.getInventory());
    }

    private static void registerEvent(UUID uuid, Inventory inventory) {
        Server server = ItemDB.getInstance().getServer();
        ItemDBInventory itemDBInventory = new ItemDBInventory(inventory);
        Listener listener = new Listener() {};

        server.getPluginManager().registerEvent(InventoryClickEvent.class, listener, EventPriority.LOWEST, (listeners, event) -> {
            if (uuid.equals(((InventoryClickEvent) event).getWhoClicked().getUniqueId()))
                itemDBInventory.onClick((InventoryClickEvent) event);
        }, ItemDB.getInstance());

        server.getPluginManager().registerEvent(InventoryCloseEvent.class, listener, EventPriority.LOWEST, (listeners, event) -> {
            if (uuid.equals(((InventoryCloseEvent) event).getPlayer().getUniqueId())) {
                InventoryCloseEvent.getHandlerList().unregister(listener);
                InventoryClickEvent.getHandlerList().unregister(listener);
            }
        }, ItemDB.getInstance());
    }
}
