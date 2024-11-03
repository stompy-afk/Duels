package lol.stompy.duels;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Duels extends JavaPlugin {

    @Getter
    private static Duels instance;

    /**
     * plugin loading logic
     */

    @Override
    public void onLoad() {
        instance = this;
        this.saveDefaultConfig();
    }

    /**
     * plugin enabling logic
     */

    @Override
    public void onEnable() {
        super.onEnable();
    }
}
