package AST;

import Game.Direction;
import Game.Game;

import static AST.Node.*;

public class NearbyNode extends ExprNode {
    private final Direction direction;

    public NearbyNode(Direction direction) {
        this.direction = direction;
    }

    @Override
    public long eval(Game game) {
        return game.nearby(direction);
    }

    @Override
    public String toString() {
        return "nearby " + direction;
    }
}
