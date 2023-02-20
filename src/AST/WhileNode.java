package AST;

import Game.Game;
import AST.ASTException.*;

public class WhileNode extends ConditionalNode {
    private int executionCount = 0;

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
        if (super.condition.eval(game) > 0) {
            if (executionCount >= 10000)
                return next;
            ExecNode last = getLastNode(trueNode);
            if (last != this)
                last.next = this;
            executionCount++;
            return trueNode;
        }
        return next;
    }
}
