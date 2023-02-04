package Parser;

import java.util.Map;

public abstract class ExpressionNode extends Node {
    public abstract long eval(Map<String, Long> bindings);
}
