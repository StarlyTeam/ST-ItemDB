package net.starly.itemdb.command;

import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.*;
import java.util.stream.Collectors;

public abstract class STItemDBCommand implements CommandExecutor, TabCompleter {

    private final String command;
    private final List<STSubCommand> subCommands = new ArrayList<>();

    public STItemDBCommand(JavaPlugin plugin, String command) {
        this.command = command;
        PluginCommand pluginCommand = Objects.requireNonNull(plugin.getCommand(command));
        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        execute(sender, label, !label.equals(this.command), args);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        String first;
        boolean korean = !label.equals(command);

        try { first = args[0]; } catch (ArrayIndexOutOfBoundsException exception) { first = ""; }
        if (args.length <= 1) return StringUtil.copyPartialMatches(first, getSubCommands(korean)
                .stream()
                .filter((iterator) -> sender.isOp() || sender.hasPermission("starly.itemdb." + iterator))
                .collect(Collectors.toList()), new ArrayList<>()
        );
        else if (isPlayerTabComplete()) {
            if (args.length == 2) return null;
            else return Collections.emptyList();
        } else return Collections.emptyList();
    }

    private void execute(CommandSender sender, String label, boolean korean, String[] args) {
        if (args.length == 0) printHelpLine(sender, label, korean);
        else {
            Optional<STSubCommand> optionalSTSubCommand = subCommands.stream().filter((iterator) ->
                    iterator.getKor().equals(args[0]) || iterator.getEng().equals(args[0])).findFirst();
            if (optionalSTSubCommand.isPresent()) {
                STSubCommand subCommand = optionalSTSubCommand.get();
                if (subCommand.hasNext() && args.length == 1 && isPlayerTabComplete()) sender.sendMessage("플레이어 이름 입력 안함.");
                else subCommand.execute(sender, args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length), label.equalsIgnoreCase("itemdb") || label.equals("아이템저장소"));
            } else sender.sendMessage("잘못된 커맨드임.");
        }
    }

    private List<String> getSubCommands(boolean korean) {
        return subCommands.stream().map((iterator) -> {
            if (korean) return iterator.getKor();
            else return iterator.getEng();
        }).collect(Collectors.toList());
    }

    protected List<String> reformattedHelpLine(boolean korean, String label, List<STSubCommand> subCommandList) {
        return subCommandList.stream().map((iterator) -> {
            StringBuilder builder = new StringBuilder("§6/");
            builder.append(label);
            builder.append(" ");

            if (korean) {
                builder.append(iterator.getKor());
                STSubCommand pointer = iterator;
                while (pointer.hasNext()) {
                    pointer = pointer.getNextCommand();
                    builder.append(" ").append(pointer.getKor());
                }
            } else {
                builder.append(iterator.getEng());
                STSubCommand pointer = iterator;
                while (pointer.hasNext()) {
                    pointer = pointer.getNextCommand();
                    builder.append(" ").append(pointer.getEng());
                }
            }
            builder.append("§f : ").append(iterator.getDescription());
            return builder.toString();
        }).collect(Collectors.toList());
    }

    protected void printHelpLine(CommandSender sender, String label, boolean korean) {
        reformattedHelpLine(korean, label, subCommands
                .stream()
                .filter((iterator) -> sender.isOp() || sender.hasPermission("starly.itemdb." + iterator.getEng()))
                .collect(Collectors.toList())
        ).forEach(sender::sendMessage);
    }

    protected void registerSubCommand(STSubCommand... subCommands) {
        this.subCommands.addAll(Arrays.asList(subCommands));
    }

    protected abstract boolean isPlayerTabComplete();
}
