package AST;

import Player.Player;

import java.util.Map;

public abstract class Node {
    public abstract static class ExprNode extends Node {
        public long eval(Map<String, Long> memory){
            return 0;
        }
    }

    public abstract static class ExecNode extends Node {
        public ExecNode next;
        public ExecNode execute(Player player) {
            throw new ASTException.NotImplemented();
        }
    }
}

