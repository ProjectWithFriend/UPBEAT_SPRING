package AST;

public class MoveNode extends Node{
    private String direction;

    public MoveNode(String direction){
        this.direction = direction;
    }
    public void execute(){
        System.out.println("Move " + direction);
    }
}
