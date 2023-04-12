package net.starly.itemdb;

import lombok.Getter;
import net.starly.core.bstats.Metrics;
import net.starly.itemdb.command.ItemDBCmd;
import net.starly.itemdb.command.tabcomplete.ItemDBTab;
import net.starly.itemdb.itemdb.impl.ItemDBImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemDBMain extends JavaPlugin {

    @Getter private static ItemDBMain instance;
    @Getter private static ItemDBImpl itemDB;

    @Override
    public void onLoad() { instance = this; }

    @Override
    public void onEnable() {
        /* DEPENDENCY
         ──────────────────────────────────────────────────────────────────────────────────────────────────────────────── */
        if (!isPluginEnabled("net.starly.core.StarlyCore")) {
            getServer().getLogger().warning("[" + getName() + "] ST-Core 플러그인이 적용되지 않았습니다! 플러그인을 비활성화합니다.");
            getServer().getLogger().warning("[" + getName() + "] 다운로드 링크 : §fhttp://starly.kr/");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        /* SETUP
         ──────────────────────────────────────────────────────────────────────────────────────────────────────────────── */
        new Metrics(this, 12345); // TODO: 수정
        itemDB = new ItemDBImpl(this);

        /* CONFIG
         ──────────────────────────────────────────────────────────────────────────────────────────────────────────────── */
        saveDefaultConfig();

        /* COMMAND
         ──────────────────────────────────────────────────────────────────────────────────────────────────────────────── */
        getCommand("itemdb").setExecutor(new ItemDBCmd());
        getCommand("itemdb").setTabCompleter(new ItemDBTab());

        /* EVENT
         ──────────────────────────────────────────────────────────────────────────────────────────────────────────────── */
        // TODO: 수정
    }


    private boolean isPluginEnabled(String path) {
        try {
            Class.forName(path);
            return true;
        } catch (ClassNotFoundException ignored) {
        } catch (Exception ex) { ex.printStackTrace(); }
        return false;
    }
}
