package lol.stompy.duels.game;

import lol.stompy.duels.arena.Arena;
import lombok.Getter;

@Getter
public abstract class Game<T> {

    protected final T inputOne, inputTwo;
    protected final GameType gameType;
    protected final Arena arena;

    protected GameState gameState;

    /**
     * creates a new game
     *
     * @param inputOne player/team
     * @param inputTwo player/team
     */

    public Game(T inputOne, T inputTwo, GameType gameType, Arena arena) {
        this.inputOne = inputOne;
        this.inputTwo = inputTwo;

        this.arena = arena;
        this.gameType = gameType;
        this.gameState = GameState.CREATED;
    }

    /**
     * starts the game
     */

    public abstract void start();

    /**
     * ends the game
     */

    public abstract void end();


}
