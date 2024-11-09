package lol.stompy.duels.kit;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lol.stompy.duels.Duels;
import lol.stompy.duels.profile.Profile;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
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
        this.kitMap = new HashMap<>();

        this.load();
    }

    /**
     * loads all kits
     */

    private void load() {
        duels.getServer().getScheduler().runTaskAsynchronously(duels, () -> {
           for (Document document : duels.getMongoHandler().getKits().find()) {
               final Kit kit = new Kit(document);
               kitMap.put(kit.getName().toUpperCase(), kit);
           }
        });
    }

    /**
     * creates a kit
     *
     * @param s name to create kit off
     */

    public final void createKit(String s) {
        final Kit kit = new Kit(s);

        this.save(kit, true);
        kitMap.put(s.toUpperCase(), kit);
    }

    /**
     * creates a kit off a player
     *
     * @param s name of kit
     * @param player player to create kit off
     */

    public final void createKit(String s, Player player) {
        final Kit kit = new Kit(s, player);

        this.save(kit, true);
        kitMap.put(s.toUpperCase(), kit);
    }

    /**
     * handles the removal of the kit
     *
     * @param kit kit to remove
     * @param async to do task async or not
     */

    public final void handleRemoval(Kit kit, boolean async) {
        kitMap.remove(kit.getName().toUpperCase());

        if (async) {
            duels.getServer().getScheduler().runTaskAsynchronously(duels, () -> handleRemoval(kit, false));
            return;
        }

        Document document = duels.getMongoHandler().getKits().find(Filters.eq("_id", kit.getName())).first();

        if (document == null)
            return;

        duels.getMongoHandler().getKits().deleteOne(document);
    }

    /**
     * saves a kit
     *
     * @param kit kit to save
     * @param async to do task async or not
     */

    private void save(Kit kit, boolean async) {
        if (async) {
            duels.getServer().getScheduler().runTaskAsynchronously(duels, () -> save(kit, false));
            return;
        }

        final Document document = duels.getMongoHandler().getKits().find(Filters.eq("_id", kit.getName())).first();

        if (document == null) {
            duels.getMongoHandler().getArenas().insertOne(kit.toBson());
            return;
        }

        duels.getMongoHandler().getKits().replaceOne(document, kit.toBson(), new ReplaceOptions().upsert(true));
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
