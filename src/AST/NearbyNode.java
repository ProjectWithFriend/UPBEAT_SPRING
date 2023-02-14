package AST;

public class NearbyNode extends ExprNode{
    private String direction;

    public NearbyNode(String direction) {
        this.direction = direction;
    }
    @Override
    public void execute() {
        System.out.println("NearbyNode: " + direction);
    }
}
