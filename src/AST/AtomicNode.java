package AST;

import Game.Game;

import static AST.Node.*;
import static AST.ASTException.*;

public class AtomicNode extends ExprNode {
    private final int value;
    private final String identifier;

    public AtomicNode(int value) {
        this.value = value;
        this.identifier = null;
    }

    public AtomicNode(String identifier) {
        this.value = 0;
        this.identifier = identifier;
    }

    @Override
    public long eval(Game game) {
        if (identifier == null) {
            return value;
        } else {
            Long value = game.getIdentifiers().get(identifier);
            if (value == null)
                throw new UndefinedIdentifier(identifier);
            return value;
        }
    }

    @Override
    public String toString() {
        return identifier != null ? identifier : String.valueOf(value);
    }
}
