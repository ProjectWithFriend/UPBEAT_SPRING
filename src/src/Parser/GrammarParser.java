package Parser;

import AST.*;
import Tokenizer.*;

import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Integer> memory = new HashMap<>();

    public GrammarParser(Tokenizer tkz) {
        this.tkz = tkz;
    }

    public Actions parse() {
        Actions actions = parsePlan();
        if (tkz.hasNext())
            throw new RuntimeException("Unexpected token: " + tkz.peek());
        return actions;
    }

    private Actions parsePlan() {
        Actions actions = parseStatement();
        while (tkz.hasNext()) {
            actions.addChild(parseStatement());
        }
        return actions;
    }

    private Actions parseStatement() {
        if (tkz.peek("if"))
            return parseIfStatement();
//        if (tkz.peek("while"))
//            return parseWhileStatement();
//        if (tkz.peek("{"))
//            return parseBlockStatement();
        return parseCommand();
    }

    private Actions parseIfStatement() {
        tkz.consume("if");
        tkz.consume("(");
        Expr condition = parseExpression();
        if (condition.eval(memory) >= 1) {
            tkz.consume(")");
            tkz.consume("then");
            return parseStatement();
        } else {
            tkz.consume(")");
            tkz.consume("else");
            return parseStatement();
        }
    }

    private Expr parseExpression() {
        Expr expr = parseTerm();
        while (tkz.peek("+") || tkz.peek("-")) {
            String op = tkz.consume();
            Expr right = parseTerm();
            expr = new Compute(expr, right, op);
        }
        return expr;
    }

    private Expr parseTerm() {
        Expr term = parseFactor();
        while (tkz.peek("*") || tkz.peek("/") || tkz.peek("%")) {
            String op = tkz.consume();
            Expr right = parseFactor();
            term = new Compute(term, right, op);
        }
        return term;
    }

    private Expr parseFactor() {
        Expr factor = parsePower();
        while (tkz.peek("^")) {
            tkz.consume();
            Expr right = parseFactor();
            factor = new Compute(factor, right, "^");
        }
        return factor;
    }

    private Expr parsePower() {
        if (Character.isDigit(tkz.peek().charAt(0))) {
            return new Numbers(Integer.parseInt(tkz.consume()));
        } else if (Character.isLetter(tkz.peek().charAt(0))) {
            return new Variable(tkz.consume());
        } else if (tkz.peek("(")) {
            tkz.consume("(");
            Expr expr = parseExpression();
            tkz.consume(")");
            return expr;
        } else if (tkz.peek("opponent") || tkz.peek("nearby")) {
            return parseInfoExpression();
        }
        return null;
    }

    private Expr parseInfoExpression() {
        if (tkz.peek("opponent")) {
            String action = tkz.consume();
            return new InfoExp(action);
        } else if (tkz.peek("nearby")) {
            String action = tkz.consume();
            String direction = tkz.consume();
            return new InfoExp(action, direction);
        }
        return null;
    }

    private Actions parseCommand() {
        if (tkz.peek("done") || tkz.peek("relocate") ||
                tkz.peek("move") || tkz.peek("invest") ||
                tkz.peek("collect") || tkz.peek("shoot")) {
            return parseActionCommand();
        }
//        return parseAssignmentStatement();
        return null;
    }

    private Actions parseActionCommand() {
        if (tkz.peek("done") || tkz.peek("relocate")) {
            String action = tkz.consume();
            System.out.println(action);
            return new Logic(action);
        } else if (tkz.peek("move")) {
            String action = tkz.consume();
            String direction = tkz.consume();
            System.out.println(action + " " + direction);
            return new Logic(action + " " + direction);
        } else if (tkz.peek("invest") || tkz.peek("collect")) {
            String action = tkz.consume();
            Expr expr = parseExpression();
            return new Logic(action + " " + expr.eval(memory));
        }
        return null;
    }
}
