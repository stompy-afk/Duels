package lol.stompy.duels.arena;

import lol.stompy.duels.Duels;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArenaHandler {

    private final List<Arena> arenas, tempArenas;
    private final Duels duels;

    private final int arenaSpacing; // Distance between temporary arenas
    private final Random random;

    /**
     * Arena handler - handles all related arena activites
     *
     * @param duels instance of main
     */

    public ArenaHandler(Duels duels) {
        this.duels = duels;

        this.random = new Random();
        this.arenaSpacing = 200;

        this.arenas = new ArrayList<>();
        this.tempArenas = new ArrayList<>();
    }

    /**
     * Creates a temporary arena at a random location far from other arenas.
     *
     * @param originalArena The original arena template.
     * @return The new temporary arena.
     */

    public final Arena createTemporaryArena(Arena originalArena, World world) {
        int offsetX = random.nextInt(10000) * arenaSpacing;
        int offsetZ = random.nextInt(10000) * arenaSpacing;

        Location pos1 = originalArena.getArenaMapper().getPos1().clone().add(offsetX, 0, offsetZ);
        Location pos2 = originalArena.getArenaMapper().getPos2().clone().add(offsetX, 0, offsetZ);

        Location player1Spawn = originalArena.getArenaMapper().getO1().clone().add(offsetX, 0, offsetZ);
        Location player2Spawn = originalArena.getArenaMapper().getO2().add(offsetX, 0, offsetZ);

        ArenaMapper tempArenaMapper = originalArena.getArenaMapper().clone();
        tempArenaMapper.setPos1(pos1);
        tempArenaMapper.setPos2(pos2);

        tempArenaMapper.setO1(player1Spawn);
        tempArenaMapper.setO2(player2Spawn);

        Arena tempArena = new Arena("Temp_" + originalArena.getName(), tempArenaMapper);
        tempArenas.add(tempArena);

        return tempArena;
    }

}
