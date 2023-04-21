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
            new STSubCommand("열기", "open", "ItemDB GUI를 엽니다..", openCommand, (sender, args) -> {

                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("콘솔에서는 GUI를 열 수 없습니다.");
                    return;
                }

                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (args.length == 0) ItemDBUtil.openItemDBGui(player);
                    else {
                        sender.sendMessage("올바르지 않은 명령어입니다.");
                    }
                }
            });

    public static final STSubCommand SAVE =
            new STSubCommand("저장", "save", "ItemDB에 아이템을 저장합니다.", saveItemDB, (sender, args) -> {

                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("콘솔 불가능");
                    return;
                }

                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (args.length == 0) {
                        player.sendMessage("저장할 이름을 입력해주세요.");
                        return;
                    }

                    if (args.length == 1) {
                        String id = args[0];
                        ItemDB.getApi().saveItem(player.getInventory().getItemInMainHand(), id);
                        player.sendMessage("손에 든 아이템을 아이템저장소에 " + id + "로 저장하였습니다.");

                    } else {
                        sender.sendMessage("올바르지 않은 명령어입니다.");
                    }
                }
            });

    public static final STSubCommand DELETE =
            new STSubCommand("삭제", "delete", "ItemDB에 아이템을 저장합니다.", deleteItemDB, (sender, args) -> {

                if (args.length == 0) {
                    sender.sendMessage("삭제할 아이템 ID를 입력해주세요.");
                    return;
                }

                if (args.length != 1) {
                    sender.sendMessage("올바르지 않은 명령어입니다.");
                    return;
                }

                if (sender instanceof ConsoleCommandSender) {
                    String id = args[0];
                    ItemDB.getApi().deleteItem(id);
                    sender.sendMessage("아이템저장소에 있는" + id + "를 삭제하였습니다.");
                    return;
                }

                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    String id = args[0];
                    ItemDB.getApi().deleteItem(id);
                    player.sendMessage("아이템저장소에 있는" + id + "를 삭제하였습니다.");
                }
            });

    public static final STSubCommand GIVE =
            new STSubCommand("지급", "give", "플레이어에게 ItemDB에 있는 아이템을 지급합니다.", giveItemDB, (sender, args) -> {

                if (args.length == 0) {
                    sender.sendMessage("지급할 플레이어 닉네임을 입력해주세요.");
                    return;
                }

                if (args.length == 1) {
                    sender.sendMessage("지급할 아이템 ID를 입력해주세요.");
                    return;
                }

                if (args.length != 2) {
                    sender.sendMessage("올바르지 않은 명령어입니다.");
                    return;
                }

                Player target = ItemDB.getInstance().getServer().getPlayer(args[0]);
                String id = args[1];

                if (target == null) {
                    sender.sendMessage("플레이어를 찾을 수 없습니다.");
                    return;
                }

                if (sender instanceof ConsoleCommandSender) {
                    target.getInventory().addItem(ItemDB.getApi().getItem(id));
                    sender.sendMessage(target.getDisplayName() + "님에게 " + id + "의 아이템을 지급하였습니다.");
                    return;
                }

                if (sender instanceof Player) {
                    target.getInventory().addItem(ItemDB.getApi().getItem(id));
                    sender.sendMessage(target.getDisplayName() + "님에게 " + id + "의 아이템을 지급하였습니다.");
                }
            });

    public static final STSubCommand GET =
            new STSubCommand("받기", "get", "플레이어에게 ItemDB에 있는 아이템을 받습니다.", getItemDB, (sender, args) -> {

                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("콘솔 불가능");
                    return;
                }

                if (sender instanceof Player) {
                    if (args.length == 1) {
                        Player player = (Player) sender;

                        String id = args[0];
                        player.getInventory().addItem(ItemDB.getApi().getItem(id));
                        player.sendMessage(id + "의 아이템을 지급 받았습니다.");
                    } else {
                        sender.sendMessage("올바르지 않은 명령어입니다.");
                    }
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
