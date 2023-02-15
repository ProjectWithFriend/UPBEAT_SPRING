package AST;

import Player.Player;

public class CollectNode extends Node.ExecNode {
    private final ExprNode expression;

    public CollectNode(ExprNode expression) {
        this.expression = expression;
    }

    @Override
    public ExecNode execute(Player player) {
        throw new ASTException.NotImplemented();
    }
}
