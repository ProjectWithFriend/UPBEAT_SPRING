package Parser;

public abstract class ParserException extends RuntimeException {
    protected ParserException(String m) {
        super(m);
    }

    public static class CommandNotFound extends ParserException {
        public CommandNotFound(String cmd) {
            super(String.format("command '%s' not found", cmd));
        }
    }

    public static class CommandNotImplemented extends ParserException {
        public CommandNotImplemented(String cmd) {
            super(String.format("command '%s' not implemented", cmd));
        }
    }
    public static class InvalidDirection extends ParserException {
        public InvalidDirection(String direction) {
            super(String.format("invalid direction '%s'", direction));
        }
    }
    public static class InvalidInfoExpression extends ParserException {
        public InvalidInfoExpression(String exr) {
            super(String.format("invalid info expression '%s'", exr));
        }
    }
}
