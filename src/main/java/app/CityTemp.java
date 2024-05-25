package app;

public class CityTemp {
    private String cityName;
    private double startTemp;
    private double endTemp;
    private double tempDiff;

    public  CityTemp(String cityName, double startTemp, double endTemp, double tempDiff) {
        this.cityName = cityName;
        this.startTemp = startTemp;
        this.endTemp = endTemp;
        this.tempDiff = tempDiff;
    }

    public String getCityName() {
        return cityName;
    }
    public double getStartTemp() {
        return startTemp;
    }
    public double getEndTemp() {
        return endTemp;
    }
    public double getTempDiff() {
        return tempDiff;
    }
}