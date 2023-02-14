package AST;

public class IfElseNode extends ConditionalNode {
    public IfElseNode(ExprNode condition, ExecNode trueNode, ExecNode falseNode) {
        super(condition, trueNode, falseNode);
    }
}
