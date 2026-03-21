import java.util.Map;

public class QuackCommand implements Expression {

    @Override
    public void interpret(Map<String, Integer> context) {
        System.out.println("꽥꽥!");
    }
}
