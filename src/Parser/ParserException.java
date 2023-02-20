package Parser;

public abstract class ParserException extends RuntimeException {
    protected ParserException(String m, int line) {
        super(String.format("%s at line %d", m, line));
    }

    public static class CommandNotFound extends ParserException {
        public CommandNotFound(String cmd, int line) {
            super(String.format("command '%s' not found", cmd), line);
        }
    }

    public static class CommandNotImplemented extends ParserException {
        public CommandNotImplemented(String cmd, int line) {
            super(String.format("command '%s' not implemented", cmd), line);
        }
    }

    public static class InvalidDirection extends ParserException {
        public InvalidDirection(String direction, int line) {
            super(String.format("invalid direction '%s'", direction), line);
        }
    }

    public static class InvalidInfoExpression extends ParserException {
        public InvalidInfoExpression(String exr, int line) {
            super(String.format("invalid info expression '%s'", exr), line);
        }
    }

    public static class StatementRequired extends ParserException {
        public StatementRequired(int line) {
            super("at least one statement required", line);
        }
    }

    public static class ReservedIdentifier extends ParserException {
        public ReservedIdentifier(String iden, int line) {
            super(String.format("reserved identifier '%s'", iden), line);
        }
    }
}
