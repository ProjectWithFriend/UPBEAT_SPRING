package Parser;

import AST.*;
import AST.Action;
import AST.GameCommand;
import Tokenizer.*;

public class GameParser implements Parser {
    private final Tokenizer tkz;
    private final DirectionParser directionParser;
    private final ExpressionParser expressionParser;

    public GameParser(Tokenizer tkz) {
        this.tkz = tkz;
        this.directionParser = new DirectionParser(tkz);
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
        else if (tkz.peek("}"))
            return null;
        else
            return parseCommand();
    }

    private Node parseCommand() {
        String x = tkz.consume();
        GameCommand gameCommand = ParameterMap.map.get(x);
        if (tkz.peek("=")) {
            if (gameCommand != null)
                throw new ParserException.ReserveIdentifier();
            tkz.consume("=");
            return new Command.AssignCommandNode(x, expressionParser.parse());
        } else {
            if (gameCommand == null)
                throw new ParserException.NoCommand();
            else if (gameCommand instanceof Action)
                return switch ((Action) gameCommand) {
                    case DONE, RELOCATE -> new Command.ActionCommandNode(gameCommand);
                    case MOVE -> new Command.MoveCommandNode(directionParser.parse());
                    case INVEST, COLLECT -> new Command.RegionCommandNode(gameCommand, expressionParser.parse());
                    case SHOOT -> new Command.AttackCommandNode(directionParser.parse(), expressionParser.parse());
                };
            else
                throw new ParserException.NoCommand();
        }
    }


    private Node parseWhileStatement() {
        ConditionNode node = new ConditionNode();
        tkz.consume("while");
        tkz.consume("(");
        node.expression = expressionParser.parse();
        tkz.consume(")");
        node.conditionTrue = parseStatement();
        node.conditionFalse = node;
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
            if (current == null)
                break;
            if (node == null)
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
