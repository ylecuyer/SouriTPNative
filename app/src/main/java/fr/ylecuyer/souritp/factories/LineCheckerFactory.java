package fr.ylecuyer.souritp.factories;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusLineChecker;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusStationFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroLineChecker;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroStationFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERLineChecker;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERStationFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramLineChecker;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramStationFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevLineChecker;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevStationFetcher;
import fr.ylecuyer.souritp.interfaces.LineChecker;
import fr.ylecuyer.souritp.interfaces.StationFetcher;

public class LineCheckerFactory {

    public static LineChecker getLineChecker(String company, String mode, Line line) {

        if (company.equalsIgnoreCase("TRANSDEV")) {
            return new TransdevLineChecker(line);
        }

        if (company.equalsIgnoreCase("RATP")) {
            if (mode.equalsIgnoreCase("BUS")) {
                return new BusLineChecker(line);
            }
            if (mode.equalsIgnoreCase("METRO")) {
                return new MetroLineChecker(line);
            }
            if (mode.equalsIgnoreCase("TRAM")) {
                return new TramLineChecker(line);
            }
            if (mode.equalsIgnoreCase("RER")) {
                return new RERLineChecker(line);
            }
        }

        return null;
    }
}
