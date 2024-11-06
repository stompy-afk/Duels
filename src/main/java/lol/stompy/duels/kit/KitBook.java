package lol.stompy.duels.kit;

import lol.stompy.duels.util.CC;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
