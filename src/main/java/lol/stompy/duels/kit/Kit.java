package lol.stompy.duels.kit;

import lol.stompy.duels.profile.Profile;
import lol.stompy.duels.util.Serializer;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicInteger;

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
     * @param name   name of the kit
     * @param player player to get inventory off
     */

    public Kit(String name, Player player) {
        this.name = name;

        this.contents = player.getInventory().getContents();
        this.armorContents = player.getInventory().getArmorContents();
    }

    /**
     * creates a kit out of a document
     *
     * @param document document to create kit off
     */

    @SneakyThrows
    public Kit(Document document) {
        this.name = document.getString("_id");
        this.contents = Serializer.itemStackArrayFromBase64(document.getString("contents"));
        this.armorContents = Serializer.itemStackArrayFromBase64(document.getString("armorContents"));
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
     * applies all kit books to the player
     *
     * @param profile profile of the player
     * @param player  player to apply kit books to
     */

    public final void applyKitBooks(Profile profile, Player player) {
        player.getInventory().addItem(new KitBook(this, contents, armorContents).getKitBook(0));

        final AtomicInteger i = new AtomicInteger(1);

        profile.getKitBooks().forEach(kitBook -> {
            if (kitBook.getKit().getName().equalsIgnoreCase(this.name)) {
                player.getInventory().addItem(new KitBook(this, contents, armorContents).getKitBook(i.get()));
                i.getAndIncrement();
            }
        });
    }

    /**
     * puts all data of the kit into a bson document
     *
     * @return {@link Document}
     */

    public final Document toBson() {
        return new Document("_id", this.name)
                .append("contents", Serializer.itemStackArrayToBase64(contents))
                .append("armorContents", Serializer.itemStackArrayToBase64(armorContents));
    }

}
