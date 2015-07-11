package fr.ylecuyer.souritp.implementations;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Stop;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.interfaces.StopFetcher;

public class BaseStopFetcher implements StopFetcher {

    protected Station station;
    protected Terminus terminus;
    protected Line line;

    public BaseStopFetcher(Station station, Terminus terminus, Line line) {
        this.station = station;
        this.terminus = terminus;
        this.line = line;
    }

    @Override
    public ArrayList<Stop> nextStops() {
        throw new UnsupportedOperationException();
    }
}
