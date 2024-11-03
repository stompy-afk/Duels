package lol.stompy.duels.kit;

import lol.stompy.duels.Duels;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class KitHandler {

    private final Map<String, Kit> kitMap;
    private final Duels duels;

    /**
     * kit handler, handles all kit related actions
     *
     * @param duels instance of main
     */

    public KitHandler(Duels duels) {
        this.duels = duels;
        this.kitMap = new ConcurrentHashMap<>();
    }

    /**
     * gets a kit from the map
     *
     * @param name name of kit
     * @return {@link Optional<Kit>}
     */

    public final Optional<Kit> getKit(String name) {
        return Optional.ofNullable(kitMap.get(name));
    }

}
