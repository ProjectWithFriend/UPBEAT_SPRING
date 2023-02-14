package AST;

public class AttackNode extends Node{
    private final long power;
    private final String direction;

    public AttackNode(long power, String direction){
        this.power = power;
        this.direction = direction;
    }
    @Override
    public void execute() {
        System.out.println("AttackNode: " + power + " " + direction);
    }
}
