package net.starly.itemdb.command.tabcomplete;

import net.starly.itemdb.ItemDBMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ItemDBTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        List<String> list = new ArrayList<>();

        if (args.length == 1) {
            if (player.hasPermission("starly.itemdb.save")) list.add("저장");
            if (player.hasPermission("starly.itemdb.get")) list.add("받기");
            if (player.hasPermission("starly.itemdb.give")) list.add("지급");
            if (player.hasPermission("starly.itemdb.delete")) list.add("삭제");
            return StringUtil.copyPartialMatches(args[0], list, new ArrayList<>());
        }

        else if (args.length == 2) {
            if (args[0].equals("받기"))
                return StringUtil.copyPartialMatches(args[1], ItemDBMain.getItemDB().getIds().stream().collect(Collectors.toList()), new ArrayList<>());
            if (args[0].equals("지급"))
                return StringUtil.copyPartialMatches(args[1], ItemDBMain.getInstance().getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()), new ArrayList<>());
            if (args[0].equals("삭제"))
                return StringUtil.copyPartialMatches(args[1], ItemDBMain.getItemDB().getIds().stream().collect(Collectors.toList()), new ArrayList<>());
        }

        else if (args.length == 3) {
            if (args[0].equals("지급"))
                return StringUtil.copyPartialMatches(args[2], ItemDBMain.getItemDB().getIds().stream().collect(Collectors.toList()), new ArrayList<>());
        }
        return Collections.emptyList();
    }
}
