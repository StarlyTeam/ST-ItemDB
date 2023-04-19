package net.starly.itemdb.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class STSubCommand {

    @NonNull private String kor;
    @NonNull private String eng;
    @NonNull private String description;
    @Nullable private STSubCommand nextCommand;
    @Nullable private BiConsumer<CommandSender, String[]> biConsumer;

    public boolean hasNext() { return nextCommand != null; }
    public void execute(CommandSender sender, String[] args, boolean itemdb) {
        if (sender.isOp() || sender.hasPermission("starly.itemdb." + eng)) biConsumer.accept(sender, args);
        else if (itemdb) sender.sendMessage("펄미션 없네.");
        else sender.sendMessage("펄미션 없음.");
    }
}
