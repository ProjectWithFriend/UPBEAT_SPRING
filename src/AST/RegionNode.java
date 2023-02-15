package AST;

import Player.Player;

import static AST.Node.*;
import static AST.ASTException.*;

public class RegionNode extends ExecNode {
    private final ExprNode expression;
    private final String action;

    public RegionNode(ExprNode expression, String action) {
        this.expression = expression;
        this.action = action;
    }

    @Override
    public ExecNode execute(Player player) {
        throw new NotImplemented(); // TODO: implement execution step
    }
}
