package AST;

import Player.Player;

import static AST.Node.*;

public abstract class ConditionalNode extends ExecNode {
    protected final ExprNode condition;
    protected ExecNode trueNode;
    protected ExecNode falseNode;

    public ConditionalNode(ExprNode condition, ExecNode trueNode, ExecNode falseNode) {
        this.condition = condition;
        this.trueNode = trueNode;
        this.falseNode = falseNode;
    }

    @Override
    public ExecNode execute(Player player) {
        trueNode.next = next;
        falseNode.next = next;
        if (condition.eval(null) != 0) {
            return trueNode;
        } else {
            return falseNode;
        }
    }
}
