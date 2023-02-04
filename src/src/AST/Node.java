package AST;

import java.util.Map;

public interface Node {
    void prettyPrint(StringBuilder s);
    void addChild(Node child);
    String toString();
}
