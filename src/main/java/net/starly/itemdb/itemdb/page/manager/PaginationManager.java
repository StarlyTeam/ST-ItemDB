package net.starly.itemdb.itemdb.page.manager;

import net.starly.itemdb.itemdb.page.ItemDBPage;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface PaginationManager {
    void nextPage();
    void prevPage();
    ItemDBPage getCurrentPageData();
    List<ItemDBPage> paginateItems(List<ItemStack> itemList);
}
