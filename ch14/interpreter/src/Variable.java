import java.util.Map;

public class Variable {

    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(Map<String, Integer> context) {
        return context.getOrDefault(name, 0);
    }
}
