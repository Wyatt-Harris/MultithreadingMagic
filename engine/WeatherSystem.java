package engine;

import utils.WeatherSystemApiHelper;

public class WeatherSystem implements Runnable {
    private final GameWorld world;

    public WeatherSystem(GameWorld world) { this.world = world; }

    @Override
    public void run() {
        try {
            String pick = utils.WeatherSystemApiHelper.fetchWeatherCondition();
            world.setWeather(pick);
            world.getEventQueue().offer(new Event("WeatherUpdate", e -> System.out.println("[Weather] " + pick)));
        } catch (Exception e) {
            System.out.println("WeatherSystem failed to fetch weather: " + e.getMessage());
        }
        System.out.println("WeatherSystem exiting.");
    }
}
