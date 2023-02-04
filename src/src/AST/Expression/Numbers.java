package AST.Expression;

import AST.Expression.Expr;
import AST.Node;

import java.util.Map;

public class Numbers implements Expr {
    private int value;
    public Numbers(int value) {
        this.value = value;
    }
    @Override
    public int eval(Map<String, Integer> memory) {
        return value;
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append(value);
    }

    @Override
    public void addChild(Node child) {
        // do nothing
    }
}
