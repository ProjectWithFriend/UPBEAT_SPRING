package AST;

import Player.Player;

import static AST.Node.*;

public class MoveNode extends ExecNode {
    private String direction;

    public MoveNode(String direction){
        this.direction = direction;
    }
    public ExecNode execute(Player player){
        throw new ASTException.NotImplemented(); // TODO: implement execution step
    }
}
