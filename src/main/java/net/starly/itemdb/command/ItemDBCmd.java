package net.starly.itemdb.command;

import net.starly.itemdb.ItemDB;
import net.starly.itemdb.itemdb.ItemDBApi;
import net.starly.itemdb.util.ItemDBUtil;
import org.apache.logging.log4j.util.TriConsumer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Deprecated()
public class ItemDBCmd implements CommandExecutor {

    private final static Map<String, TriConsumer<ItemDBApi, Player, String>> subCommandMap = new HashMap<>();

    static {
        subCommandMap.put("저장", (item, player, id) -> item.save(player.getInventory().getItemInMainHand(), id));
        subCommandMap.put("받기", (item, player, id) -> player.getInventory().addItem(item.getItem(id)));
        subCommandMap.put("삭제", (item, player, id) -> item.delete(id));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        ItemDBApi itemDB = ItemDB.getApi();

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
                Player target = ItemDB.getInstance().getServer().getPlayer(args[1]);
                String id = args[2];

                if (args[1].equals("@a")) {
                    ItemDB.getInstance().getServer().getOnlinePlayers().forEach(players ->
                            players.getInventory().addItem(ItemDB.getApi().getItem(id)));
                }

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
