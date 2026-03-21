public class SmartHomeMediator implements Mediator {

    private final Alarm alarm;
    private final Calendar calendar;
    private final CoffeePot coffeePot;
    private final Sprinkler sprinkler;

    public SmartHomeMediator(Alarm alarm, Calendar calendar, CoffeePot coffeePot, Sprinkler sprinkler) {
        this.alarm = alarm;
        this.calendar = calendar;
        this.coffeePot = coffeePot;
        this.sprinkler = sprinkler;

        alarm.setMediator(this);
        calendar.setMediator(this);
        coffeePot.setMediator(this);
        sprinkler.setMediator(this);
    }

    @Override
    public void notify(Colleague sender, String event) {
        if (event.equals("alarm")) {
            calendar.checkDay();

            if (calendar.isWeekend()) {
                coffeePot.skip();
                sprinkler.turnOff();
            } else {
                coffeePot.brewCoffee();
                sprinkler.turnOn();
            }
        }
    }
}
