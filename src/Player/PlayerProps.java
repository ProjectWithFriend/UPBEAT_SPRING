package Player;

import Game.Direction;
import Region.Region;

import java.util.HashMap;
import java.util.Map;

public class PlayerProps implements Player {
    private final long id;
    private final String name;
    private Region cityCenter;
    private Region cityCrew;
    private long budget;
    private final Map<String, Long> identifier;

    public PlayerProps(long id, String name, long budget, Region cityCenter) {
        this.id = id;
        this.name = name;
        this.identifier = new HashMap<>();
        this.budget = budget;
        this.cityCenter = cityCenter;
        this.cityCrew = cityCenter;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public long getBudget() {
        return budget;
    }

    @Override
    public void updateBudget(long amount) {
        this.budget += amount;
    }

    @Override
    public void moveCityCrew(Direction direction) {

    }

    @Override
    public int getCityCenter() {
        return cityCenter.getLocation();
    }

    @Override
    public int getCityCrew() {
        return cityCrew.getLocation();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public Map<String, Long> getIdentifiers() {
        return identifier;
    }
}
