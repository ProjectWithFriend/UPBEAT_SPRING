package AST;

import Game.Game;

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
    public ExecNode execute(Game game) {
        throw new NotImplemented(); // TODO: implement execution step
    }
}
