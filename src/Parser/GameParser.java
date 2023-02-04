package Parser;

import AST.*;
import Tokenizer.*;

public class GameParser implements Parser {
    private final Tokenizer tkz;
    private final ExpressionParser expressionParser;

    public GameParser(Tokenizer tkz) {
        this.tkz = tkz;
        this.expressionParser = new ExpressionParser(tkz);
    }

    @Override
    public Node parse() throws ParserException {
        return parsePlan();
    }

    private Node parsePlan() throws ParserException {
        Node node = parseStatement();
        node.next = parseStatements();
        return node;
    }

    private Node parseStatement() throws ParserException {
        if (tkz.peek("{"))
            return parseBlockStatement();
        else if (tkz.peek("if"))
            return parseIfStatement();
        else if (tkz.peek("while"))
            return parseWhileStatement();
        else
            return parseCommand();
    }

    private Node parseCommand() {
        String x = tkz.consume();
        Parameter parameter = ParameterMap.map.get(x);
        if (tkz.peek("=")) {
            if (parameter != null)
                throw new ParserException.ReserveIdentifier();
            tkz.consume("=");
            return new Command.AssignCommand(x, Long.parseLong(tkz.consume()));
        } else {
            if (parameter == null)
                throw new ParserException.NoCommand();
            else if (parameter instanceof Action)
                return switch ((Action) parameter) {
                    case DONE, RELOCATE -> new Command.ActionCommand(parameter);
                    case MOVE -> new Command.MoveCommand(parseDirection());
                    case INVEST, COLLECT -> new Command.RegionCommand(parameter, expressionParser.parse());
                    case SHOOT -> new Command.AttackCommand(parseDirection(), expressionParser.parse());

                };
            else
                throw new ParserException.NotImplement();
        }
    }

    private Direction parseDirection() {
        String x = tkz.consume();
        Parameter parameter = ParameterMap.map.get(x);
        if (parameter instanceof Direction)
            return (Direction) parameter;
        else
            throw new ParserException.NotImplement();
    }

    private Node parseWhileStatement() {
        ConditionNode node = new ConditionNode();
        tkz.consume("while");
        tkz.consume("(");
        node.expression = expressionParser.parse();
        tkz.consume(")");
        node.conditionTrue = parseStatement();
        node.conditionTrue.next = node;
        return node;
    }

    private Node parseIfStatement() {
        ConditionNode node = new ConditionNode();
        tkz.consume("if");
        tkz.consume("(");
        node.expression = expressionParser.parse();
        tkz.consume(")");
        tkz.consume("then");
        node.conditionTrue = parseStatement();
        tkz.consume("else");
        node.conditionFalse = parseStatement();
        return node;
    }

    private Node parseStatements() {
        Node node = null;
        while (tkz.hasNext()) {
            Node current = parseStatement();
            if (node != null)
                node.next = current;
            node = current;
        }
        return node;
    }

    private Node parseBlockStatement() {
        Node node;
        tkz.consume("{");
        node = parseStatements();
        tkz.consume("}");
        return node;
    }
}
