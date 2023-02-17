package AST;

import Game.Game;
import Game.Direction;

import static AST.Node.*;

public class MoveNode extends ExecNode {
    private final Direction direction;

    public MoveNode(Direction direction) {
        this.direction = direction;
    }

    @Override
    public ExecNode execute(Game game) {
        game.move(direction);
        return next;
    }
}
