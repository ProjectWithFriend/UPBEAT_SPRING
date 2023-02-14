import AST.Node;
import Parser.GrammarParser;
import Parser.Parser;
import Tokenizer.IterateTokenizer;

public class Main {
    public static void main(String[] args) {
        Parser parser = new GrammarParser(new IterateTokenizer("x = 5 * 3 y = 3 * 5 x = 1 + y"));
        Node.ExecNode tree = parser.parse();
        while (tree != null) {
            tree = tree.execute(null);;
        }
        return;
    }
}
