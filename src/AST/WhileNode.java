package AST;

import Game.Game;

public class WhileNode extends ConditionalNode {

    public WhileNode(ExprNode expression, ExecNode statements) {
        super(expression, statements, null);
        if (trueNode == null)
            trueNode = this;
    }

    private ExecNode getLastNode(ExecNode node) {
        while (node != this && node != null) {
            if (node.next == this || node.next == null) return node;
            node = node.next;
        }
        return this;
    }

    @Override
    public ExecNode execute(Game game) {
        if (super.condition.eval(game.currentPlayer().getIdentifiers()) != 0) {
            ExecNode last = getLastNode(trueNode);
            if (last != this)
                last.next = this;
            return trueNode;
        }
        return next;
    }
}
