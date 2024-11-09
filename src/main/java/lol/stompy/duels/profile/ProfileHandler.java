package lol.stompy.duels.profile;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lol.stompy.duels.Duels;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ProfileHandler {

    private final Map<UUID, Profile> profileMap;
    private final Duels duels;

    /**
     * profile handler - handles all related profile actions
     *
     * @param duels instance of main
     */

    public ProfileHandler(Duels duels) {
        this.duels = duels;
        this.profileMap = new ConcurrentHashMap<>();
    }

    /**
     * loads a profile
     *
     * @param uuid profile to load
     * @param async to do task async or not
     */

    public final void load(UUID uuid, boolean async) {

        if (async) {
            duels.getServer().getScheduler().runTaskAsynchronously(duels, () -> this.load(uuid, false));
            return;
        }

        Document document = duels.getMongoHandler().getProfiles().find(Filters.eq("_id", uuid.toString())).first();

        if (document == null) {
            this.save(profileMap.put(uuid, new Profile(uuid)), false);
            return;
        }

        final Profile profile = new Profile(document, duels);
        profileMap.put(uuid, profile);
    }

    /**
     * handles the removal of a profile
     *
     * @param profile profile to handle removal off
     */

    public final void handleRemoval(Profile profile, boolean async) {
        if (profile.getTeam() != null)
            profile.getTeam().kick(profile);

        this.save(profile, async);
        profileMap.remove(profile.getUuid());
    }

    /**
     * saves a profile
     *
     * @param profile profile to save
     * @param async to do task async or not
     */

    private void save(Profile profile, boolean async) {
        if (async) {
            duels.getServer().getScheduler().runTaskAsynchronously(duels, () -> save(profile, false));
            return;
        }

        final Document document = duels.getMongoHandler().getProfiles().find(Filters.eq("_id", profile.getUuid().toString())).first();

        if (document == null) {
            duels.getMongoHandler().getProfiles().insertOne(profile.toBson());
            return;
        }

        duels.getMongoHandler().getProfiles().replaceOne(document, profile.toBson(), new ReplaceOptions().upsert(true));
    }


    /**
     * gets a profile from the map
     *
     * @param uuid uuid of profile
     * @return {@link Optional<Profile>}
     */

    public final Optional<Profile> getProfile(UUID uuid) {
        return Optional.ofNullable(profileMap.get(uuid));
    }


}
