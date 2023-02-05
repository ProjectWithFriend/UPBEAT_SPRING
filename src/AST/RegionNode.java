package AST;

public class RegionNode extends Node{
    private final long amount;
    private final String action;

    public RegionNode(long amount, String action) {
        this.amount = amount;
        this.action = action;
    }
    @Override
    public void execute() {
        System.out.println("Region: " + amount + " " + action);
    }
}
