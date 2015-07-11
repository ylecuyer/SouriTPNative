package fr.ylecuyer.souritp.factories;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusTerminusFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroTerminusFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERTerminusFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramTerminusFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevTerminusFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevTypeFetcher;
import fr.ylecuyer.souritp.interfaces.TerminusFetcher;
import fr.ylecuyer.souritp.interfaces.TypeFetcher;

public class TypeFetcherFactory {

    public static TypeFetcher getTypeFetcher(String company) {

        if (company.equalsIgnoreCase("TRANSDEV")) {
            return new TransdevTypeFetcher();
        }

        return null;
    }
}
