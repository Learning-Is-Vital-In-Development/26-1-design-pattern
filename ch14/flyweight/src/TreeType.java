public class TreeType {

    private final String name;
    private final String color;
    private final String texture;

    public TreeType(String name, String color, String texture) {
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    public void display(int x, int y, int age) {
        System.out.println("[" + name + "] color=" + color + ", texture=" + texture
                + " | x=" + x + ", y=" + y + ", age=" + age);
    }
}
