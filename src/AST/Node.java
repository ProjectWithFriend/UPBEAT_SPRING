package AST;

import Player.Player;

import java.util.Map;

public abstract class Node {
    public abstract static class ExprNode extends Node {
        /**
         * evaluate node for a result
         * @param identifiers identifiers for evaluation (variables)
         * @return result of evaluation
         */
        public abstract long eval(Map<String, Long> identifiers);
    }

    public abstract static class ExecNode extends Node {
        public ExecNode next;

        /**
         * execute a node and maybe change state of execution
         * @param player state of current execution
         * @return next executable node `null` if no more execution
         */
        public abstract ExecNode execute(Player player);
    }
}

