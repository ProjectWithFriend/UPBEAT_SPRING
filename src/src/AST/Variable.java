package AST;

import java.util.Map;

public class Variable implements Expr{
    private String name;
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int eval(Map<String, Integer> memory) {
        if(memory.containsKey(name)) {
            return memory.get(name);
        }else{
            throw new IllegalArgumentException("Variable " + name + " not found");
        }
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append(name);
    }
}
