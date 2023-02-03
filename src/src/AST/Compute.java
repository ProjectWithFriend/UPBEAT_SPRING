package AST;

import java.util.Map;

public class Compute implements Expr {
    private Expr left;
    private Expr right;
    private String op;
    public Compute(Expr left, Expr right, String op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public int eval(Map<String, Integer> memory) {
        int r = right.eval(memory);
        int l = left.eval(memory);
        if(op.equals("+")) return l + r;
        if(op.equals("-")) return l - r;
        if(op.equals("*")) return l * r;
        if(op.equals("/")) return l / r;
        if(op.equals("%")) return l % r;
        if(op.equals("^")) return (int) Math.pow(l, r);
        throw new IllegalArgumentException("Unknown operator: " + op);
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append("(");
        left.prettyPrint(s);
        s.append(op);
        right.prettyPrint(s);
        s.append(")");
    }
}
