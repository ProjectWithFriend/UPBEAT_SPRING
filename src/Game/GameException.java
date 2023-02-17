package Game;

import AST.ASTException;

public class GameException extends RuntimeException {
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
}
