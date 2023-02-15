import AST.Node.*;
import Parser.*;
import Player.*;
import Tokenizer.IterateTokenizer;

public class Main {
    public static void main(String[] args) {
        Parser parser = new GrammarParser(new IterateTokenizer("x = 5 * 3 y = 3 * 5 x = 1 + y invest 10 collect 100*100"));
        ExecNode tree = parser.parse();
        Player player = new PlayerProps("santa claus");
        while (tree != null) {
            tree = tree.execute(player);
        }
        return;
    }
}
