package AST;

import java.util.HashMap;
import java.util.Map;

import static AST.Action.*;
import static AST.Direction.*;
import static AST.Info.*;

public class ParameterMap {
    public static Map<String, GameCommand> map = new HashMap<>() {
        {
            put("done", DONE);
            put("move", MOVE);
            put("invest", INVEST);
            put("relocate", RELOCATE);
            put("collect", COLLECT);
            put("shoot", SHOOT);
            put("up", UP);
            put("down", DOWN);
            put("upleft", UPLEFT);
            put("upright", UPRIGHT);
            put("downleft", DOWNLEFT);
            put("downright", DOWNRIGHT);
            put("nearby", NEARBY);
            put("opponent", OPPONENT);
        }
    };
}