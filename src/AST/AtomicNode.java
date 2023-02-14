package AST;

import java.util.Map;

import static AST.Node.*;
import static AST.ASTException.*;

public class AtomicNode extends ExprNode {
    private final int value;
    private final String identifer;

    public AtomicNode(int value) {
        this.value = value;
        this.identifer = null;
    }

    public AtomicNode(String identifer) {
        this.value = 0;
        this.identifer = identifer;
    }

    @Override
    public long eval(Map<String, Long> memory) {
        if (identifer == null) {
            return value;
        } else {
            throw new NotImplemented();
        }
    }
}
