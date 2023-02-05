package AST;

public class ConditionNode extends Node{
    private final ExprNode condition;
    private final Node trueNode;
    private final Node falseNode;

    public ConditionNode(ExprNode condition, Node trueNode, Node falseNode){
        this.condition = condition;
        this.trueNode = trueNode;
        this.falseNode = falseNode;
    }
    @Override
    public void execute() {
        System.out.println("ConditionNode: " + "something need to be executed here");
    }
}
