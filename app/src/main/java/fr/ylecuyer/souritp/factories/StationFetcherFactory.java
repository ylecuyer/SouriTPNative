package fr.ylecuyer.souritp.factories;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusStationFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroStationFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERStationFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramStationFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramStopFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevStationFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevStopFetcher;
import fr.ylecuyer.souritp.interfaces.StationFetcher;
import fr.ylecuyer.souritp.interfaces.StopFetcher;

public class StationFetcherFactory {

    public static StationFetcher getStationFetcher(String company, String mode, Line line, Terminus terminus) {

        if (company.equalsIgnoreCase("TRANSDEV")) {
            return new TransdevStationFetcher(line, terminus);
        }

        if (company.equalsIgnoreCase("RATP")) {
            if (mode.equalsIgnoreCase("BUS")) {
                return new BusStationFetcher(line, terminus);
            }
            if (mode.equalsIgnoreCase("METRO")) {
                return new MetroStationFetcher(line, terminus);
            }
            if (mode.equalsIgnoreCase("TRAM")) {
                return new TramStationFetcher(line, terminus);
            }
            if (mode.equalsIgnoreCase("RER")) {
                return new RERStationFetcher(line, terminus);
            }
        }

        return null;
    }
}
