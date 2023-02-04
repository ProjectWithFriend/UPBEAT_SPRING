package Parser;

import Tokenizer.*;
import org.junit.Test;

public class TestParser {
    @Test
    public void testSomething() {
        Tokenizer tkz = new IterateTokenizer("e=1 while (e) {e=e-1} y=opponent done");
        GameParser parser = new GameParser(tkz);
        Node node = parser.parse();
        return;
    }
}
