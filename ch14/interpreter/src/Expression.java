import java.util.Map;

public interface Expression {
    void interpret(Map<String, Integer> context);
}
