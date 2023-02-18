package Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerProps implements Player {
    String name;
    private int cityCenterLocation;
    private int cityCrewLocation;
    private double actionBudget;
    private double budget;
    private double timeLeft;
    private boolean alive;
    private Map<String,Long> identifier;

    public PlayerProps(String name,double budget,double centerDeposit){
        this.identifier = new HashMap<>();
        this.name = name;
        this.budget = budget;
        this.timeLeft = 0;
        this.alive = true;
        this.cityCenterLocation = 0;
        this.cityCrewLocation = 0;
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
        switch (direction) { //TODO : implement this method
        }
    }

    @Override
    public int getCityCenterLocation() {
        return cityCenterLocation;
    }

    @Override
    public int getCityCrewLocation() {
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
