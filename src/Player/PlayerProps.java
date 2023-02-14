package Player;

import java.util.Map;

public class PlayerProps implements Player {
    private String name;
    private int[] cityCenterLocation;
    private final int[] cityCrewLocation;
    private double budget;
    private double actionBudget;
    private double timeLeft;
    private boolean alive;
    private Map<String,Long> identifier;

    public PlayerProps(){
        double budget = 1000;
        this.actionBudget = 1;
        this.timeLeft = 0;
        this.alive = true;
        this.cityCenterLocation = new int[]{0,0};
        this.cityCrewLocation = this.cityCenterLocation;
    }

    @Override
    public Map<String, Long> getIdentifiers() {
        return identifier;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public long attack(String direction) {
        return 0;
    }

    @Override
    public double getBudget() {
        return budget;
    }

    @Override
    public void updateBudget(double amount) {
        this.budget += amount;
    }

    @Override
    public void moveCityCrew(String direction) {
        switch (direction) {
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
        return this.cityCenterLocation;
    }

    @Override
    public int[] getCityCrewLocation() {
        return this.cityCrewLocation;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
