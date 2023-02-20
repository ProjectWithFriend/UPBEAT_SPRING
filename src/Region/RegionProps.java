package Region;


public class RegionProps implements Region {
    private int location;
    private double budget;
    private String owner;

    public RegionProps(int location){
        this.location = location;
        this.budget = 1000;
        this.owner = "None";
    }

    @Override
    public String getOwner() {
        return this.owner;
    }

    @Override
    public double getBudget() {
        return this.budget;
    }

    @Override
    public void updateBudget(double amount) {
        this.budget += amount;
    }

    @Override
    public void updateOwner(String newOwner) {
        this.owner = newOwner;
    }

    @Override
    public int getLocation() {
        return this.location;
    }

    @Override
    public int getNearby() {
        return Integer.MAX_VALUE; //TODO To remove this in future
    }
}
