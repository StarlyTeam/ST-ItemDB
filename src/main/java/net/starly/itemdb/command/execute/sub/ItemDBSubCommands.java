package net.starly.itemdb.command.execute.sub;

import net.starly.itemdb.ItemDB;
import net.starly.itemdb.command.STSubCommand;
import net.starly.itemdb.message.MessageLoader;
import net.starly.itemdb.message.enums.MessageType;
import net.starly.itemdb.message.impl.ItemDBMessageContextImpl;
import net.starly.itemdb.util.ItemDBUtil;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ItemDBSubCommands {

    private static final STSubCommand openCommand = new STSubCommand("", "", "");
    private static final STSubCommand saveItemDB = new STSubCommand("<아이디>", "<id>", "");
    private static final STSubCommand deleteItemDB = new STSubCommand("<아이디>", "<id>", "");
    private static final STSubCommand giveItemDB = new STSubCommand("<플레이어>", "<playerName>", "");
    private static final STSubCommand giveAllItemDB = new STSubCommand("", "", "");
    private static final STSubCommand getItemDB = new STSubCommand("", "", "");
    private static final ItemDBMessageContextImpl context = ItemDBMessageContextImpl.getInstance();

    public static final STSubCommand OPEN =
            new STSubCommand("열기", "open", "아이템저장소 GUI를 엽니다.", openCommand, (sender, args) -> {

                if (sender instanceof ConsoleCommandSender) {
                    context.get(MessageType.ERROR, "noConsoleCommand").send(sender);
                    return;
                }

                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (args.length == 0) ItemDBUtil.openItemDBGui(player);
                    else {
                        context.get(MessageType.ERROR, "wrongCommand").send(sender);
                    }
                }
            });

    public static final STSubCommand SAVE =
            new STSubCommand("저장", "save", "아이템저장소에 아이템을 저장합니다.", saveItemDB, (sender, args) -> {

                if (sender instanceof ConsoleCommandSender) {
                    context.get(MessageType.ERROR, "noConsoleCommand").send(sender);
                    return;
                }

                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (args.length == 0) {
                        context.get(MessageType.ERROR, "noItemId").send(sender);
                        return;
                    }

                    if (args.length == 1) {
                        String id = args[0];

                        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                            context.get(MessageType.ERROR, "noItemInHand").send(sender);
                            return;
                        }

                        ItemDB.getApi().saveItem(player.getInventory().getItemInMainHand(), id);
                        context.get(MessageType.DEFAULT, "saveItemDB", (it) ->
                                it.replace("{id}", id)
                                        .replace("{displayName}", player.getInventory().getItemInMainHand().getItemMeta().getDisplayName())
                                        .replace("{material}", player.getInventory().getItemInMainHand().getType().name())).send(sender);
                    } else {
                        context.get(MessageType.ERROR, "wrongCommand").send(sender);
                    }
                }
            });

    public static final STSubCommand DELETE =
            new STSubCommand("삭제", "delete", "아이템저장소에 아이템을 삭제합니다.", deleteItemDB, (sender, args) -> {

                if (args.length == 0) {
                    context.get(MessageType.ERROR, "noItemId").send(sender);
                    return;
                }

                if (args.length != 1) {
                    context.get(MessageType.ERROR, "wrongCommand").send(sender);
                    return;
                }

                if (sender instanceof ConsoleCommandSender) {
                    String id = args[0];
                    deleteItemDB(sender, id);
                    return;
                }

                if (sender instanceof Player) {
                    String id = args[0];
                    deleteItemDB(sender, id);
                }
            });

    public static final STSubCommand GIVE =
            new STSubCommand("지급", "give", "플레이어에게 아이템저장소에 있는 아이템을 지급합니다.", giveItemDB, (sender, args) -> {

                if (args.length == 0) {
                    context.get(MessageType.ERROR, "noPlayerName").send(sender);
                    return;
                }

                if (args.length == 1) {
                    context.get(MessageType.ERROR, "noItemId").send(sender);
                    return;
                }

                if (args.length != 2) {
                    context.get(MessageType.ERROR, "wrongCommand").send(sender);
                    return;
                }

                Player target = ItemDB.getInstance().getServer().getPlayer(args[0]);
                String id = args[1];

                if (target == null) {
                    context.get(MessageType.ERROR, "notFoundPlayer", (it) ->
                            it.replace("{player}", args[0])).send(sender);
                    return;
                }

                if (sender instanceof ConsoleCommandSender) {
                    giveItemDB(sender, target, id);
                    return;
                }

                if (sender instanceof Player) {
                    giveItemDB(sender, target, id);
                }
            });

    public static final STSubCommand GIVEALL =
            new STSubCommand("모두지급", "giveall", "모든 플레이어에게 아이템저장소에 있는 아이템을 지급합니다.", giveAllItemDB, (sender, args) -> {


                if (args.length == 0) {
                    context.get(MessageType.ERROR, "noItemId").send(sender);
                    return;
                }

                if (args.length != 1) {
                    context.get(MessageType.ERROR, "wrongCommand").send(sender);
                    return;
                }

                String id = args[0];

                if (sender instanceof ConsoleCommandSender) {
                    giveAllItemDB(sender, id);
                    return;
                }

                if (sender instanceof Player) {
                    giveAllItemDB(sender, id);
                }
            });

    public static final STSubCommand GET =
            new STSubCommand("받기", "get", "플레이어에게 아이템저장소에 있는 아이템을 받습니다.", getItemDB, (sender, args) -> {

                if (sender instanceof ConsoleCommandSender) {
                    context.get(MessageType.ERROR, "noConsoleCommand").send(sender);
                    return;
                }

                if (sender instanceof Player) {
                    if (args.length == 1) {
                        Player player = (Player) sender;
                        String id = args[0];
                        boolean foundId = false;

                        for (String ids : ItemDB.getApi().getIds()) {
                            if (id.equals(ids)) {
                                foundId = true;
                                player.getInventory().addItem(ItemDB.getApi().getItem(id));
                                context.get(MessageType.DEFAULT, "getItemDB", (it) ->
                                        it.replace("{id}", id)).send(player);
                                break;
                            }
                        }
                        if (!foundId) context.get(MessageType.ERROR, "noFoundId").send(sender);
                    } else {
                        context.get(MessageType.ERROR, "wrongCommand").send(sender);
                    }
                }
            });

    public static final STSubCommand RELOAD =
            new STSubCommand("리로드", "reload", "콘피그를 리로드합니다.", getItemDB, (sender, args) -> {
                if (sender instanceof ConsoleCommandSender) {
                    ItemDB.getInstance().reloadConfig();
                    MessageLoader.load(ItemDB.getInstance().getConfig());
                    context.get(MessageType.DEFAULT, "reloadComplete").send(sender);
                    return;
                }
                if (args.length == 0) {
                    ItemDB.getInstance().reloadConfig();
                    MessageLoader.load(ItemDB.getInstance().getConfig());
                    context.get(MessageType.DEFAULT, "reloadComplete").send(sender);
                } else {
                    context.get(MessageType.ERROR, "wrongCommand").send(sender);
                }
            });


    private static void deleteItemDB(CommandSender sender, String id) {

        boolean foundId = false;
        for (String ids : ItemDB.getApi().getIds()) {
            if (id.equals(ids)) {
                foundId = true;
                ItemDB.getApi().deleteItem(id);
                context.get(MessageType.DEFAULT, "deleteItemDB", (it) -> it.replace("{id}", id)).send(sender);
                break;
            }
        }
        if (!foundId) context.get(MessageType.ERROR, "noFoundId").send(sender);
    }

    private static void giveItemDB(CommandSender sender, Player target, String id) {
        try {
            target.getInventory().addItem(ItemDB.getApi().getItem(id));
            context.get(MessageType.DEFAULT, "giveItemDB", (it) ->
                    it.replace("{target}", target.getDisplayName())
                            .replace("{player}", sender.getName())
                            .replace("{id}", id)).send(sender);
            context.get(MessageType.DEFAULT, "giveItemDBTarget", (it) ->
                    it.replace("{target}", target.getDisplayName())
                            .replace("{player}", sender.getName())
                            .replace("{id}", id)).send(sender);
        } catch (IllegalArgumentException ex) {
            context.get(MessageType.ERROR, "noFoundId").send(sender);
        }
    }

    private static void giveAllItemDB(CommandSender sender, String id) {
        ItemDB.getInstance().getServer().getOnlinePlayers().forEach(players -> {
            try {
                players.getInventory().addItem(ItemDB.getApi().getItem(id));
                context.get(MessageType.DEFAULT, "giveAllItemDB", (it) ->
                        it.replace("{player}", sender.getName())
                                .replace("{id}", id)
                                .replace("{count}", String.valueOf(ItemDB.getInstance().getServer().getOnlinePlayers().size()))).broadcast();
            } catch (IllegalArgumentException ex) {
                context.get(MessageType.ERROR, "noFoundId").send(sender);

            }
        });
    }
}
