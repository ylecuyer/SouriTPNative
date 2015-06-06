package fr.ylecuyer.souritp.DAO;

public class Stop {

    private String terminus;
    private String waitTime;

    public Stop(String terminus, String waitTime) {
        this.terminus = terminus;
        this.waitTime = waitTime;
    }

    public String getTerminus() {
        return terminus;
    }

    public String getWaitTime() {
        return waitTime;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "terminus='" + terminus + '\'' +
                ", waitTime='" + waitTime + '\'' +
                '}';
    }
}
