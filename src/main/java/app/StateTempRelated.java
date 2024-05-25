package app;

public class StateTempRelated {
    private String stateName;
    private String startTemp;
    private String endTemp;
    private String tempDif;
    
    public StateTempRelated(String stateName,String startTemp,String endTemp,String tempDif){
        this.stateName = stateName;
        this.startTemp = startTemp;
        this.endTemp = endTemp;
        this.tempDif = tempDif;
    }

    public String getStateName() {
        return stateName;
    }

    public String getStartTemp() {
        return startTemp;
    }

    public String getEndTemp() {
        return endTemp;
    }

    public String getTempDif() {
        return tempDif;
    }
}
