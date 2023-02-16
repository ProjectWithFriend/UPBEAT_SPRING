package Tokenizer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IterateTokenizerTest {
    private IterateTokenizer tkz;

    @Test
    public void testHasNext() {
        tkz = new IterateTokenizer(null);
        assertFalse(tkz.hasNext());
        tkz = new IterateTokenizer("");
        assertFalse(tkz.hasNext());
        tkz = new IterateTokenizer("a");
        assertTrue(tkz.hasNext());
    }

    @Test
    public void testPeek() {
        tkz = new IterateTokenizer(null);
        assertThrows(TokenizerException.NoToken.class, tkz::peek);
        tkz = new IterateTokenizer("");
        assertThrows(TokenizerException.NoToken.class, tkz::peek);
        tkz = new IterateTokenizer("a");
        assertEquals("a", tkz.peek());
    }

    @Test
    public void testPeekString() {
        tkz = new IterateTokenizer(null);
        assertFalse(tkz.peek(""));
        assertFalse(tkz.peek("a"));
        tkz = new IterateTokenizer("");
        assertFalse(tkz.peek(""));
        assertFalse(tkz.peek("a"));
        tkz = new IterateTokenizer("a");
        assertTrue(tkz.peek("a"));
        tkz = new IterateTokenizer("abc");
        assertFalse(tkz.peek("a"));
        assertTrue(tkz.peek("abc"));
    }

    @Test
    public void testConsume() {
        tkz = new IterateTokenizer(null);
        assertThrows(TokenizerException.NoToken.class, tkz::consume);
        tkz = new IterateTokenizer("");
        assertThrows(TokenizerException.NoToken.class, tkz::consume);
        tkz = new IterateTokenizer("a");
        assertEquals("a", tkz.consume());
    }

    @Test
    public void testConsumeString() {
        tkz = new IterateTokenizer(null);
        assertThrows(TokenizerException.NoToken.class, () -> tkz.consume(""));
        assertThrows(TokenizerException.NoToken.class, () -> tkz.consume("a"));
        tkz = new IterateTokenizer("");
        assertThrows(TokenizerException.NoToken.class, () -> tkz.consume(""));
        assertThrows(TokenizerException.NoToken.class, () -> tkz.consume("a"));
        tkz = new IterateTokenizer("a");
        assertTrue(tkz.consume("a"));
        tkz = new IterateTokenizer("a");
        assertFalse(tkz.consume("abc"));
        assertTrue(tkz.consume("a"));
        tkz = new IterateTokenizer("abc");
        assertFalse(tkz.consume("a"));
        assertTrue(tkz.consume("abc"));
    }

    @Test
    public void testComment() {
        tkz = new IterateTokenizer("# eiei \n casa\n###########");
        assertTrue(tkz.consume("casa"));
    }
}