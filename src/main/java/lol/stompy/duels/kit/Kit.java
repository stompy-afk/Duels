package lol.stompy.duels.kit;

import lol.stompy.duels.util.Serializer;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Setter
@Getter
public class Kit {

    private String name;
    private ItemStack[] contents, armorContents;

    /**
     * creates a kit
     *
     * @param name name of kit
     */

    public Kit(String name) {
        this.name = name;
    }

    /**
     * creates the kit with the inventory of the player
     *
     * @param name name of the kit
     * @param player player to get inventory off
     */

    public Kit(String name, Player player) {
        this.name = name;

        this.contents = player.getInventory().getContents();
        this.armorContents = player.getInventory().getArmorContents();
    }

    /**
     * applies to player
     *
     * @param player player to set kit to
     */

    public final void apply(Player player) {
        player.getInventory().setContents(this.contents);
        player.getInventory().setArmorContents(this.armorContents);
    }

    /**
     * puts all data of the kit into a bson document
     *
     * @return {@link Document}
     */

    public final Document toBson() {
        return new Document("_id", this.name)
                .append("contents", contents == null ? "null" : Serializer.itemStackArrayToBase64(contents))
                .append("armorContents", armorContents == null ? "null" : Serializer.itemStackArrayToBase64(armorContents));
    }

}
