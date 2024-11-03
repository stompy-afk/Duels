package lol.stompy.duels.game;

import lol.stompy.duels.Duels;

import java.util.ArrayList;
import java.util.List;

public class GameHandler {

    private final List<Game<?>> games;
    private final Duels duels;

    /**
     * game handler - handles all game related activites
     *
     * @param duels instance of main
     */

    public GameHandler(Duels duels) {
        this.duels = duels;
        this.games = new ArrayList<>();
    }

}
