package fr.ylecuyer.souritp.implementations;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.interfaces.Direction;
import fr.ylecuyer.souritp.interfaces.StationFetcher;
import fr.ylecuyer.souritp.interfaces.StopFetcher;

public class BaseStationFetcher implements StationFetcher {
    
    protected Line line;
    protected Direction direction;

    public BaseStationFetcher(Line line, Direction direction) {
        this.line = line;
        this.direction = direction;
    }

    @Override
    public ArrayList<Station> getAllStations() {
        throw new UnsupportedOperationException();
    }
}
