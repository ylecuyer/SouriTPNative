package fr.ylecuyer.souritp.DAO;

import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

public class Station implements Serializable {

    private String name;
    private String stationId;
    private Line line;

    public Station(String name, String stationId, Line line) {
        this.name = name;
        this.stationId = stationId;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public String getStationId() {
        return stationId;
    }

    public Line getLine() {
        return line;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", stationId='" + stationId + '\'' +
                ", line=" + line +
                '}';
    }
}
