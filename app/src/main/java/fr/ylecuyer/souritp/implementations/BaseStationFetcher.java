package fr.ylecuyer.souritp.implementations;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.interfaces.StationFetcher;
import fr.ylecuyer.souritp.interfaces.StopFetcher;

public class BaseStationFetcher implements StationFetcher {
    
    protected Line line;
    protected Terminus terminus;

    public BaseStationFetcher(Line line, Terminus terminus) {
        this.line = line;
        this.terminus = terminus;
    }

    @Override
    public ArrayList<Station> getAllStations() {
        throw new UnsupportedOperationException();
    }
}
