package AST;

import Player.Player;

public class InvestNode extends Node.ExecNode {
    private final ExprNode expression;

    public InvestNode(ExprNode expression) {
        this.expression = expression;
    }

    @Override
    public ExecNode execute(Player player) {
        throw new ASTException.NotImplemented();
    }
}
