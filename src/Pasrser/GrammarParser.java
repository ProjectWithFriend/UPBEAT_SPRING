package Pasrser;

import AST.*;
import Tokenizer.Tokenizer;

import java.util.*;

public class GrammarParser implements Parser {
    //    Plan → Statement+
//    Statement → Command | BlockStatement | IfStatement | WhileStatement
//    Command → AssignmentStatement | ActionCommand
//    AssignmentStatement → <identifier> = Expression
//    ActionCommand → done | relocate | MoveCommand | RegionCommand | AttackCommand
//    MoveCommand → move Direction
//    RegionCommand → invest Expression | collect Expression
//    AttackCommand → shoot Direction Expression
//    Direction → up | down | upleft | upright | downleft | downright
//    BlockStatement → { Statement* }
//    IfStatement → if ( Expression ) then Statement else Statement
//    WhileStatement → while ( Expression ) Statement
//    Expression → Expression + Term | Expression - Term | Term
//    Term → Term * Factor | Term / Factor | Term % Factor | Factor
//    Factor → Power ^ Factor | Power
//    Power → <number> | <identifier> | ( Expression ) | InfoExpression
//    InfoExpression → opponent | nearby Direction

    Tokenizer tkz;
    List<Node> allNodes;
    Map<String, Long> memory;

    public GrammarParser(Tokenizer tkz) {
        this.tkz = tkz;
        allNodes = new ArrayList<>();
        memory = new HashMap<>();
    }

    public void runTree() {
        allNodes = reverseList(allNodes);
        for (Node node : allNodes) {
            node.execute();
        }
    }

    private List<Node> reverseList(List<Node> list) {
        List<Node> reversedList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            reversedList.add(list.get(i));
        }
        return reversedList;
    }

    @Override
    public Node parse() {
        Node actions = parsePlan();
        if (tkz.hasNext())
            throw new RuntimeException("Unexpected token: " + tkz.peek());
        return actions;
    }

    private Node parsePlan() {
        Node actions = parseStatement();
        return actions;
    }

    private Node parseStatement() {
        if (tkz.peek("if")) {
            Node ifStatement = parseIfStatement();
            allNodes.add(ifStatement);
            return ifStatement;
        } else if (tkz.peek("while")) {
//            Node whileStatement = parseWhileStatement();
//            allNodes.add(whileStatement);
//            return whileStatement;
        } else if (tkz.peek("{")) {
//            Node blockStatement = parseBlockStatement();
//            allNodes.add(blockStatement);
//            return blockStatement;
        } else {
            Node command = parseCommand();
            allNodes.add(command);
            return command;
        }
        return null;
    }

    private Node parseIfStatement() {
        tkz.consume("if");
        tkz.consume("(");
        ExprNode expression = parseExpression();
        tkz.consume(")");
        tkz.consume("then");
        Node trueStatement = parseStatement();
        tkz.consume("else");
        Node falseStatement = parseStatement();
        return new ConditionNode(expression, trueStatement, falseStatement);
    }

    private Node parseCommand() {
        if (tkz.peek("done") || tkz.peek("relocate") ||
                tkz.peek("move") || tkz.peek("invest") ||
                tkz.peek("collect") || tkz.peek("shoot")) {
            return parseActionCommand();
        }
        return parseAssignmentStatement();
    }

    private Node parseAssignmentStatement() {
        String identifier = tkz.consume();
        tkz.consume("=");
        ExprNode expression = parseExpression();
        return new AssignmentNode(identifier, expression);
    }

    private Node parseActionCommand() {
        if (tkz.peek("done")) {
            tkz.consume();
            return new DoneNode();
        } else if (tkz.peek("relocate")) {
            tkz.consume();
            return new RelocateNode();
        } else if (tkz.peek("move")) {
            return parseMoveCommand();
        } else if (tkz.peek("invest")) {
            return parseInvestCommand();
        } else if (tkz.peek("collect")) {
            return parseCollectCommand();
        } else if (tkz.peek("shoot")) {
            return parseShootCommand();
        } else {
            throw new RuntimeException("Unexpected token: " + tkz.peek());
        }
    }

    private Node parseShootCommand() {
        tkz.consume();
        String direction = parseDirection();
        ExprNode expression = parseExpression();
        return new AttackNode(expression.eval(memory), direction);
    }

    private Node parseCollectCommand() {
        String action = tkz.consume();
        ExprNode expression = parseExpression();
        return new RegionNode(expression.eval(memory), action);
    }

    private Node parseInvestCommand() {
        String action = tkz.consume();
        ExprNode expression = parseExpression();
        return new RegionNode(expression.eval(memory), action);
    }

    private ExprNode parseExpression() {
        ExprNode left = parseTerm();
        while (tkz.peek("+") || tkz.peek("-")) {
            String operator = tkz.consume();
            ExprNode right = parseTerm();
            left = new BinaryOperationNode(left, operator, right);
        }
        return left;
    }

    private ExprNode parseTerm() {
        ExprNode left = parseFactor();
        while (tkz.peek("*") || tkz.peek("/") || tkz.peek("%")) {
            String operator = tkz.consume();
            ExprNode right = parseFactor();
            left = new BinaryOperationNode(left, operator, right);
        }
        return left;
    }

    private ExprNode parseFactor() {
        ExprNode left = parsePower();
        while (tkz.peek("^")) {
            String operator = tkz.consume();
            ExprNode right = parseFactor();
            left = new BinaryOperationNode(left, operator, right);
        }
        return left;
    }

    private ExprNode parsePower() {
        if (Character.isDigit(tkz.peek().charAt(0))) {
            return new NumberNode(Integer.parseInt(tkz.consume()));
        } else if (Character.isLetter(tkz.peek().charAt(0))) {
            return new Identifier(tkz.consume());
        } else if (tkz.peek("(")) {
            tkz.consume("(");
            ExprNode expr = parseExpression();
            tkz.consume(")");
            return expr;
        } else if (tkz.peek("opponent") || tkz.peek("nearby")) {
            return parseInfoExpression();
        }
        return null;
    }

    private ExprNode parseInfoExpression() {
        if (tkz.peek("opponent")) {
            tkz.consume();
            return new OpponentNode();
        } else if (tkz.peek("nearby")) {
            tkz.consume();
            String direction = parseDirection();
            return new NearbyNode(direction);
        } else {
            throw new RuntimeException("Unexpected token: " + tkz.peek());
        }
    }

    private Node parseMoveCommand() {
        tkz.consume();
        String direction = parseDirection();
        tkz.consume();
        return new MoveNode(direction);
    }

    private String parseDirection() {
        if (tkz.peek("up")) {
            return "up";
        } else if (tkz.peek("down")) {
            return "down";
        } else if (tkz.peek("upleft")) {
            return "upleft";
        } else if (tkz.peek("upright")) {
            return "upright";
        } else if (tkz.peek("downleft")) {
            return "downleft";
        } else if (tkz.peek("downright")) {
            return "downright";
        } else {
            throw new RuntimeException("Unexpected token: " + tkz.peek());
        }
    }
}
