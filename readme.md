## ItemDB - Interfact Library Api for Bukkit Plugin

How to include the API with Maven: 
```xml
<repositories>
    <repository>
        <id>starly.kr</id>
        <url>https://starly.kr:8083</url>
    </repository>
</repositories>

<dependencies>
<dependency>
    <groupId>net.starly.itemdb</groupId>
    <artifactId>ST-ItemDB</artifactId>
    <version>1.0</version>
    <scope>provided</scope>
</dependency>
</dependencies>
```

How to include the API with Gradle:
```gradle
repositories {
    maven {
        url = "http://starly.kr:8083/repository/maven-public/"
        allowInsecureProtocol = true
    }
}

dependencies {
    compileOnly "net.starly.itemdb:ST-ItemDB:1.0"
}
```

**Note**:
You can download the desired version by putting the version number into
the "version" field. Please refer to the release notes for more details.

## License
Copyright (C) 2023 Starly Store <yangdaeyeong0808@gmail.comm>

ST-ItemDB cannot be redistributed, as it is subject to the terms of the
GNU Lesser General Public License as published by the Free Software Foundation.
However, you are allowed to modify it under the terms of the license

You should have received a copy of the GNU Lesser General Public License along with ST-ItemDB.
If not, see http://www.gnu.org/licenses/.

## How to get items from ItemDB
```java
import net.starly.itemdb.ItemDB;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ExampleClass {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            
            // Gets all ItemStacks from ItemDB.
            for (ItemStack itemStack : ItemDB.getApi().getItems()) {
                player.getInventory().addItem(itemstack);
            }
            
            // Get an ItemStack from ItemDB using its ID.
            // The ID is of type String.
            // Put the matching ID inside the getItem("") method.
            player.getInventory().addItem(ItemDB.getApi().getItem("ITEM_ID"));
            return true;
        }
        return false;
    }
}
```