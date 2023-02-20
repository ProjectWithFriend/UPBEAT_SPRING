package Game;

import AST.ASTException;

public abstract class GameException extends RuntimeException {
    public static class NotImplemented extends ASTException {
        public NotImplemented() {
            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            StackTraceElement e = stacktrace[2];
            String className = e.getClassName();
            String methodName = e.getMethodName();
            throw new NotImplemented(String.format("%s.%s not implemented", className, methodName));
        }

        private NotImplemented(String m) {
            super(m);
        }
    }

    public static class InvalidConfiguration extends ASTException {
        public InvalidConfiguration(String m) {
            super(m);
        }

        public InvalidConfiguration() {
            super();
        }
    }

    public static class InvalidValue extends ASTException {
        public InvalidValue(long m) {
            super(String.format("invalid value '%d'", m));
        }

    }
}
