package Parser;

import java.util.Map;
import java.util.concurrent.Callable;

public abstract class ExpressionNode extends Node {
    public abstract long eval(Map<String, Long> bindings);
}
