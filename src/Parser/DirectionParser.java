package Parser;


import AST.Direction;
import AST.GameCommand;
import AST.ParameterMap;
import Tokenizer.Tokenizer;

public class DirectionParser {
    private Tokenizer tkz;

    public DirectionParser(Tokenizer tkz) {
        this.tkz = tkz;
    }

    public Direction parse() {
        String x = tkz.consume();
        GameCommand gameCommand = ParameterMap.map.get(x);
        if (gameCommand instanceof Direction)
            return (Direction) gameCommand;
        else
            throw new ParserException.NotImplement();
    }
}
