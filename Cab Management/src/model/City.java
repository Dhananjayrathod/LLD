package model;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class City {

    private final String cityId;
    private final String cityName;

    public City(String cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

}
