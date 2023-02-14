package AST;

import java.util.Map;

public class Identifier extends ExprNode{
    private String name;
    public Identifier(String name){
        this.name = name;
    }

    public long eval(Map<String, Long> memory){
        if(memory.containsKey(name)){
            return memory.get(name);
        }
        else{
            throw new RuntimeException("Identifier " + name + " not found");
        }
    }

    @Override
    public void execute() {
    }
}
