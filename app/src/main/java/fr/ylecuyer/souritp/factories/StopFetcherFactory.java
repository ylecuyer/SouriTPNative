package fr.ylecuyer.souritp.factories;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramStopFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevStopFetcher;
import fr.ylecuyer.souritp.interfaces.StopFetcher;

public class StopFetcherFactory {

    public static StopFetcher getStopFetcher(String company, String mode, Station station, Terminus terminus, Line line) {

        if (company.equalsIgnoreCase("TRANSDEV")) {
            return new TransdevStopFetcher(station, terminus, line);
        }

        if (company.equalsIgnoreCase("RATP")) {
            if (mode.equalsIgnoreCase("BUS")) {
                return new BusStopFetcher(station, terminus, line);
            }
            if (mode.equalsIgnoreCase("METRO")) {
                return new MetroStopFetcher(station, terminus, line);
            }
            if (mode.equalsIgnoreCase("TRAM")) {
                return new TramStopFetcher(station, terminus, line);
            }
            if (mode.equalsIgnoreCase("RER")) {
                return new RERStopFetcher(station, terminus, line);
            }
        }

        return null;
    }
}
