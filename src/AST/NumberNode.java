package AST;

import java.util.Map;

public class NumberNode extends ExprNode {
    private int value;

    public NumberNode(int value) {
        this.value = value;
    }

    public long eval(Map<String, Long> memory) {
        return value;
    }
    @Override
    public void execute() {

    }
}
