package service;

import model.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class CityService {
    private final Map<String, City> cities = new ConcurrentHashMap<>();

    public void registerCity(String cityId, String cityName) {
        if (cities.containsKey(cityId)) {
            throw new IllegalArgumentException("City already registered");
        }
        City city = new City(cityId, cityName);
        cities.put(cityId, city);
        System.out.println("City " + cityName + " (ID: " + cityId + ") registered");
    }

    public City getCity(String cityId) {
        if (!cities.containsKey(cityId)) {
            throw new IllegalArgumentException("City not found");
        }
        return cities.get(cityId);
    }

    public List<City> getAllCities() {
        return new ArrayList<>(cities.values());
    }
}
