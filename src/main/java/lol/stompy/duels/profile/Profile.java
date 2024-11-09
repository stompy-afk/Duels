package lol.stompy.duels.profile;

import lol.stompy.duels.Duels;
import lol.stompy.duels.kit.KitBook;
import lol.stompy.duels.team.Team;
import lol.stompy.duels.util.cooldown.SimpleCooldown;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

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

    /**
     * creates the profile from a document
     *
     * @param document document to make profile from
     * @param duels instance of main
     */

    public Profile(Document document, Duels duels) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.wins = document.getInteger("wins");
        this.losses = document.getInteger("losses");

        this.kitBooks = new ArrayList<>();
        final List<String> kitBooks = document.getList("kitBooks", String.class);
        kitBooks.forEach(s -> this.kitBooks.add(new KitBook(s, duels)));
    }

    /**
     * puts all the profile's data into a document
     *
     * @return {@link Document}
     */

    public final Document toBson() {
        final List<String> kitBooks = new ArrayList<>();
        this.kitBooks.forEach(kitBook -> kitBooks.add(kitBook.toString()));

        return new Document("_id", uuid.toString())
                .append("wins", wins)
                .append("losses", losses)
                .append("kitBooks", kitBooks);
    }


}
