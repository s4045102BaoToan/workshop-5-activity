package app;

public class PeriodData {
    private String country;
    private String state;
    private String city;
    private double averageTemperature;
    private double averagePopulation;

    public PeriodData(String country, String state, String city, double averageTemperature, double averagePopulation) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.averageTemperature = averageTemperature;
        this.averagePopulation = averagePopulation;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public double getAveragePopulation() {
        return averagePopulation;
    }
}
