public class CurrentConditionDisplay implements Observer, DisplayElement {
    private float temperature;
    private float humidity;
    private float pressure;

    public CurrentConditionDisplay() {
    }

    @Override
    public void display() {
        System.out.printf("--Current Condition--\n");
        System.out.printf("Current temperature: %f\n",temperature);
        System.out.printf("Current humidity: %f\n", humidity);
        System.out.printf("Current pressure: %f\n", pressure);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }
}
