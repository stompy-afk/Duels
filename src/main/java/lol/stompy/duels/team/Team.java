package lol.stompy.duels.team;

import lol.stompy.duels.profile.Profile;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Team {

    private final Profile leader;

    private final List<Profile> members;
    private final String name;
    /**
     * creates a new team
     *
     * @param leader leader of the team
     */

    public Team(Profile leader) {
        this.leader = leader;
        this.name = Objects.requireNonNull(Bukkit.getServer().getPlayer(leader.getUuid())).getName() + "'s Team";

        this.members = new ArrayList<>();
    }

    /**
     * kicks a member of the team
     *
     * @param profile profile to kick of the team
     */

    public final void kick(Profile profile) {
        if (!members.contains(profile))
            return;

        profile.setTeam(null);
    }

}
