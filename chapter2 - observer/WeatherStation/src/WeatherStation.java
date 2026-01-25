public class WeatherStation {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        CurrentConditionDisplay currentConditionDisplay = new CurrentConditionDisplay();
        weatherData.registerObserver(currentConditionDisplay);

        weatherData.setMeasurements(60, 80, 40);
        weatherData.setMeasurements(10, 30, 10);
        weatherData.setMeasurements(50, 8, 20);
    }
}
