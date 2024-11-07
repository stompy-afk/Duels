package lol.stompy.duels.game.games;

import lol.stompy.duels.Duels;
import lol.stompy.duels.arena.Arena;
import lol.stompy.duels.game.Game;
import lol.stompy.duels.game.enums.GameEndReason;
import lol.stompy.duels.game.enums.GameState;
import lol.stompy.duels.game.enums.GameType;
import lol.stompy.duels.kit.Kit;
import lol.stompy.duels.profile.Profile;
import lol.stompy.duels.team.Team;
import lol.stompy.duels.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamGame extends Game<Team> {

    /**
     * creates a new game
     *
     * @param inputOne player/team
     * @param inputTwo player/team
     * @param gameType
     * @param arena
     * @param kit      kit of the match
     */

    public TeamGame(Team inputOne, Team inputTwo, GameType gameType, Arena arena, Kit kit) {
        super(inputOne, inputTwo, gameType, arena, kit);
    }

    /**
     * starts the game
     */
    @Override
    public void start() {
        final List<Player> playerList = new ArrayList<>();

        inputOne.getMembers().forEach(profile -> {
            final Player player = Bukkit.getPlayer(profile.getUuid());

            if (player == null)
                return;

            player.getInventory().clear();
            player.teleport(arena.getArenaMapper().getO1());

            playerList.add(player);
        });

        inputTwo.getMembers().forEach(profile -> {
            final Player player = Bukkit.getPlayer(profile.getUuid());

            if (player == null)
                return;

            player.getInventory().clear();
            player.teleport(arena.getArenaMapper().getO2());

            playerList.add(player);
        });

        Bukkit.getScheduler().runTaskTimer(Duels.getInstance(), (task) -> {
            if (startingTime == 0) {
                this.setGameState(GameState.STARTED);
                playerList.forEach(player -> player.sendMessage(CC.translate("&aGame started!")));

                task.cancel();
                return;
            }

            playerList.forEach(player ->
                    player.sendMessage(CC.translate("&aGame starting in..." + startingTime)));

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
    public void end(Team winner, GameEndReason gameEndReason) {

    }
}
