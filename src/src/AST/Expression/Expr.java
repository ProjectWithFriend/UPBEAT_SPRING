package AST.Expression;

import AST.Node;

import java.util.Map;

public interface Expr extends Node {
    int eval(Map<String, Integer> memory);
}
