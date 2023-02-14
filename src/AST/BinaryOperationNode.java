package AST;

import java.util.Map;

public class BinaryOperationNode extends ExprNode {
    private final ExprNode left;
    private final ExprNode right;
    private final String operator;

    public BinaryOperationNode(ExprNode left, String operator, ExprNode right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public long eval(Map<String, Long> memory) {
        long leftValue = left.eval(memory);
        long rightValue = right.eval(memory);
        return switch (operator) {
            case "+" -> leftValue + rightValue;
            case "-" -> leftValue - rightValue;
            case "*" -> leftValue * rightValue;
            case "/" -> leftValue / rightValue;
            case "%" -> leftValue % rightValue;
            case "^" -> (long) Math.pow(leftValue, rightValue);
            default -> throw new RuntimeException("Unknown operator: " + operator);
        };
    }

    @Override
    public void execute() {
        //Nothing to do this node can't be executed
    }
}
