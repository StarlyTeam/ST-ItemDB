package net.starly.itemdb.itemdb;

import lombok.AllArgsConstructor;
import net.starly.itemdb.ItemDB;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

@AllArgsConstructor
public class ItemDBInventory {

    private Inventory inventory;

    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemDBApi itemDB = ItemDB.getApi();

        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

        if (event.getSlot() == 48) {

        }

        if (event.getSlot() == 50) {

            System.out.println(itemDB.getItems().size());

            if (itemDB.getItems().size() < 46) {
                player.sendMessage("다음 페이지가 없습니다.");
                return;
            }

            player.sendMessage("다음페이지로 넘어갑니다.");
        }

        for (int i = 45; i < 54; i ++) if (event.getSlot() == i) return;

        player.getInventory().addItem(event.getCurrentItem());
    }
}
