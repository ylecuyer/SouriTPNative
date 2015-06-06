package fr.ylecuyer.souritp.DAO;

public class Stop {

    private String terminus;
    private String waitTime;
    private Line line;

    public Stop(String terminus, String waitTime, Line line) {
        this.terminus = terminus;
        this.waitTime = waitTime;
        this.line = line;
    }

    public String getTerminus() {
        return terminus;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public Line getLine() {
        return line;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "terminus='" + terminus + '\'' +
                ", waitTime='" + waitTime + '\'' +
                ", line=" + line +
                '}';
    }
}
