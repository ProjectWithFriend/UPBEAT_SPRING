package Player;

public interface Player {
    boolean isAlive();
    long attack(String direction);
    double getBudget();
    void updateBudget(double amount);
    void moveCityCrew(String direction);
    int[] getCityCenterLocation();
    int[] getCityCrewLocation();
    String getName();
}
