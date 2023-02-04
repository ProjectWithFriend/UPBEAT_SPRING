package AST;

import Parser.Node;

public class Command {
    public static class AssignCommand extends Node {
        public AssignCommand(String name, long value) {// TODO: implement it

        }
    }

    public static class ActionCommand extends Node {
        public ActionCommand(Parameter parameter) {// TODO: implement it

        }
    }

    public static class RegionCommand extends Node {
        public RegionCommand(Parameter parameter, Node expression) {// TODO: implement it

        }
    }

    public static class MoveCommand extends Node {
        public MoveCommand(Parameter direction) {// TODO: implement it

        }
    }

    public static class AttackCommand extends Node {
        public AttackCommand(Parameter direction, Node expression) {// TODO: implement it

        }
    }
}
