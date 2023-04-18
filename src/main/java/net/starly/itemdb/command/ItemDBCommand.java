package net.starly.itemdb.command;

import net.starly.itemdb.ItemDB;
import net.starly.itemdb.command.sub.ItemDBSubCommands;
import net.starly.itemdb.util.ItemDBUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemDBCommand extends STItemDBCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length == 0 && sender instanceof Player) {
            ItemDBUtil.openItemDBGui(player);
        }
        return super.onCommand(sender, command, label, args);
    }

    public ItemDBCommand(JavaPlugin plugin, String command) {
        super(plugin, command);
        registerSubCommand(
                ItemDBSubCommands.GIVE,
                ItemDBSubCommands.GET
        );
    }

    @Override
    protected boolean isPlayerTabComplete() { return false; }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 2) {
            if (!(args[0].equals("지급") || args[0].equals("give"))) {
                return StringUtil.copyPartialMatches(args[1], ItemDB.getInstance().getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()), new ArrayList<>());
            }
        }

//        if (args.length == 1) {
//            if (player.hasPermission("starly.itemdb.save")) list.add("저장");
//            if (player.hasPermission("starly.itemdb.get")) list.add("받기");
//            if (player.hasPermission("starly.itemdb.give")) list.add("지급");
//            if (player.hasPermission("starly.itemdb.delete")) list.add("삭제");
//            return StringUtil.copyPartialMatches(args[0], list, new ArrayList<>());
//        } else if (args.length == 2) {
//            if (args[0].equals("받기"))
//                return StringUtil.copyPartialMatches(args[1], ItemDB.getApi().getIds().stream().collect(Collectors.toList()), new ArrayList<>());
//            if (args[0].equals("지급"))
//                return StringUtil.copyPartialMatches(args[1], ItemDB.getInstance().getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()), new ArrayList<>());
//            if (args[0].equals("삭제"))
//                return StringUtil.copyPartialMatches(args[1], ItemDB.getApi().getIds().stream().collect(Collectors.toList()), new ArrayList<>());
//        } else if (args.length == 3) {
//            if (args[0].equals("지급"))
//                return StringUtil.copyPartialMatches(args[2], ItemDB.getApi().getIds().stream().collect(Collectors.toList()), new ArrayList<>());
//        }
        return super.onTabComplete(sender, command, label, args);
    }
}
