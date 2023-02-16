package Game;

import Player.Player;
import Region.Region;

public interface Game {
    Player currentPlayer();
    Region getRegion();
}
