import java.util.Map;

public class RightCommand implements Expression {

    @Override
    public void interpret(Map<String, Integer> context) {
        System.out.println("오리가 오른쪽으로 방향을 틀었습니다.");
    }
}
