package Player;

import Region.*;

import java.util.HashMap;
import java.util.Map;

public class PlayerProps implements Player {
    private final long id;
    private final String name;
    private Region cityCenter;
    private long budget;
    private final Map<String, Long> identifier;

    public PlayerProps(long id, String name, long budget, Region cityCenter) {
        this.id = id;
        this.name = name;
        this.identifier = new HashMap<>();
        this.budget = budget;
        this.cityCenter = cityCenter;
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
    public Region getCityCenter() {
        return cityCenter;
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

    @Override
    public void relocate(Region to) {
        cityCenter = to;
    }
}
