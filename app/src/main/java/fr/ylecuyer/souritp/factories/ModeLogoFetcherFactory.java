package fr.ylecuyer.souritp.factories;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusLineImageFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusModeLogoFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroLineImageFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroModeLogoFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERLineImageFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERModeLogoFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramLineImageFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramModeLogoFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevLineImageFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevModeLogoFetcher;
import fr.ylecuyer.souritp.interfaces.LineImageFetcher;
import fr.ylecuyer.souritp.interfaces.ModeLogoFetcher;

public class ModeLogoFetcherFactory {

    public static ModeLogoFetcher getModeLogoFetcher(String company, String mode) {

        if (company.equalsIgnoreCase("TRANSDEV")) {
            return new TransdevModeLogoFetcher();
        }

        if (company.equalsIgnoreCase("RATP")) {
            if (mode.equalsIgnoreCase("BUS")) {
                return new BusModeLogoFetcher();
            }
            if (mode.equalsIgnoreCase("METRO")) {
                return new MetroModeLogoFetcher();
            }
            if (mode.equalsIgnoreCase("TRAM")) {
                return new TramModeLogoFetcher();
            }
            if (mode.equalsIgnoreCase("RER")) {
                return new RERModeLogoFetcher();
            }
        }

        return null;
    }
}
