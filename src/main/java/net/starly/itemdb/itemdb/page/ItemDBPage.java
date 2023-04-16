package net.starly.itemdb.itemdb.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ItemDBPage {
    private final int pageNum;
    private final List<ItemStack> items;
}
