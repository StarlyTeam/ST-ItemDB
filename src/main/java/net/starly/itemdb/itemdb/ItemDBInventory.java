package net.starly.itemdb.itemdb;

import lombok.AllArgsConstructor;
import net.starly.itemdb.itemdb.page.holder.PaginationInventoryHolder;
import net.starly.itemdb.itemdb.page.manager.PaginationManager;
import net.starly.itemdb.util.ItemDBUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

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
            try {
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1, 2);
            } catch (NoSuchFieldError error) {
                player.playSound(player.getLocation(), Sound.valueOf("ITEM_BOOK_PAGE_TURN"), 2, 1);
            } catch (Exception ignored) {}
        }

        if (event.getSlot() == paginationHolder.getNextBtnSlot()) {
            paginationManager.nextPage();
            ItemDBUtil.pageInventory(player, paginationHolder);
            try {
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1, 2);
            } catch (NoSuchFieldError error) {
                player.playSound(player.getLocation(), Sound.valueOf("ITEM_BOOK_PAGE_TURN"), 2, 1);
            } catch (Exception ignored) {}
        }

        for (int i = 45; i < 54; i ++) if (event.getSlot() == i) return;

        player.getInventory().addItem(event.getCurrentItem());

        try {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BELL, 1, 2);
        } catch (NoSuchFieldError error) {
            player.playSound(player.getLocation(), Sound.valueOf("BLOCK_NOTE_BLOCK_BELL"), 2, 2);
        } catch (Exception ignored) {}
    }
}
