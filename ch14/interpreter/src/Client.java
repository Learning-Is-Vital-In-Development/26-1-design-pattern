import java.util.HashMap;
import java.util.Map;

public class Client {

    public static void main(String[] args) {
        Map<String, Integer> context = new HashMap<>();

        // 변수 설정: 꽥 3번, 날기 2번
        context.put("quackCount", 3);
        context.put("flyCount", 2);

        Variable quackCount = new Variable("quackCount");
        Variable flyCount = new Variable("flyCount");

        // 프로그램 구성: 꽥 3번 → 우회전 → 날기 2번
        Expression program = new Sequence(
                new Repetition(quackCount, new QuackCommand()),
                new Sequence(
                        new RightCommand(),
                        new Repetition(flyCount, new FlyCommand())
                )
        );

        System.out.println("=== 오리 프로그램 실행 ===");
        program.interpret(context);

        System.out.println();
        System.out.println("=== 변수 변경 후 재실행 ===");
        context.put("quackCount", 1);
        context.put("flyCount", 4);
        program.interpret(context);
    }
}
