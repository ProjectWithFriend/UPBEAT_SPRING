package AST;

import Player.Player;

import static AST.Node.*;

public class NearbyNode extends ExprNode {
    private String direction;

    public NearbyNode(String direction) {
        this.direction = direction;
    }
}
