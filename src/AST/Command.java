package AST;

import Parser.ExpressionNode;
import Parser.Node;

public class Command {
    // TODO: ใส่โหนด
    public static class AssignCommandNode extends Node {
        String name;
        ExpressionNode expr;

        public AssignCommandNode(String name, ExpressionNode value) {// TODO: implement it
            this.name = name;
            expr = value;
        }
    }

    public static class ActionCommandNode extends Node {
        GameCommand command;
        public ActionCommandNode(GameCommand gameCommand) {// TODO: implement it
            this.command=gameCommand;
        }
    }

    public static class RegionCommandNode extends Node {
        public RegionCommandNode(GameCommand gameCommand, Node expression) {// TODO: implement it

        }
    }

    public static class MoveCommandNode extends Node {
        public MoveCommandNode(GameCommand direction) {// TODO: implement it

        }
    }

    public static class AttackCommandNode extends Node {
        public AttackCommandNode(GameCommand direction, Node expression) {// TODO: implement it

        }
    }
}
