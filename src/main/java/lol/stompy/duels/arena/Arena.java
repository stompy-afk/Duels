package lol.stompy.duels.arena;

import lombok.Getter;
import org.bukkit.Location;

@Getter
public class Arena {

    private final String name;
    private final ArenaMapper arenaMapper;

    /**
     * Creates an arena
     *
     * @param name name of arena
     * @param arenaMapper arena mapper
     */

    public Arena(String name, ArenaMapper arenaMapper) {
        this.name = name;
        this.arenaMapper = arenaMapper;
    }

}
