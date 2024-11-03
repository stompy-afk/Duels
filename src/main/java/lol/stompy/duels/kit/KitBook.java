package lol.stompy.duels.kit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public record KitBook(Kit kit, ItemStack[] armorContents, ItemStack[] contents) {

}
