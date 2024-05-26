package app;

public class ctryInPeriod {
    private String countryname;
    private double temperature;
    private long population;
    public ctryInPeriod(String countryname, double temperature, long population){
        this.countryname = countryname;
        this.temperature = temperature;
        this.population = population;
    }
    public String getCountryname() {
        return countryname;
    }
    public double getTemperature() {
        return temperature;
    }
    public long getPopulation() {
        return population;
    }
    
}
