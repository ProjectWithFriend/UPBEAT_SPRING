package AST;

import java.util.List;

public class BlockNode extends Node{
    List<Node> statements;

    @Override
    public void execute() {
        System.out.println("BlockNode execute");
    }
}
