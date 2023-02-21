package Player;

import Game.Direction;

import java.util.Map;

public interface Player {
    boolean isAlive();

    long getBudget();

    void updateBudget(long amount);

    void moveCityCrew(Direction direction);

    int getCityCenter();

    int getCityCrew();

    String getName();

    long getID();

    Map<String, Long> getIdentifiers();

    void relocate(int location);
}
