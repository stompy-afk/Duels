package lol.stompy.duels;

import lol.stompy.duels.arena.ArenaHandler;
import lol.stompy.duels.game.GameHandler;
import lol.stompy.duels.kit.KitHandler;
import lol.stompy.duels.profile.ProfileHandler;
import lol.stompy.duels.util.CC;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Duels extends JavaPlugin {

    @Getter
    private static Duels instance;

    private ProfileHandler profileHandler;
    private KitHandler kitHandler;
    private GameHandler gameHandler;
    private ArenaHandler arenaHandler;

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
        this.getServer().getConsoleSender().sendMessage(CC.translate("&7[&aTP&&] &fLoading kits, profiles, arenas..."));

        this.kitHandler = new KitHandler(this);
        this.arenaHandler = new ArenaHandler(this);
        this.profileHandler = new ProfileHandler(this);
        this.gameHandler = new GameHandler(this);

        this.getServer().getConsoleSender().sendMessage(CC.translate("&7[&aTP&&] &fLoading all successfully..."));

    }
}
