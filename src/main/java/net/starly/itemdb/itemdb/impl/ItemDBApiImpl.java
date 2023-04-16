package net.starly.itemdb.itemdb.impl;

import net.starly.itemdb.itemdb.ItemDBApi;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemDBApiImpl implements ItemDBApi {

    private final File file;
    private final FileConfiguration config;

    public ItemDBApiImpl(JavaPlugin plugin) {
        file = new File(plugin.getDataFolder(), "itemdb.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        try {
            if (!(file.exists())) file.createNewFile();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void save(ItemStack item, String id) {
        try {
            if (!file.exists()) file.createNewFile();
            ItemStack itemStack = new ItemStack(item);

            config.set("itemdb." + id, itemStack);
            config.save(file);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public ItemStack getItem(String id) {
        ItemStack itemStack = config.getItemStack("itemdb." + id);

        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) { e.printStackTrace(); }

        return itemStack;
    }

    @Override
    public List<ItemStack> getItems() {
        List<ItemStack> itemList = new ArrayList<>();
        config.getConfigurationSection("itemdb").getKeys(false).forEach(key ->
                itemList.add(config.getItemStack("itemdb." + key)));
        return itemList;
    }

    @Override
    public void delete(String id) {
        try {
            if (!file.exists()) file.createNewFile();
            config.set("itemdb." + id, null);
            config.save(file);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public List<String> getIds() {
        List<String> ids = new ArrayList<>(config.getConfigurationSection("itemdb").getKeys(false));
        return ids;
    }
}
