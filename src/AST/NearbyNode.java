package AST;

import java.util.Map;

import static AST.Node.*;
import static AST.ASTException.*;

public class NearbyNode extends ExprNode {
    private final String direction;

    public NearbyNode(String direction) {
        this.direction = direction;
    }

    @Override
    public long eval(Map<String, Long> identifiers) {
        throw new NotImplemented();
    }

    @Override
    public String toString() {
        return "nearby " + direction;
    }
}
