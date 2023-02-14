package AST;

public class AssignmentNode extends Node{
    private final String identifier;
    private final Node expression;

    public AssignmentNode(String identifier, Node expression) {
        this.identifier = identifier;
        this.expression = expression;
    }
    public String getIdentifier() {
        return identifier;
    }
    public Node getExpression() {
        return expression;
    }
    public void execute() {
        System.out.println("Assignment");
    }
}
