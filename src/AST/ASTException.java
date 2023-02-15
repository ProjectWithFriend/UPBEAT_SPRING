package AST;

public abstract class ASTException extends RuntimeException {
    protected ASTException() {
        super();
    }
    protected ASTException(String m) {
        super(m);
    }

    public static class LeftoverTokenException extends ASTException {
        public LeftoverTokenException(String token) {
            super("Unexpected token: " + token);
        }
    }

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

    public static class UnknownOperator extends ASTException {
        public UnknownOperator(String op) {
            super(String.format("%s is not an operator", op));
        }
    }

    public static class UndefinedIdentifier extends ASTException {
        public UndefinedIdentifier(String iden) {
            super(String.format("identifer '%s' is not defined", iden));
        }
    }
}
