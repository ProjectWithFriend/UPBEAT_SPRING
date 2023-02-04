package Parser;

import AST.ParameterMap;
import Tokenizer.Tokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static AST.Info.NEARBY;
import static AST.Info.OPPONENT;
import static Parser.ExpressionGrammar.*;

public class ExpressionParser implements Parser {
    private final Tokenizer tkz;
    private final DirectionParser directionParser;

    public ExpressionParser(Tokenizer tkz) {
        this.tkz = tkz;
        this.directionParser = new DirectionParser(tkz);
    }

    private ExpressionNode parseExpression() {
        ExpressionNode node = parseTerm();
        while (tkz.peek("+") || tkz.peek("-")) {
            node = new Expression(node, tkz.consume(), parseTerm());
        }
        return node;
    }

    private ExpressionNode parseTerm() {
        ExpressionNode node = parseFactor();
        while (tkz.peek("*") || tkz.peek("/") || tkz.peek("%")) {
            node = new Term(node, tkz.consume(), parseFactor());
        }
        return node;
    }

    private ExpressionNode parseFactor() {
        ExpressionNode node = parsePower();
        while (tkz.peek("^")) {
            node = new Factor(node, tkz.consume(), parseFactor());
        }
        return node;
    }

    private Power parsePower() {
        String value = tkz.peek();
        Matcher matcher = Pattern.compile("^\\d+|\\w+$").matcher(value);
        if (matcher.find()) {
            return new Power(tkz.consume());
        } else if (ParameterMap.map.containsKey(value)) {
            return new Power(parseInfo());
        } else {
            tkz.consume("(");
            Power power = new Power(parseExpression());
            tkz.consume(")");
            return power;
        }
    }

    private Info parseInfo() {
        if (tkz.peek("nearby")) {
            tkz.consume();
            return new Info(NEARBY, null);
        } else if (tkz.peek("opponent")) {
            tkz.consume();
            return new Info(OPPONENT, directionParser.parse());
        } else
            throw new ParserException.InvalidSyntax(tkz.consume());
    }

    public ExpressionNode parse() {
        return parseExpression();
    }
}
