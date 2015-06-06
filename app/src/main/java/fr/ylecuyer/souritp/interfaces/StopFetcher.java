package fr.ylecuyer.souritp.interfaces;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Stop;

public interface StopFetcher {
    public ArrayList<Stop> nextStops();
}
