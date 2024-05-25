package app;

public class globalTemp {
    private int year;
    private double temp;
    public globalTemp(int year, double temp){
        this.year = year;
        this.temp = temp;
    }
    public int getYear() {
        return year;
    }
    public double getTemp() {
        return temp;
    }
}
