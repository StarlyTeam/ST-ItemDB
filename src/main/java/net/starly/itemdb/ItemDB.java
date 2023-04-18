package net.starly.itemdb;

import lombok.Getter;
import net.starly.core.bstats.Metrics;
import net.starly.itemdb.command.ItemDBCmd;
import net.starly.itemdb.command.ItemDBCommand;
import net.starly.itemdb.itemdb.ItemDBApi;
import net.starly.itemdb.itemdb.impl.ItemDBApiImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemDB extends JavaPlugin {

    @Getter private static ItemDB instance;
    @Getter private static ItemDBApi api;

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
        api = new ItemDBApiImpl(this);

        /* CONFIG
         ──────────────────────────────────────────────────────────────────────────────────────────────────────────────── */
        saveDefaultConfig();

        /* COMMAND
         ──────────────────────────────────────────────────────────────────────────────────────────────────────────────── */
        new ItemDBCommand(this, "itemdb");
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
