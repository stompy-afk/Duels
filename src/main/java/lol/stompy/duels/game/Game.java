package lol.stompy.duels.game;

import lol.stompy.duels.arena.Arena;
import lol.stompy.duels.game.enums.GameEndReason;
import lol.stompy.duels.game.enums.GameState;
import lol.stompy.duels.game.enums.GameType;
import lol.stompy.duels.kit.Kit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Game<T> {

    protected final T inputOne, inputTwo;

    protected final GameType gameType;
    protected final Arena arena;
    protected final Kit kit;

    protected int startingTime;

    private GameState gameState;

    /**
     * creates a new game
     *
     * @param inputOne player/team
     * @param inputTwo player/team
     * @param kit kit of the match
     */

    public Game(T inputOne, T inputTwo, GameType gameType, Arena arena, Kit kit) {
        this.inputOne = inputOne;
        this.inputTwo = inputTwo;

        this.kit = kit;

        this.startingTime = 5;

        this.arena = arena;
        this.gameType = gameType;
        this.gameState = GameState.CREATED;
    }

    /**
     * Gets the loser of the match
     *
     * @param winner winner
     * @return {@link T}
     */

    public final T getLoser(T winner) {
        if (winner.equals(inputOne))
            return inputTwo;

        return inputOne;
    }

    /**
     * starts the game
     */

    public abstract void start();

    /**
     * ends the game
     *
     * @param winner winner of the game
     * @param gameEndReason the reason the game ended
     */

    public abstract void end(T winner, GameEndReason gameEndReason);


}
