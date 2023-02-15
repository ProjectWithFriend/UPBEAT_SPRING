package Parser;

import AST.Node;

public interface Parser {
    /**
     * parse given data to AST structure
     * @return first executable node of AST
     */
    Node.ExecNode parse();
}
