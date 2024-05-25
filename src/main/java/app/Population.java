package app;

public class Population {
    private int Year;
    private long Popualtion;

    public Population (int Year, long Popualtion) {
        this.Year = Year;
        this.Popualtion = Popualtion;
    }

    public int getYear() {
        return Year;
    }

    public long getPopualtion() {
        return Popualtion;
    }
}
