package Player;

import Game.Direction;
import Region.*;

import java.util.Map;

public interface Player {
    boolean isAlive();

    long getBudget();

    void updateBudget(long amount);

    String getName();

    long getID();

    Map<String, Long> getIdentifiers();

    void relocate(Region region);
    Region getCityCenter();

}
