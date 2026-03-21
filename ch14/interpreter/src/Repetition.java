import java.util.Map;

public class Repetition implements Expression {

    private final Variable variable;
    private final Expression expression;

    public Repetition(Variable variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void interpret(Map<String, Integer> context) {
        int count = variable.getValue(context);
        for (int i = 0; i < count; i++) {
            expression.interpret(context);
        }
    }
}
