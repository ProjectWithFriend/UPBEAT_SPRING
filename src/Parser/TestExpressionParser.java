package Parser;

import Tokenizer.*;
import org.junit.Test;
import org.junit.jupiter.api.Nested;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestExpressionParser {
    @Test
    public void numberTest() {
        Tokenizer tkz = new IterateTokenizer("0999");
        ExpressionParser psr = new ExpressionParser(tkz);
        assertEquals(999, psr.parse().eval(null));
    }

    @Test
    public void additionTest() {
        Tokenizer tkz = new IterateTokenizer("johnny + 333+333");
        ExpressionParser psr = new ExpressionParser(tkz);
        assertEquals(120 + 333 + 333, psr.parse().eval(Map.of("johnny", (long) 120)));
    }

    @Test
    public void additionTest2() {
        Tokenizer tkz = new IterateTokenizer("333+333");
        ExpressionParser psr = new ExpressionParser(tkz);
        assertEquals(666, psr.parse().eval(new HashMap<>()));
    }

    @Test
    public void subtractionTest() {
        Tokenizer tkz = new IterateTokenizer("y-333-333");
        ExpressionParser psr = new ExpressionParser(tkz);
        assertEquals(900 - 333 - 333, psr.parse().eval(Map.of("y", (long) 900)));
    }

    @Test
    public void multiplicationTest() {
        Tokenizer tkz = new IterateTokenizer("x * y*z");
        ExpressionParser psr = new ExpressionParser(tkz);
        assertEquals(100 * 200 * 333, psr.parse().eval(Map.of("x", (long) 100, "y", (long) 200, "z", (long) 333)));
    }

    @Test
    public void divisionTest() {
        Tokenizer tkz = new IterateTokenizer("333 /pmodify/ 333");
        ExpressionParser psr = new ExpressionParser(tkz);
        assertEquals(333 / 2 / 333, psr.parse().eval(Map.of("pmodify", (long) 2)));
    }

    @Test
    public void moduloTest() {
        Tokenizer tkz = new IterateTokenizer("xxx%333 %333");
        ExpressionParser psr = new ExpressionParser(tkz);
        assertEquals(1 % 333 % 333, psr.parse().eval(Map.of("xxx", (long) 1)));
    }

    @Test
    public void PEMDASTest() {
        Tokenizer tkz = new IterateTokenizer(" (a+b+c)^2 * x*y/z - w");
        ExpressionParser psr = new ExpressionParser(tkz);
        assertEquals(4 * 3 * 4 / 5 - 6, psr.parse().eval(Map.of("a", (long) 0, "b", (long) 1, "c", (long) 1, "x", (long) 3, "y", (long) 4, "z", (long) 5, "w", (long) 6)));
    }

//    @Test
//    public void parenthesesFailTest() {
//        Tokenizer tkz = new IterateTokenizer("((333)-33");
//        ExpressionParser psr = new ExpressionParser(tkz);
//        assertThrows(Exception.SyntaxError.class, psr::parseAST);
//
//        tkz = new IterateTokenizer("(333))-33");
//        psr = new ExpressionParser(tkz);
//        assertThrows(RuntimeException.class, psr::parseAST);
//    }

    @Test
    public void associativityTest() {
        Tokenizer tkz = new IterateTokenizer("(333 + 222) + 111");
        ExpressionParser psr = new ExpressionParser(tkz);

        Tokenizer tkz2 = new IterateTokenizer("333 + (222 + 111)");
        ExpressionParser psr2 = new ExpressionParser(tkz2);

        assertEquals(666, psr.parse().eval(null));
        assertEquals(666, psr2.parse().eval(null));
    }

}
