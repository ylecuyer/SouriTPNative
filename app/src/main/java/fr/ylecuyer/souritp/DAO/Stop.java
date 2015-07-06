package fr.ylecuyer.souritp.DAO;

public class Stop {

    private String terminus;
    private String waitTime;
    private Line line;
    private Station station;

    public Stop(String terminus, String waitTime, Line line, Station station) {
        this.terminus = terminus;
        this.waitTime = waitTime;
        this.line = line;
        this.station = station;
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

    public Station getStation() {
        return station;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "terminus='" + terminus + '\'' +
                ", waitTime='" + waitTime + '\'' +
                ", line=" + line +
                ", station=" + station +
                '}';
    }
}
