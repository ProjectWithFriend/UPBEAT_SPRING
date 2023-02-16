package AST;

import Game.Game;

public class InvestNode extends Node.ExecNode {
    private final ExprNode expression;

    public InvestNode(ExprNode expression) {
        this.expression = expression;
    }

    @Override
    public ExecNode execute(Game game) {
        throw new ASTException.NotImplemented();
    }
}
