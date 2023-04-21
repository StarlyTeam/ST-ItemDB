package net.starly.itemdb.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.starly.itemdb.message.enums.MessageType;
import net.starly.itemdb.message.impl.ItemDBMessageContextImpl;
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
        else if(itemdb) ItemDBMessageContextImpl.getInstance().get(MessageType.ERROR, "noPermission").send(sender);
        else ItemDBMessageContextImpl.getInstance().get(MessageType.ERROR, "noPermission").send(sender);
    }
}
