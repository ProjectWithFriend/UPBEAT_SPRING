package Parser;

import AST.AssignmentNode;

import static AST.Node.ExecNode;

import Tokenizer.IterateTokenizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static Parser.ParserException.*;

public class GrammarParserTest {
    public GrammarParser parser;
    public ExecNode node;

    @Test
    public void testEmptyStatement() {
        assertThrows(StatementRequired.class, () -> new GrammarParser(new IterateTokenizer("")));
    }

    @Test
    public void testMultipleStatement() {
        parser = new GrammarParser(new IterateTokenizer("x=1 x=1 x=1 x=1"));
        node = parser.parse();
        assertInstanceOf(AssignmentNode.class, node);
        node = node.next;
        assertInstanceOf(AssignmentNode.class, node);
        node = node.next;
        assertInstanceOf(AssignmentNode.class, node);
        node = node.next;
        assertInstanceOf(AssignmentNode.class, node);
        node = node.next;
        assertNull(node);
    }

    @Test
    public void testExpression() {
        parser = new GrammarParser(new IterateTokenizer("1+1"));
        assertThrows(CommandNotFound.class, parser::parse);
    }

    @Test
    public void testUnknown() {
        parser = new GrammarParser(new IterateTokenizer("wind"));
        assertThrows(CommandNotFound.class, parser::parse);
    }
}
