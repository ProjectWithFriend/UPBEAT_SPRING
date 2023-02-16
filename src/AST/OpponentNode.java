package AST;

import java.util.Map;

import static AST.Node.*;
import static AST.ASTException.*;

public class OpponentNode extends ExprNode {
    @Override
    public long eval(Map<String, Long> identifiers) {
        throw new NotImplemented();
    }

    @Override
    public String toString() {
        return "opponent";
    }
}
