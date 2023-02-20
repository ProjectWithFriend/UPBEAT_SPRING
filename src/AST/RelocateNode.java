package AST;

import Game.Game;

import static AST.Node.*;

public class RelocateNode extends ExecNode {
    @Override
    public ExecNode execute(Game game) {
        game.relocate();
        return null;
    }
}
