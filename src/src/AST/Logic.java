package AST;

public class Logic implements Actions{
    private String name;
    private Node child;

    public Logic(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
    @Override
    public void prettyPrint(StringBuilder s) {
        s.append(name);
    }

    @Override
    public void addChild(Node child) {
        this.child = child;
    }
}
