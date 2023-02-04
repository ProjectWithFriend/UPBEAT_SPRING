package Parser;

import AST.Direction;
import AST.Info;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class ExpressionGrammar {
    private static abstract class BinaryArithmetic extends ExpressionNode {
        private final ExpressionNode lhs;
        private final ExpressionNode rhs;
        private final String op;

        public BinaryArithmetic(ExpressionNode lhs, String op, ExpressionNode rhs) {
            this.lhs = lhs;
            this.op = op;
            this.rhs = rhs;
        }
    }

    public static class Term extends BinaryArithmetic {
        public Term(ExpressionNode lhs, String op, ExpressionNode rhs) {
            super(lhs, op, rhs);
        }

        @Override
        public long eval(Map<String, Long> bindings) {
            switch (super.op) {
                case "*" -> {
                    return super.lhs.eval(bindings) * super.rhs.eval(bindings);
                }
                case "/" -> {
                    if (super.rhs.eval(bindings) == 0)
                        throw new ParserException.ArithmeticError("Can't be divided by zero");
                    return super.lhs.eval(bindings) / super.rhs.eval(bindings);
                }
                case "%" -> {
                    if (super.rhs.eval(bindings) == 0)
                        throw new ParserException.ArithmeticError("Can't be modulo by zero");
                    return super.lhs.eval(bindings) % super.rhs.eval(bindings);
                }
                default -> throw new ParserException.OperatorNotFound(super.op);
            }
        }
    }

    public static class Expression extends BinaryArithmetic {
        public Expression(ExpressionNode lhs, String op, ExpressionNode rhs) {
            super(lhs, op, rhs);
        }

        @Override
        public long eval(Map<String, Long> bindings) {
            return switch (super.op) {
                case "+" -> super.lhs.eval(bindings) + super.rhs.eval(bindings);
                case "-" -> super.lhs.eval(bindings) - super.rhs.eval(bindings);
                default -> throw new ParserException.OperatorNotFound(super.op);
            };
        }
    }

    public static class Factor extends BinaryArithmetic {
        public Factor(ExpressionNode lhs, String op, ExpressionNode rhs) {
            super(lhs, op, rhs);
        }

        @Override
        public long eval(Map<String, Long> bindings) {
            return switch (super.op) {
                case "^" -> (long) Math.pow(
                        super.lhs.eval(bindings),
                        super.rhs.eval(bindings)
                );
                default -> throw new ParserException.OperatorNotFound(super.op);
            };
        }
    }

    public static class Power extends ExpressionNode {
        private String n;
        private ExpressionNode node;

        public Power(ExpressionNode node) {
            this.node = node;
        }

        public Power(String n) {
            this.n = n;
        }

        private boolean isNumber(String string) {
            if (string == null) return false;
            return Pattern.compile("^\\d+$").matcher(string).find();
        }

        @Override
        public long eval(Map<String, Long> bindings) {
            if (node != null) {
                return node.eval(bindings);
            }
            if (isNumber(n)) return Integer.parseInt(n);
            else return bindings.get(n);
        }
    }

    public static class Info extends ExpressionNode {

        private final AST.Info info;
        private final Direction direction;

        public Info(AST.Info info, Direction direction) {
            this.info = info;
            if (info == AST.Info.OPPONENT && direction == null)
                throw new ParserException.InvalidParameter();
            this.direction = direction;
        }

        @Override
        public long eval(Map<String, Long> bindings) {
            return 0; // TODO: make it work
        }
    }
}
