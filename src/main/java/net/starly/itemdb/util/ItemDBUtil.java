package net.starly.itemdb.util;

import net.starly.itemdb.ItemDBMain;
import net.starly.itemdb.itemdb.ItemDBInventory;
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

        Server server = ItemDBMain.getInstance().getServer();
        Inventory inventory = server.createInventory(null, 6 * 9, "테스트");

        // TODO 콘피그에 있는 아이템 인벤토리로 불러오기
        for (int i = 0; i < ItemDBMain.getItemDB().getItems().size(); i++) {
            for (ItemStack itemStack : ItemDBMain.getItemDB().getItems())
                inventory.setItem(i, itemStack);
        }
        // TODO 여기까지

        ItemDBInventory itemDBInventory = new ItemDBInventory(inventory);
        player.openInventory(inventory);

        Listener listener = new Listener() {};

        server.getPluginManager().registerEvent(InventoryClickEvent.class, listener, EventPriority.LOWEST, (listeners, event) -> {
            if (player.getUniqueId().equals(((InventoryClickEvent) event).getWhoClicked().getUniqueId()))
                itemDBInventory.onClick((InventoryClickEvent) event);
        }, ItemDBMain.getInstance());

        server.getPluginManager().registerEvent(InventoryCloseEvent.class, listener, EventPriority.LOWEST, (listeners, event) -> {
            if (player.getUniqueId().equals(((InventoryCloseEvent) event).getPlayer().getUniqueId())) {
                InventoryCloseEvent.getHandlerList().unregister(listener);
                InventoryClickEvent.getHandlerList().unregister(listener);
            }
        }, ItemDBMain.getInstance());
    }
}
