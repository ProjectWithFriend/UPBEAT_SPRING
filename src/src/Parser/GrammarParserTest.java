package Parser;

import AST.Node;
import Tokenizer.IterateTokenizer;
import Tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;

class GrammarParserTest {
    @Test
    public void TestIfState() {
        String[] testkeyword = {"if(100 -50)then done else done"};
        for (String s : testkeyword) {
            Tokenizer tkz = new IterateTokenizer(s);
            GrammarParser parser = new GrammarParser(tkz);
            Node actions = parser.parse();
//            assertEquals(s, actions.toString());
        }
    }
}