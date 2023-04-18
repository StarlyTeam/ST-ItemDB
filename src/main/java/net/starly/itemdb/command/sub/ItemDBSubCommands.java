package net.starly.itemdb.command.sub;

import net.starly.itemdb.command.STSubCommand;
import org.bukkit.command.ConsoleCommandSender;

public class ItemDBSubCommands {

    private static final STSubCommand giveCommand = new STSubCommand("<플레이어>", "<playerName>", "");
    private static final STSubCommand getCommand = new STSubCommand("", "", "");

    public static final STSubCommand GIVE =
            new STSubCommand("지급", "give", "플레이어에게 ItemDB에 있는 아이템을 지급합니다.", giveCommand, (sender, args) -> {
                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("콘솔 불가능");
                    return;
                }
                if (args.length == 0) sender.sendMessage("입력하셈.");
                else {
                    sender.sendMessage("테스트임.");
                }
            });

    public static final STSubCommand GET =
            new STSubCommand("받기", "get", "플레이어에게 ItemDB에 있는 아이템을 받습니다.", giveCommand, (sender, args) -> {
                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("콘솔 불가능");
                    return;
                }
                if (args.length == 0) sender.sendMessage("입력하셈.");
                else {
                    sender.sendMessage("테스트임.");
                }
            });
}
