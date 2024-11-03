package lol.stompy.duels.arena;

import lol.stompy.duels.Duels;

import java.util.ArrayList;
import java.util.List;

public class ArenaHandler {

    private final List<Arena> arenas, tempArenas;
    private final Duels duels;

    /**
     * Arena handler - handles all related arena activites
     *
     * @param duels instance of main
     */

    public ArenaHandler(Duels duels) {
        this.duels = duels;

        this.arenas = new ArrayList<>();
        this.tempArenas = new ArrayList<>();
    }

}
