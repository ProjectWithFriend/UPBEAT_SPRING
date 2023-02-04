package Parser;

import Tokenizer.Tokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionParser {
    private final Tokenizer tkz;

    public ExpressionParser(Tokenizer tkz) {
        this.tkz = tkz;
    }

    private ExpressionNode parseExpression() {
        ExpressionNode node = parseTerm();
        while (tkz.peek("+") || tkz.peek("-")) {
            node = new ExpressionGrammar.Expression(node, tkz.consume(), parseTerm());
        }
        return node;
    }

    private ExpressionNode parseTerm() {
        ExpressionNode node = parseFactor();
        while (tkz.peek("*") || tkz.peek("/") || tkz.peek("%")) {
            node = new ExpressionGrammar.Term(node, tkz.consume(), parseFactor());
        }
        return node;
    }


    private ExpressionGrammar.Factor parseFactor() {
        String value = tkz.peek();
        Matcher matcher = Pattern.compile("^\\d+|\\w+$").matcher(value);
        if (matcher.find()) {
            return new ExpressionGrammar.Factor(tkz.consume());
        } else {
            tkz.consume("(");
            ExpressionGrammar.Factor factor = new ExpressionGrammar.Factor(parseExpression());
            tkz.consume(")");
            return factor;
        }
    }

    public Node parse() {
        return parseExpression();
    }
}
