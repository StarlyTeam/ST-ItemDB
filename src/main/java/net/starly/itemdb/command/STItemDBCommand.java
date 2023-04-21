package net.starly.itemdb.command;

import net.starly.itemdb.message.MessageContext;
import net.starly.itemdb.message.enums.MessageType;
import net.starly.itemdb.message.impl.ItemDBMessageContextImpl;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.*;
import java.util.stream.Collectors;

public abstract class STItemDBCommand implements CommandExecutor, TabCompleter {

    private final String command;
    private final List<STSubCommand> subCommands = new ArrayList<>();
    private final MessageContext context;

    public STItemDBCommand(JavaPlugin plugin, String command) {
        this.command = command;
        PluginCommand cmd = Objects.requireNonNull(plugin.getCommand(command));
        cmd.setExecutor(this);
        cmd.setTabCompleter(this);
        context = ItemDBMessageContextImpl.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        execute(sender, label, !label.equals(this.command), args);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        String first;
        boolean korean = !label.equals(this.command);
        try {
            first = args[0];
        } catch (ArrayIndexOutOfBoundsException exception) {
            first = "";
        }
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

    protected abstract boolean isPlayerTabComplete();

    private void execute(CommandSender sender, String label, boolean korean, String[] args) {
        if (args.length == 0) printHelpLine(sender, label, korean);
        else {
            Optional<STSubCommand> optionalSTSubCommand = subCommands.stream().filter((iterator) -> iterator.getKor().equals(args[0]) || iterator.getEng().equals(args[0])).findFirst();
            if (optionalSTSubCommand.isPresent()) {
                STSubCommand sub = optionalSTSubCommand.get();
                if (sub.hasNext() && args.length == 1 && isPlayerTabComplete())
                    context.get(MessageType.ERROR, "noPlayerName").send(sender);
                else
                    sub.execute(sender, args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length));
            } else context.get(MessageType.ERROR, "wrongCommand").send(sender);
        }
    }

    public void printHelpLine(CommandSender sender, String label, boolean korean) {
        reformattedHelpline(korean, label, subCommands
                .stream()
                .filter((iterator) -> sender.isOp() || sender.hasPermission("starly.itemdb." + iterator.getEng()))
                .collect(Collectors.toList())
        ).forEach(sender::sendMessage);
    }

    protected List<String> reformattedHelpline(boolean korean, String label, List<STSubCommand> subCommandList) {
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

    private List<String> getSubCommands(boolean korean) {
        return subCommands.stream().map((it) -> {
            if (korean) return it.getKor();
            else return it.getEng();
        }).collect(Collectors.toList());
    }

    protected void registerSubCommand(STSubCommand... subCommands) {
        this.subCommands.addAll(Arrays.asList(subCommands));
    }
}