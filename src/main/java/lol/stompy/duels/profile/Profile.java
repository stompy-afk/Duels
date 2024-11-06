package lol.stompy.duels.profile;

import lol.stompy.duels.kit.KitBook;
import lol.stompy.duels.team.Team;
import lol.stompy.duels.util.cooldown.SimpleCooldown;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Profile {

    private final UUID uuid;

    private int wins, losses;

    private Team team;

    private List<KitBook> kitBooks;
    private List<SimpleCooldown> cooldowns;

    /**
     * creating a profile from uuid
     *
     * @param uuid uuid to create profile of
     */

    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.kitBooks = new ArrayList<>();
    }



}
