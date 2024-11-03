package lol.stompy.duels.arena;

import org.bukkit.Location;

public class Arena {

    private final String name;
    private final ArenaMapper arenaMapper;

    /**
     * creates an arena
     *
     * @param name name of arena
     * @param arenaMapper arena mapper
     */

    public Arena(String name, ArenaMapper arenaMapper) {
        this.name = name;
        this.arenaMapper = arenaMapper;
    }

}
