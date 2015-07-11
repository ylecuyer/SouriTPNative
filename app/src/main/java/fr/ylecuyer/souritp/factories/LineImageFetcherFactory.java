package fr.ylecuyer.souritp.factories;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusLineImageFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroLineImageFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERLineImageFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramLineImageFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramStopFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevLineImageFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevStopFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevTypeFetcher;
import fr.ylecuyer.souritp.interfaces.LineImageFetcher;
import fr.ylecuyer.souritp.interfaces.TypeFetcher;

public class LineImageFetcherFactory {

    public static LineImageFetcher getLineImageFetcher(String company, String mode, Line line) {

        if (company.equalsIgnoreCase("TRANSDEV")) {
            return new TransdevLineImageFetcher(line);
        }

        if (company.equalsIgnoreCase("RATP")) {
            if (mode.equalsIgnoreCase("BUS")) {
                return new BusLineImageFetcher(line);
            }
            if (mode.equalsIgnoreCase("METRO")) {
                return new MetroLineImageFetcher(line);
            }
            if (mode.equalsIgnoreCase("TRAM")) {
                return new TramLineImageFetcher(line);
            }
            if (mode.equalsIgnoreCase("RER")) {
                return new RERLineImageFetcher(line);
            }
        }

        return null;
    }
}
