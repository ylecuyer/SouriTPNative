package fr.ylecuyer.souritp.factories;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusTerminusFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroTerminusFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERTerminusFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramTerminusFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevStopFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevTerminusFetcher;
import fr.ylecuyer.souritp.interfaces.StopFetcher;
import fr.ylecuyer.souritp.interfaces.TerminusFetcher;

public class TerminusFetcherFactory {

    public static TerminusFetcher getTerminusFetcher(String company, String mode, Line line) {

        if (company.equalsIgnoreCase("TRANSDEV")) {
            return new TransdevTerminusFetcher(line);
        }

        if (company.equalsIgnoreCase("RATP")) {
            if (mode.equalsIgnoreCase("BUS")) {
                return new BusTerminusFetcher(line);
            }
            if (mode.equalsIgnoreCase("METRO")) {
                return new MetroTerminusFetcher(line);
            }
            if (mode.equalsIgnoreCase("TRAM")) {
                return new TramTerminusFetcher(line);
            }
            if (mode.equalsIgnoreCase("RER")) {
                return new RERTerminusFetcher(line);
            }
        }

        return null;
    }
}
