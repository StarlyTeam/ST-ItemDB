package net.starly.itemdb.command;

import net.starly.itemdb.ItemDBMain;
import net.starly.itemdb.itemdb.impl.ItemDBImpl;
import net.starly.itemdb.util.ItemDBUtil;
import org.apache.logging.log4j.util.TriConsumer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ItemDBCmd implements CommandExecutor {

    private final static Map<String, TriConsumer<ItemDBImpl, Player, String>> subCommandMap = new HashMap<>();

    static {
        subCommandMap.put("저장", (item, player, id) -> item.save(player, id));
        subCommandMap.put("받기", (item, player, id) -> player.getInventory().addItem(item.getItem(id)));
        subCommandMap.put("삭제", (item, player, id) -> item.delete(id));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        ItemDBImpl itemDB = ItemDBMain.getItemDB();

        if (args.length == 0) {

            if (!player.hasPermission("starly.itemdb.open")) {
                player.sendMessage("당신은 해당 명령어를 사용할 권한이 없습니다");
                return false;
            }

            ItemDBUtil.openItemDBGui(player);
            return false;
        }

        switch (args[0]) {

            case "저장":
            case "받기":
            case "삭제": {
                String id = args[1];

                subCommandMap.get(args[0]).accept(itemDB, player, id);
                player.sendMessage(args[0]);
                break;
            }

            case "지급": {
                Player target = ItemDBMain.getInstance().getServer().getPlayer(args[1]);
                String id = args[2];

                target.getInventory().addItem(itemDB.getItem(id));
                break;
            }

            default:
                player.sendMessage("올바르지 않은 명령어입니다.");
                break;
        }
        return false;
    }
}
