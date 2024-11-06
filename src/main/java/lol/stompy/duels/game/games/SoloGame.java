package lol.stompy.duels.game.games;

import lol.stompy.duels.Duels;
import lol.stompy.duels.arena.Arena;
import lol.stompy.duels.game.Game;
import lol.stompy.duels.game.enums.GameEndReason;
import lol.stompy.duels.game.enums.GameState;
import lol.stompy.duels.game.enums.GameType;
import lol.stompy.duels.kit.Kit;
import lol.stompy.duels.profile.Profile;
import lol.stompy.duels.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SoloGame extends Game<Profile> {

    /**
     * creates a new game
     *
     * @param inputOne player/team
     * @param inputTwo player/team
     * @param gameType game type
     * @param arena arena to play in
     * @param kit      of the match
     */
    public SoloGame(Profile inputOne, Profile inputTwo, GameType gameType, Arena arena, Kit kit) {
        super(inputOne, inputTwo, gameType, arena, kit);
    }

    /**
     * starts the game
     */
    @Override
    public void start() {
        final Player playerOne = Bukkit.getPlayer(inputOne.getUuid());
        final Player playerTwo = Bukkit.getPlayer(inputTwo.getUuid());

        if (playerOne == null) {
            this.end(inputTwo, GameEndReason.PLAYER_QUIT);
            return;
        }

        if (playerTwo == null) {
            this.end(inputOne, GameEndReason.PLAYER_QUIT);
            return;
        }

        playerOne.teleport(arena.getArenaMapper().getO1());
        playerTwo.teleport(arena.getArenaMapper().getO2());

        playerOne.getInventory().clear();
        playerTwo.getInventory().clear();

        kit.applyKitBooks(inputOne, playerOne);
        kit.applyKitBooks(inputOne, playerOne);

        this.setGameState(GameState.STARTING);

        Bukkit.getScheduler().runTaskTimer(Duels.getInstance(), (task) -> {
            if (startingTime == 0) {
                this.setGameState(GameState.STARTED);
                task.cancel();
                return;
            }

            playerOne.sendMessage(CC.translate("&aGame starting in..." + startingTime));
            playerTwo.sendMessage(CC.translate("&aGame starting in..." + startingTime));

            startingTime--;
        }, 20L, 20L);
    }

    /**
     * ends the game
     *
     * @param winner        winner of the game
     * @param gameEndReason the reason the game ended
     */
    @Override
    public void end(Profile winner, GameEndReason gameEndReason) {
        final Profile loser = this.getLoser(winner);

        loser.setLosses(loser.getLosses() + 1);
        winner.setWins(winner.getWins() + 1);

        winner.getCooldowns().forEach(simpleCooldown -> simpleCooldown.setExpire(0));
        loser.getCooldowns().forEach(simpleCooldown -> simpleCooldown.setExpire(0));

        if (gameEndReason.equals(GameEndReason.PLAYER_QUIT))
            Duels.getInstance().getProfileHandler().handleRemoval(loser);
    }


}
