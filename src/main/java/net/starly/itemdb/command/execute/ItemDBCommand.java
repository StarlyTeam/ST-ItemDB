package net.starly.itemdb.command.execute;

import net.starly.itemdb.ItemDB;
import net.starly.itemdb.command.STItemDBCommand;
import net.starly.itemdb.command.execute.sub.ItemDBSubCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ItemDBCommand extends STItemDBCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return super.onCommand(sender, command, label, args);
    }

    public ItemDBCommand(JavaPlugin plugin, String command) {
        super(plugin, command, false);
        registerSubCommand(
                ItemDBSubCommands.OPEN,
                ItemDBSubCommands.SAVE,
                ItemDBSubCommands.DELETE,
                ItemDBSubCommands.GIVE,
                ItemDBSubCommands.GET,
                ItemDBSubCommands.RELOAD
        );
    }

    @Override
    protected boolean isPlayerTabComplete() { return false; }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 2) {
            if (args[0].equals("지급") || args[0].equals("give"))
                return StringUtil.copyPartialMatches(args[1], ItemDB.getInstance().getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()), new ArrayList<>());
            else if (args[0].equals("받기") || args[0].equals("get") || (args[0].equalsIgnoreCase("삭제") || args[0].equals("delete")))
                return StringUtil.copyPartialMatches(args[1], ItemDB.getApi().getIds().stream().collect(Collectors.toList()), new ArrayList<>());
            else return Collections.emptyList();
        }
        else if (args.length == 3) {
            if (args[0].equals("지급") || args[0].equals("give"))
                return StringUtil.copyPartialMatches(args[2], ItemDB.getApi().getIds().stream().collect(Collectors.toList()), new ArrayList<>());
            else return Collections.emptyList();
        }
        return super.onTabComplete(sender, command, label, args);
    }
}