package Parser;

public class ParserException extends RuntimeException {
    public ParserException(String message) {
        super(message);
    }

    public static class NotImplement extends ParserException {
        public NotImplement() {
            super("no implementation available");
        }
    }

    public static class NoCommand extends ParserException {
        public NoCommand() {
            super("command not found");
        }
    }

    public static class ReserveIdentifier extends ParserException {
        public ReserveIdentifier() {
            super("use reserved keyword as identifier");
        }
    }

    public static class ArithmeticError extends ParserException {
        public ArithmeticError(String message) {
            super(message);
        }
    }

    public static class OperatorNotFound extends ParserException {
        public OperatorNotFound(String message) {
            super(message);
        }
    }
}
