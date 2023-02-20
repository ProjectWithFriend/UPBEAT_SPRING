package AST;

import Game.Game;
import AST.ASTException.*;

import java.util.Map;

import static AST.Node.*;

public class AssignmentNode extends ExecNode {
    private final String identifier;
    private final ExprNode expression;

    public AssignmentNode(String identifier, ExprNode expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    public ExecNode execute(Game game) {
        Map<String, Long> memory = game.getIdentifiers();
        memory.put(identifier, expression.eval(game));
        return next;
    }

    public ExecNode execute(Map<String, Long> map) {
        if (!(expression instanceof AtomicNode))
            throw new IntegerRequired(expression.toString());
        map.put(identifier, ((AtomicNode) expression).eval());
        return next;
    }
}
