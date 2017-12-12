package example.cerki.osuhub;

import example.cerki.osuhub.List.Player;

/**
 * Created by cerki on 30-Nov-17.
 */

@Deprecated
public interface PlayersTableWrapper {
    void insertPlayer(Player player);
    Player getPlayer(int userId);
}
