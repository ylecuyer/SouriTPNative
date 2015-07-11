package fr.ylecuyer.souritp.factories;

import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramStopFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevStopFetcher;
import fr.ylecuyer.souritp.interfaces.StopFetcher;

public class StopFetcherFactory {

    public static StopFetcher getStopFetcher(String company, String mode, Station station, Terminus terminus, String lineId) {

        if (company.equalsIgnoreCase("TRANSDEV")) {
            return new TransdevStopFetcher(station, terminus, lineId);
        }

        if (company.equalsIgnoreCase("RATP")) {
            if (mode.equalsIgnoreCase("BUS")) {
                return new BusStopFetcher(station, terminus, lineId);
            }
            if (mode.equalsIgnoreCase("METRO")) {
                return new MetroStopFetcher(station, terminus, lineId);
            }
            if (mode.equalsIgnoreCase("TRAM")) {
                return new TramStopFetcher(station, terminus, lineId);
            }
            if (mode.equalsIgnoreCase("RER")) {
                return new RERStopFetcher(station, terminus, lineId);
            }
        }

        return null;
    }
}
