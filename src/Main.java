import Pasrser.GrammarParser;
import Pasrser.Parser;
import Tokenizer.IterateTokenizer;
import AST.Node;

public class Main {
    public static void main(String[] args) {
        Parser parser = new GrammarParser(new IterateTokenizer("if(1)then done else relocate"));
        Node tree = parser.parse();
        parser.runTree();
        //Test Something
    }
}
