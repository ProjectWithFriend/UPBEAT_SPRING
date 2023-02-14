package Region;

public interface Region {
    String getOwner();
    double getBudget();
    void updateBudget(double amount);
    void updateOwner(String newOwner);
    int[] getLocation();
}
