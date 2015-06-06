package fr.ylecuyer.souritp.implementations;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.interfaces.TerminusFetcher;

public class BaseTerminusFetcher implements TerminusFetcher {

    public Line line;

    public BaseTerminusFetcher(Line line) {
        this.line = line;
    }

    @Override
    public ArrayList<Terminus> getAllTerminuses() {
        throw new UnsupportedOperationException();
    }
}
