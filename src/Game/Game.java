package Game;

import Player.Player;
import Region.Region;

import java.util.List;

public interface Game {
    Player currentPlayer();
    Region getRegion();
    List<Region> getTerritory();
}
