package AST;

public class WhileNode extends Node {
    private Node condition;
    private Node body;

    public WhileNode(Node condition, Node body) {
        this.condition = condition;
        this.body = body;
    }

    public void execute() {
        System.out.println("WhileNode.execute");
    }
}
