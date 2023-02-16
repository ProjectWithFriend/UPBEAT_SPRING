package AST;

import java.util.Map;

import static AST.Node.*;
import static AST.ASTException.*;

public class BinaryOperationNode extends ExprNode {
    private final ExprNode left;
    private final ExprNode right;
    private final String operator;

    public BinaryOperationNode(ExprNode left, String operator, ExprNode right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public long eval(Map<String, Long> identifiers) {
        long leftValue = left.eval(identifiers);
        long rightValue = right.eval(identifiers);
        return switch (operator) {
            case "+" -> leftValue + rightValue;
            case "-" -> leftValue - rightValue;
            case "*" -> leftValue * rightValue;
            case "/" -> leftValue / rightValue;
            case "%" -> leftValue % rightValue;
            case "^" -> (long) Math.pow(leftValue, rightValue);
            default -> throw new UnknownOperator(operator);
        };
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", left.toString(), operator, right.toString());
    }
}
