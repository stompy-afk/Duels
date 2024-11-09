package lol.stompy.duels.kit;

import lol.stompy.duels.Duels;
import lol.stompy.duels.util.CC;
import lol.stompy.duels.util.Serializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;

@Getter
public class KitBook {

    private final Kit kit;
    private final ItemStack[] contents, armorContents;

    /**
     * Kit books
     *
     * @param kit kit
     * @param contents contents
     * @param armorContents armor contents
     */

    public KitBook(Kit kit, ItemStack[] contents, ItemStack[] armorContents) {
        this.kit = kit;
        this.contents = contents;
        this.armorContents = armorContents;
    }

    /**
     * gets the kitbook from a string
     *
     * @param s string to get kitbook from
     * @param duels instance of main
     */

    @SneakyThrows
    public KitBook(String s, Duels duels) {
        final String[] args = s.split("':'");

        this.kit = duels.getKitHandler().getKit(args[0]).orElse(null);
        this.contents = Serializer.itemStackArrayFromBase64(args[1]);
        this.armorContents = Serializer.itemStackArrayFromBase64(args[2]);
    }

    /**
     * puts the kitbook in a string
     *
     * @return {@link KitBook}
     */

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(kit.getName()).append("':'")
                .append(Serializer.itemStackArrayToBase64(contents))
                .append("':'").append(Serializer.itemStackArrayToBase64(armorContents));

        return stringBuilder.toString();
    }

    /**
     * gets the kit book
     *
     * @param i number on the kit book
     * @return {@link ItemStack}
     */

    public final ItemStack getKitBook(int i) {
        final ItemStack stack = new ItemStack(Material.BOOK);
        ItemMeta meta = stack.getItemMeta();

        if (meta == null)
            meta = Bukkit.getItemFactory().getItemMeta(Material.BOOK);

        meta.setDisplayName(CC.translate("&a" + kit.getName() + " " + i));
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);

        stack.setItemMeta(meta);
        return stack;
    }

}
