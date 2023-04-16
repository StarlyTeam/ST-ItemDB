package net.starly.itemdb.itemdb.page.holder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.starly.itemdb.ItemDB;
import net.starly.itemdb.itemdb.page.ItemDBPage;
import net.starly.itemdb.itemdb.page.manager.impl.PaginationManagerImpl;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@AllArgsConstructor
@Getter
public class PaginationHolder implements InventoryHolder {

    private final PaginationManagerImpl paginationManager;
    private final int prevBtnSlot;
    private final int nextBtnSlot;

    @Override
    public Inventory getInventory() {

        Inventory inventory = ItemDB.getInstance().getServer().createInventory(this, 54, "Page: " + paginationManager.getCurrentPage());
        ItemDBPage currentPage = paginationManager.getCurrentPageData();

        for(int i = 0; i < currentPage.getItems().size(); i++)
            inventory.setItem(i, currentPage.getItems().get(i));

        if (paginationManager.getPages().size() > 1) {
            ItemStack prevPageItem = new ItemStack(Material.ARROW);
            ItemMeta prevPageItemMeta = prevPageItem.getItemMeta();
            prevPageItemMeta.setDisplayName("이전 페이지");
            prevPageItem.setItemMeta(prevPageItemMeta);

            ItemStack nextPageItem = new ItemStack(Material.ARROW);
            ItemMeta nextPageItemMeta = prevPageItem.getItemMeta();
            nextPageItemMeta.setDisplayName("다음 페이지");
            nextPageItem.setItemMeta(nextPageItemMeta);

            inventory.setItem(prevBtnSlot, prevPageItem);
            inventory.setItem(nextBtnSlot, nextPageItem);
        }
        return inventory;
    }
}
