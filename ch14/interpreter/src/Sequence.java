import java.util.Map;

public class Sequence implements Expression {

    private final Expression expression1;
    private final Expression expression2;

    public Sequence(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public void interpret(Map<String, Integer> context) {
        expression1.interpret(context);
        expression2.interpret(context);
    }
}
