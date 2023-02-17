package AST;

import Game.Game;

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
    public ExecNode execute(Game game) {
        trueNode.next = next;
        falseNode.next = next;
        if (condition.eval(game) != 0) {
            return trueNode;
        } else {
            return falseNode;
        }
    }
}
