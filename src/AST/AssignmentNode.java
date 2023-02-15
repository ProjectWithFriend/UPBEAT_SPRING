package AST;

import Player.Player;

import java.util.Map;

import static AST.Node.*;

public class AssignmentNode extends ExecNode {
    private final String identifier;
    private final ExprNode expression;

    public AssignmentNode(String identifier, ExprNode expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    public ExecNode execute(Player player) {
        Map<String, Long> memory = player.getIdentifiers();
        memory.put(identifier, expression.eval(memory));
        return next;
    }
}
