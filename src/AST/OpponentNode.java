package AST;

import Game.Game;

import static AST.Node.*;
import static AST.ASTException.*;

public class OpponentNode extends ExprNode {
    @Override
    public long eval(Game game) {
        return game.opponent();
    }

    @Override
    public String toString() {
        return "opponent";
    }
}
