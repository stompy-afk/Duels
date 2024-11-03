package lol.stompy.duels.arena;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArenaMapper {

    @Getter
    private final List<Block> blocks;

    private Location pos1, pos2;

    /**
     * arena mapper
     */

    public ArenaMapper() {
        this.blocks = new LinkedList<>();
    }

    /**
     * sets the first position
     *
     * @param pos1 pos1
     */

    public void setPos1(Location pos1) {
        this.pos1 = pos1;

        if (pos2 != null)
            this.setBlocks();
    }

    /**
     * sets the second position
     *
     * @param pos2 pos2
     */

    public void setPos2(Location pos2) {
        this.pos2 = pos2;

        if (pos1 != null)
            this.setBlocks();
    }

    /**
     * sets block into the array list
     */

    private void setBlocks() {
        if (pos1.getWorld() != pos2.getWorld())
            return;

        World world = pos1.getWorld();
        List<Block> blocks = new ArrayList<>();

        int x1 = pos1.getBlockX();
        int y1 = pos1.getBlockY();
        int z1 = pos1.getBlockZ();

        int x2 = pos2.getBlockX();
        int y2 = pos2.getBlockY();
        int z2 = pos2.getBlockZ();

        int lowestX = Math.min(x1, x2);
        int lowestY = Math.min(y1, y2);
        int lowestZ = Math.min(z1, z2);

        int highestX = lowestX == x1 ? x2 : x1;
        int highestY = lowestX == y1 ? y2 : y1;
        int highestZ = lowestX == z1 ? z2 : z1;

        for (int x = lowestX; x <= highestX; x++)
            for (int y = lowestY; x <= highestY; y++)
                for (int z = lowestZ; x <= highestZ; z++)
                    blocks.add(world.getBlockAt(x, y, z));
    }

}
