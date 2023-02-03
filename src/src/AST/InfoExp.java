package AST;

import java.util.Map;

public class InfoExp implements Expr, Actions{
    private String info;

    public InfoExp(String info) {
        this.info = info;
    }
    public InfoExp(String info , String direction) {
        this.info = info + " " + direction;
    }
    @Override
    public int eval(Map<String, Integer> memory) {
        return 0;
    }

    @Override
    public void prettyPrint(StringBuilder s) {
    }

    @Override
    public void addChild(Node child) {
    }

    @Override
    public String toString() {
        return info;
    }
}
