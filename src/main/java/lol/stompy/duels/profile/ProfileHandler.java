package lol.stompy.duels.profile;

import lol.stompy.duels.Duels;

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
     * @param profile profile to load
     */

    public final void load(Profile profile) {

    }

    /**
     * handles the removal of a profile
     *
     * @param profile profile to handle removal off
     */

    public final void handleRemoval(Profile profile) {
        if (profile.getTeam() != null)
            profile.getTeam().kick(profile);

        this.save(profile);
        profileMap.remove(profile.getUuid());
    }

    /**
     * saves a profile
     *
     * @param profile profile to save
     */

    private void save(Profile profile) {

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
