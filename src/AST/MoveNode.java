package AST;

import Game.Game;

import static AST.Node.*;

public class MoveNode extends ExecNode {
    private final String direction;

    public MoveNode(String direction) {
        this.direction = direction;
    }

    @Override
    public ExecNode execute(Game game) {
        throw new ASTException.NotImplemented(); // TODO: implement execution step
    }
}
