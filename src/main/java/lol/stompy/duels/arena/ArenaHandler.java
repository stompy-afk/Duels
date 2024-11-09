package lol.stompy.duels.arena;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lol.stompy.duels.Duels;
import lol.stompy.duels.kit.Kit;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArenaHandler {

    private final List<Arena> arenas, tempArenas;
    private final Duels duels;

    private final int arenaSpacing; // Distance between temporary arenas
    private final Random random;

    /**
     * Arena handler - handles all related arena activities
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
     * loads all arenas
     */

    private void load() {
        duels.getServer().getScheduler().runTaskAsynchronously(duels, () -> {
            for (Document document : duels.getMongoHandler().getArenas().find())
                arenas.add(new Arena(document));
        });
    }

    /**
     * handles the removal of the arena
     *
     * @param arena kit to remove
     * @param async to do task async or not
     */

    public final void handleRemoval(Arena arena, boolean async) {
        arenas.remove(arena);

        if (async) {
            duels.getServer().getScheduler().runTaskAsynchronously(duels, () -> handleRemoval(arena, false));
            return;
        }

        Document document = duels.getMongoHandler().getArenas().find(Filters.eq("_id", arena.getName())).first();

        if (document == null)
            return;

        duels.getMongoHandler().getArenas().deleteOne(document);
    }

    /**
     * saves an arena
     *
     * @param arena kit to save
     * @param async to do task async or not
     */

    private void save(Arena arena, boolean async) {
        if (async) {
            duels.getServer().getScheduler().runTaskAsynchronously(duels, () -> save(arena, false));
            return;
        }

        final Document document = duels.getMongoHandler().getArenas().find(Filters.eq("_id", arena.getName())).first();

        if (document == null) {
            duels.getMongoHandler().getArenas().insertOne(arena.toBson());
            return;
        }

        duels.getMongoHandler().getArenas().replaceOne(document, arena.toBson(), new ReplaceOptions().upsert(true));
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
