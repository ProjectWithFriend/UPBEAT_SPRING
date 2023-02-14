package Player;

import java.util.Map;

public class PlayerProps implements Player {
    private int[] cityCenterLocation;
    private final int[] cityCrewLocation;
    private double attackBudget;
    private double moveBudget;
    private double timeLeft;
    private boolean alive;
    private Map<String,Long> identifier;

    public PlayerProps(){
        double budget = 1000;
        this.attackBudget = 0;
        this.moveBudget = 0;
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

    }

    @Override
    public void moveCityCrew(String direction) {

    }
}
