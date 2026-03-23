public class Tree {

    private final int x;
    private final int y;
    private final int age;
    private final TreeType type;

    public Tree(int x, int y, int age, TreeType type) {
        this.x = x;
        this.y = y;
        this.age = age;
        this.type = type;
    }

    public void display() {
        type.display(x, y, age);
    }
}
