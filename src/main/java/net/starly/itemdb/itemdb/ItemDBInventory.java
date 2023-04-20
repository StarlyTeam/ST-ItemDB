package net.starly.itemdb.itemdb;

import lombok.AllArgsConstructor;
import net.starly.itemdb.itemdb.page.holder.PaginationInventoryHolder;
import net.starly.itemdb.itemdb.page.manager.PaginationManager;
import net.starly.itemdb.util.ItemDBUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class ItemDBInventory {

    private Inventory inventory;

    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

        PaginationInventoryHolder paginationHolder = (PaginationInventoryHolder) inventory.getHolder();
        PaginationManager paginationManager = paginationHolder.getPaginationManager();

        if (event.getSlot() == paginationHolder.getPrevBtnSlot()) {
            paginationManager.prevPage();
            ItemDBUtil.pageInventory(player, paginationHolder);
        }

        if (event.getSlot() == paginationHolder.getNextBtnSlot()) {
            paginationManager.nextPage();
            ItemDBUtil.pageInventory(player, paginationHolder);
        }

        for (int i = 45; i < 54; i ++) if (event.getSlot() == i) return;

        player.getInventory().addItem(event.getCurrentItem());
    }
}
