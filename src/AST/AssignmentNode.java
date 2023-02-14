package AST;

import Player.Player;
import static AST.Node.*;
import static AST.ASTException.*;

public class AssignmentNode extends ExecNode {
    private final String identifier;
    private final ExprNode expression;

    public AssignmentNode(String identifier, ExprNode expression) {
        this.identifier = identifier;
        this.expression = expression;
    }
    public String getIdentifier() {
        return identifier;
    }
    public ExprNode getExpression() {
        return expression;
    }
    public ExecNode execute(Player player) {
        throw new NotImplemented(); // TODO: implement execution step
    }
}
