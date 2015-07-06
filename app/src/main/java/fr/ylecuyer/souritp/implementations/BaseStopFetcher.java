package fr.ylecuyer.souritp.implementations;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Stop;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.interfaces.StopFetcher;

public class BaseStopFetcher implements StopFetcher {

    protected Station station;
    protected Terminus terminus;
    protected String lineId;

    public BaseStopFetcher(Station station, Terminus terminus, String lineId) {
        this.station = station;
        this.terminus = terminus;
        this.lineId = lineId;
    }

    @Override
    public ArrayList<Stop> nextStops() {
        throw new UnsupportedOperationException();
    }
}
