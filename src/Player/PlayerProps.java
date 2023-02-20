package Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerProps implements Player {
    String name;
    private int[] cityCenterLocation;
    private final int[] cityCrewLocation;
    private double actionBudget;
    private double budget;
    private double timeLeft;
    private boolean alive;
    private Map<String,Long> identifier;

    public PlayerProps(String name){
        this.identifier = new HashMap<>();
        this.name = name;
        double budget = 1000;
        this.timeLeft = 0;
        this.alive = true;
        this.cityCenterLocation = new int[]{};
        this.cityCrewLocation = new int[]{};
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public long attack(String direction) {
        return 0;
    }

    @Override
    public double getBudget() {
        return 0;
    }

    @Override
    public void updateBudget(double amount) {
        this.budget += amount;
    }

    @Override
    public void moveCityCrew(String direction) {
        switch (direction) { //TODO might be wrong
            case "up" -> this.cityCrewLocation[1] += 1;
            case "down" -> this.cityCrewLocation[1] -= 1;
            case "upleft" -> {
                this.cityCrewLocation[0] -= 1;
                this.cityCrewLocation[1] += 1;
            }
            case "upright" -> {
                this.cityCrewLocation[0] += 1;
                this.cityCrewLocation[1] += 1;
            }
            case "downleft" -> {
                this.cityCrewLocation[0] -= 1;
                this.cityCrewLocation[1] -= 1;
            }
            case "downright" -> {
                this.cityCrewLocation[0] += 1;
                this.cityCrewLocation[1] -= 1;
            }
        }
    }

    @Override
    public int[] getCityCenterLocation() {
        return cityCenterLocation;
    }

    @Override
    public int[] getCityCrewLocation() {
        return cityCrewLocation;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Map<String, Long> getIdentifiers() {
        return identifier;
    }
}
