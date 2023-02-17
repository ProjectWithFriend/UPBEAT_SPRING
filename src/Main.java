import AST.Node.*;
import Game.GameProps;
import Parser.*;
import Game.*;
import Tokenizer.IterateTokenizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
//        String script = Files.readString(Path.of("script.txt"));
//        Parser parser = new GrammarParser(new IterateTokenizer(script));
//        ExecNode tree = parser.parse();
//        Game game = new GameProps();
//        while (tree != null) {
//            tree = tree.execute(game);
//        }
//        return;


        //TEST READ CONFIGURATION FILE
        GameInitialize.loadConfig();
    }
}
