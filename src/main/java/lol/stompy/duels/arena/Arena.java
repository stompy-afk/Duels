package lol.stompy.duels.arena;

import lombok.Getter;
import org.bson.Document;
import org.bukkit.Location;

@Getter
public class Arena {

    private final String name;
    private final ArenaMapper arenaMapper;

    /**
     * Creates an arena
     *
     * @param name name of arena
     * @param arenaMapper arena mapper
     */

    public Arena(String name, ArenaMapper arenaMapper) {
        this.name = name;
        this.arenaMapper = arenaMapper;
    }

    /**
     * creates instance of an arena and gets data from document
     *
     * @param document document to get data from
     */

    public Arena(Document document) {
        this.name = document.getString("_id");
        this.arenaMapper = new ArenaMapper(document.getString("arenaMapper"));
    }

    /**
     * puts all data of the arena into a document
     *
     * @return {@link Arena}
     */

    public final Document toBson() {
        return new Document("_id", name)
                .append("arenaMapper", arenaMapper.toString());
    }

}
