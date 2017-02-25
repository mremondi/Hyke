package cbbhackscolby.hyke.models;

/**
 * Created by mremondi on 2/25/17.
 */

public class WeatherJSON {
    private Weather[] weather;
    private Main main;

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
