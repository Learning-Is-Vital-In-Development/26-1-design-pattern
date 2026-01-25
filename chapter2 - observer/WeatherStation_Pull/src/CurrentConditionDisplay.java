public class CurrentConditionDisplay implements Observer, DisplayElement {
    private WeatherData weatherData;
    private float temperature;
    private float humidity;
    private float pressure;

    public CurrentConditionDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.printf("--Current Condition--\n");
        System.out.printf("Current temperature: %f\n",temperature);
        System.out.printf("Current humidity: %f\n", humidity);
        System.out.printf("Current pressure: %f\n", pressure);
    }

    @Override
    public void update() {
        this.temperature = weatherData.getTemperature();
        this.humidity = weatherData.getHumidity();
        this.pressure = weatherData.getPressure();
        display();
    }
}
