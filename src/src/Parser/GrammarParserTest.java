package Parser;

import AST.Actions;
import Tokenizer.IterateTokenizer;
import Tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrammarParserTest {
    @Test
    public void TestIfState() {
        String[] testkeyword = {"done"};
        for (String s : testkeyword) {
            Tokenizer tkz = new IterateTokenizer(s);
            GrammarParser parser = new GrammarParser(tkz);
            Actions actions = parser.parse();
//            assertEquals(s, actions.toString());
        }
    }
}