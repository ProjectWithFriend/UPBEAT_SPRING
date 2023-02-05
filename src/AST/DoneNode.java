package AST;

public class DoneNode extends Node{
    @Override
    public void execute() {
        System.out.println("Done");
    }
}
