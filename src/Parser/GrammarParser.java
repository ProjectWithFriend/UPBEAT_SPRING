package Parser;

import AST.*;
import Tokenizer.Tokenizer;

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

    private Tokenizer tkz;
//    List<Node> allNodes;

    public GrammarParser(Tokenizer tkz) {
        this.tkz = tkz;
//        allNodes = new ArrayList<>();
    }

//    @Override
//    public void runTree() {
//        allNodes = reverseList(allNodes);
//        for (Node node : allNodes) {
//            node.execute();
//        }
//    }

//    private List<Node> reverseList(List<Node> list) {
//        List<Node> reversedList = new ArrayList<>();
//        for (int i = list.size() - 1; i >= 0; i--) {
//            reversedList.add(list.get(i));
//        }
//        return reversedList;
//    }

    @Override
    public Node.ExecNode parse() {
        Node.ExecNode actions = parsePlan();
        if (tkz.hasNext())
            throw new ASTException.LeftoverTokenException(tkz.peek()); // TODO: make exception
        return actions;
    }

    private Node.ExecNode parsePlan() {
        Node.ExecNode actions = parseStatement();
        actions.next = parseStatements();
        return actions;
    }

    private Node.ExecNode parseStatements() {
        Node.ExecNode node = null;
        while (!tkz.peek("}") && tkz.hasNext()) {
            Node.ExecNode current = parseStatement();
            if (node != null)
                node.next = current;
            if (current == null)
                break;
            if (node == null)
                node = current;
        }
        return node;
    }

    private Node.ExecNode parseStatement() {
        if (tkz.peek("if")) {
            return parseIfStatement();
        } else if (tkz.peek("while")) {
            return parseWhileStatement();
        } else if (tkz.peek("{")) {
            return parseBlockStatement();
        } else {
            return parseCommand();
        }
    }

    private Node.ExecNode parseBlockStatement() {
        Node.ExecNode node;
        tkz.consume("{");
        node = parseStatements();
        tkz.consume("}");
        return node;
    }

    private Node.ExecNode parseWhileStatement() {
        tkz.consume("while");
        tkz.consume("(");
        Node.ExecNode.ExprNode expression = parseExpression();
        tkz.consume(")");
        Node.ExecNode statements = parseStatement();
        return new WhileNode(expression, statements);
    }

    private Node.ExecNode parseIfStatement() {
        tkz.consume("if");
        tkz.consume("(");
        Node.ExecNode.ExprNode expression = parseExpression();
        tkz.consume(")");
        tkz.consume("then");
        Node.ExecNode trueStatement = parseStatement();
        tkz.consume("else");
        Node.ExecNode falseStatement = parseStatement();
        return new IfElseNode(expression, trueStatement, falseStatement);
    }

    private Node.ExecNode parseCommand() {
        if (tkz.peek("done") || tkz.peek("relocate") ||
                tkz.peek("move") || tkz.peek("invest") ||
                tkz.peek("collect") || tkz.peek("shoot")) {
            return parseActionCommand();
        }
        return parseAssignmentStatement();
    }

    private Node.ExecNode parseAssignmentStatement() {
        String identifier = tkz.consume();
        tkz.consume("=");
        Node.ExecNode.ExprNode expression = parseExpression();
        return new AssignmentNode(identifier, expression);
    }

    private Node.ExecNode parseActionCommand() {
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

    private Node.ExecNode parseShootCommand() {
        tkz.consume();
        String direction = parseDirection();
        Node.ExecNode.ExprNode expression = parseExpression();
        return new AttackNode(expression, direction);
    }

    private Node.ExecNode parseCollectCommand() {
        String action = tkz.consume();
        Node.ExecNode.ExprNode expression = parseExpression();
        return new RegionNode(expression, action);
    }

    private Node.ExecNode parseInvestCommand() {
        String action = tkz.consume();
        Node.ExecNode.ExprNode expression = parseExpression();
        return new RegionNode(expression, action);
    }

    private Node.ExecNode.ExprNode parseExpression() {
        Node.ExecNode.ExprNode left = parseTerm();
        while (tkz.peek("+") || tkz.peek("-")) {
            String operator = tkz.consume();
            Node.ExecNode.ExprNode right = parseTerm();
            left = new BinaryOperationNode(left, operator, right);
        }
        return left;
    }

    private Node.ExecNode.ExprNode parseTerm() {
        Node.ExecNode.ExprNode left = parseFactor();
        while (tkz.peek("*") || tkz.peek("/") || tkz.peek("%")) {
            String operator = tkz.consume();
            Node.ExecNode.ExprNode right = parseFactor();
            left = new BinaryOperationNode(left, operator, right);
        }
        return left;
    }

    private Node.ExecNode.ExprNode parseFactor() {
        Node.ExecNode.ExprNode left = parsePower();
        while (tkz.peek("^")) {
            String operator = tkz.consume();
            Node.ExecNode.ExprNode right = parseFactor();
            left = new BinaryOperationNode(left, operator, right);
        }
        return left;
    }

    private Node.ExecNode.ExprNode parsePower() {
        if (Character.isDigit(tkz.peek().charAt(0))) {
            return new AtomicNode(Integer.parseInt(tkz.consume()));
        } else if (tkz.peek("opponent") || tkz.peek("nearby")) {
            return parseInfoExpression();
        } else if (tkz.peek("(")) {
            tkz.consume("(");
            Node.ExecNode.ExprNode expr = parseExpression();
            tkz.consume(")");
            return expr;
        }
        return new AtomicNode(tkz.consume());
    }

    private Node.ExecNode.ExprNode parseInfoExpression() {
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

    private Node.ExecNode parseMoveCommand() {
        tkz.consume();
        String direction = parseDirection();
        return new MoveNode(direction);
    }

    private String parseDirection() {
        String direction;
        if (tkz.peek("up")) {
            direction="up";
        } else if (tkz.peek("down")) {
            direction="down";
        } else if (tkz.peek("upleft")) {
            direction="upleft";
        } else if (tkz.peek("upright")) {
            direction="upright";
        } else if (tkz.peek("downleft")) {
            direction="downleft";
        } else if (tkz.peek("downright")) {
            direction="downright";
        } else {
            throw new RuntimeException("Unexpected token: " + tkz.peek());
        }
        tkz.consume();
        return direction;
    }
}
