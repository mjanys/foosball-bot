package cz.janys.foosballbot.dto;

import com.google.common.base.Objects;

/**
 * @author Martin Janys
 */
public class JoinedPlayer {

    private final String player;
    private final boolean jabber;

    public JoinedPlayer(String player) {
        this(player, false);
    }

    public JoinedPlayer(String player, boolean jabber) {
        this.player = player;
        this.jabber = jabber;
    }

    public String getPlayer() {
        return player;
    }

    public boolean isJabber() {
        return jabber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinedPlayer that = (JoinedPlayer) o;
        return Objects.equal(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(player);
    }
}
