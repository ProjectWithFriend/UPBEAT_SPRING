package AST;

import Player.Player;

public class WhileNode extends ConditionalNode {

    public WhileNode(ExprNode expression, ExecNode statements) {
        super(expression, statements, null);
        if (trueNode == null)
            trueNode = this;
    }

    @Override
    public ExecNode execute(Player player) {
        if (super.condition.eval(null) != 0) {
            if (trueNode != this)
                trueNode.next = this;
            return trueNode;
        }
        return next;
    }
}
