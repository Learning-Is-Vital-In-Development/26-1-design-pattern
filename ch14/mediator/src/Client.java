public class Client {

    public static void main(String[] args) {
        System.out.println("=== 평일 아침 시나리오 ===");
        runScenario("월요일");

        System.out.println();

        System.out.println("=== 주말 아침 시나리오 ===");
        runScenario("토요일");
    }

    private static void runScenario(String day) {
        Alarm alarm = new Alarm();
        Calendar calendar = new Calendar(day);
        CoffeePot coffeePot = new CoffeePot();
        Sprinkler sprinkler = new Sprinkler();

        new SmartHomeMediator(alarm, calendar, coffeePot, sprinkler);

        alarm.ring();
    }
}
