package net.starly.itemdb.command.execute.sub;

import net.starly.itemdb.ItemDB;
import net.starly.itemdb.command.STSubCommand;
import net.starly.itemdb.message.impl.ItemDBMessageContextImpl;
import net.starly.itemdb.util.ItemDBUtil;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ItemDBSubCommands {

    private static final STSubCommand openCommand = new STSubCommand("", "", "");
    private static final STSubCommand saveItemDB = new STSubCommand("<아이디>", "<id>", "");
    private static final STSubCommand deleteItemDB = new STSubCommand("<아이디>", "<id>", "");
    private static final STSubCommand giveItemDB = new STSubCommand("<플레이어>", "<playerName>", "");
    private static final STSubCommand getItemDB = new STSubCommand("", "", "");
    private static final ItemDBMessageContextImpl context = ItemDBMessageContextImpl.getInstance();

    public static final STSubCommand OPEN =
            new STSubCommand("열기", "open", "ItemDB 도움말을 확인합니다.", openCommand, (sender, args) -> {
                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("콘솔 불가능");
                    return;
                }
                Player player = (Player) sender;
                if (args.length == 0) ItemDBUtil.openItemDBGui(player);
                else {
                    sender.sendMessage("테스트임.");
                }
            });

    public static final STSubCommand SAVE =
            new STSubCommand("저장", "save", "ItemDB에 아이템을 저장합니다.", saveItemDB, (sender, args) -> {
                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("콘솔 불가능");
                    return;
                }
                Player player = (Player) sender;

                String id = args[1];

                ItemDB.getApi().save(player.getInventory().getItemInMainHand(), id);
                player.sendMessage("손에 든 아이템을 아이템저장소에 " + id + "로 저장하였습니다.");
            });

    public static final STSubCommand DELETE =
            new STSubCommand("삭제", "delete", "ItemDB에 아이템을 저장합니다.", deleteItemDB, (sender, args) -> {
                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("콘솔 가능");
                    // TODO 아이템 저장소에 있는 ItemDB [id] 삭제
                    return;
                }
                Player player = (Player) sender;

                String id = args[1];

                ItemDB.getApi().save(player.getInventory().getItemInMainHand(), id);
                player.sendMessage("아이템저장소에 있는" + id + "를 삭제하였습니다.");
            });

    public static final STSubCommand GIVE =
            new STSubCommand("지급", "give", "플레이어에게 ItemDB에 있는 아이템을 지급합니다.", giveItemDB, (sender, args) -> {
                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("콘솔 가능");
                    // TODO 아이템 지급
                    return;
                }
                if (args.length == 0) sender.sendMessage("입력하셈.");
                else {
                    sender.sendMessage("테스트임.");
                }
            });

    public static final STSubCommand GET =
            new STSubCommand("받기", "get", "플레이어에게 ItemDB에 있는 아이템을 받습니다.", getItemDB, (sender, args) -> {
                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("콘솔 불가능");
                    return;
                }
                if (args.length == 0) sender.sendMessage("입력하셈.");
                else {
                    sender.sendMessage("테스트임.");
                }
            });

    public static final STSubCommand RELOAD =
            new STSubCommand("리로드", "reload", "콘피그를 리로드합니다.", getItemDB, (sender, args) -> {
                if (sender instanceof ConsoleCommandSender) {
                    context.reset();
                    sender.sendMessage("콘피그를 리로드하였습니다.");
                    return;
                }
                if (args.length == 0) {
                    context.reset();
                    sender.sendMessage("콘피그를 리로드하였습니다.");
                } else {
                    sender.sendMessage("테스트임.");
                }
            });
}
