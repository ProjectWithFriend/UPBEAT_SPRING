package AST;

import java.util.Map;

public abstract class ExprNode extends Node{
    public long eval(Map<String, Long> memory){
        return 0;
    }
}
