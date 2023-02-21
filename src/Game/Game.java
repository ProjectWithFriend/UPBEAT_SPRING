package Game;

import Region.Region;

import java.util.List;
import java.util.Map;

public interface Game {
    Map<String, Long> getIdentifiers();
    Map<String, Long> getSpecialIdentifiers();

    /**
     * attempts to attack a region located one unit away from the city crew in the specified direction.
     * @param direction direction to attack
     * @param value expenditure
     */
    void attack(Direction direction, long value);

    /**
     * retrieves deposits from the current region occupied by the city crew.
     * @param value collection amount
     * @return result of collect, true = success, false = fail
     */
    boolean collect(long value);

    /**
     * adds more deposits to the current region occupied by the city crew.
     * @param value deposits
     */
    void invest(long value);

    /**
     * relocates the city center to the current region of city crew.
     */
    void relocate();

    /**
     * looks for the opponent's region closest to the current location of the city crew in a given direction.
     * @param direction direction to look
     * @return 100x+y where x is distance, y deposit of that region if none 0
     */
    long nearby(Direction direction);

    /**
     * moves the city crew one unit in the specified direction.
     * @param direction direction to move
     * @return result of moves, true = success, false = fail
     */
    boolean move(Direction direction);

    /**
     * @return the location of the closest region belonging to an opponent in one of the six directions
     */
    long opponent();

    long getCurrentPlayerID();

    void submitPlan(String constructionPlan);

    List<Region> getTerritory();

    Region getRegion(int index);

    long getBudget();

    void endTurn();
}
