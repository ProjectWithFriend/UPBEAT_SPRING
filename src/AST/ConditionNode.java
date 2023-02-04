package AST;

import Parser.Node;

public class ConditionNode extends Node {
    public Node expression, conditionTrue, conditionFalse;
}
