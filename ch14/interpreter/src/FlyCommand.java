import java.util.Map;

public class FlyCommand implements Expression {

    @Override
    public void interpret(Map<String, Integer> context) {
        System.out.println("오리가 날아갑니다!");
    }
}
