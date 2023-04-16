package net.starly.itemdb.itemdb.page.manager.impl;

import lombok.Getter;
import net.starly.itemdb.itemdb.page.ItemDBPage;
import net.starly.itemdb.itemdb.page.manager.PaginationManager;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PaginationManagerImpl implements PaginationManager {

    @Getter private final List<ItemDBPage> pages;
    @Getter private int currentPage;

    public PaginationManagerImpl(List<ItemStack> items) {
        this.pages = paginateItems(items);
        this.currentPage = 1;
    }

    @Override
    public void nextPage() {
        if (currentPage < pages.size()) currentPage++;
    }

    @Override
    public void prevPage() {
        if (currentPage > 1) currentPage--;
    }

    @Override
    public ItemDBPage getCurrentPageData() { return pages.get(currentPage - 1); }

    @Override
    public List<ItemDBPage> paginateItems(List<ItemStack> items) {
        List<ItemDBPage> pages = new ArrayList<>();
        int itemCount = items.size();
        int pageCount = (int) Math.ceil((double) itemCount / 45);
        for (int i = 0; i < pageCount; i++) {
            int start = i * 45;
            int end = Math.min(start + 45, itemCount);
            List<ItemStack> pageItems = items.subList(start, end);
            pages.add(new ItemDBPage(i + 1, pageItems));
        }
        return pages;
    }
}