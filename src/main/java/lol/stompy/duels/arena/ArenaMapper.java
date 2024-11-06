package lol.stompy.duels.arena;

import lol.stompy.duels.util.Serializer;
import lol.stompy.duels.util.sLocation;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.*;

@Getter
@Setter
public class ArenaMapper {

    private final Map<Location, Material> blocks;

    private Location pos1, pos2;
    private Location o1, o2;

    /**
     * arena mapper
     */

    public ArenaMapper() {
        this.blocks = new HashMap<>();
    }

    /**
     * Gets arena mapper from string
     *
     * @param s string to deserialize
     */

    public ArenaMapper(String s) {
        this.blocks = new HashMap<>();
        final String[] args = s.split("':'");

        for (int i = 0; i < args.length; i++) {
            final String arg = args[i];

            if (arg.equalsIgnoreCase("pos1")) {
                this.pos1 = sLocation.stringToLocation(args[i + 1]);
                continue;
            }

            if (arg.equalsIgnoreCase("pos2")) {
                this.pos2 = sLocation.stringToLocation(args[i + 1]);
                continue;
            }

            if (arg.equalsIgnoreCase("o1")) {
                this.o1 = sLocation.stringToLocation(args[i + 1]);
                continue;
            }

            if (arg.equalsIgnoreCase("o2")) {
                this.o2 = sLocation.stringToLocation(args[i + 1]);
                continue;
            }

            final Location location = sLocation.stringToLocation(arg);

            if (location == null)
                continue;

            blocks.put(location, Material.valueOf(args[i + 1]));
        }
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

        for (int x = lowestX; x <= highestX; x++) {
            for (int y = lowestY; x <= highestY; y++) {
                for (int z = lowestZ; x <= highestZ; z++) {
                    final Block block = world.getBlockAt(x, y, z);
                    this.blocks.put(block.getLocation(), block.getType());
                }
            }
        }
    }

    /**
     * serializes the arena mapper
     *
     * @return {@link String}
     */

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        blocks.forEach((location, material) -> {
            stringBuilder.append(sLocation.locationToString(location)).append("':'").append(material.toString()).append("':'");
        });

        stringBuilder.append("pos1").append("':'").append(sLocation.locationToString(pos1)).append("':'").append(sLocation.locationToString(pos2)).append("':'");
        stringBuilder.append("o1").append("':'").append(sLocation.locationToString(o1)).append("':'")
                .append("o2").append("':'").append(sLocation.locationToString(o2));

        return stringBuilder.toString();
    }

    @SneakyThrows
    @Override
    protected ArenaMapper clone() {
        return (ArenaMapper) super.clone();
    }
}
