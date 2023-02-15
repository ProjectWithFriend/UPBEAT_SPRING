package AST;

import Player.Player;
import static AST.Node.*;
import static AST.ASTException.*;

public class AttackNode extends ExecNode {
    private final ExprNode expression;
    private final String direction;

    public AttackNode(ExprNode expression, String direction) {
        this.expression = expression;
        this.direction = direction;
    }

    @Override
    public ExecNode execute(Player player) {
        throw new NotImplemented(); // TODO: implement execution step
    }
}
