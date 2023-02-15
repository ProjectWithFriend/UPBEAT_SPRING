package AST;

import Player.Player;

import static AST.Node.*;

public class DoneNode extends ExecNode {
    @Override
    public ExecNode execute(Player player) {
        return null;
    }
}
