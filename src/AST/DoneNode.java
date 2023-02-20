package AST;

import Game.Game;

import static AST.Node.*;

public class DoneNode extends ExecNode {
    @Override
    public ExecNode execute(Game game) {
        return null;
    }
}
