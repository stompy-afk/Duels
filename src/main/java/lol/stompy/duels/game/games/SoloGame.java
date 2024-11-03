package lol.stompy.duels.game.games;

import lol.stompy.duels.arena.Arena;
import lol.stompy.duels.game.Game;
import lol.stompy.duels.game.GameType;
import lol.stompy.duels.profile.Profile;

public class SoloGame extends Game<Profile> {

    /**
     * creates a new game
     *
     * @param inputOne player/team
     * @param inputTwo player/team
     */

    public SoloGame(Profile inputOne, Profile inputTwo, Arena arena) {
        super(inputOne, inputTwo, GameType.SOLO, arena);
    }

    /**
     * starts the game
     */
    @Override
    public void start() {

    }

    /**
     * ends the game
     */
    @Override
    public void end() {

    }
}
