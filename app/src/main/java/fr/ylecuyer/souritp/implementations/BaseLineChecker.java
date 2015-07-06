package fr.ylecuyer.souritp.implementations;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.interfaces.LineChecker;
import fr.ylecuyer.souritp.interfaces.TerminusFetcher;

public class BaseLineChecker implements LineChecker {

    public Line line;

    public BaseLineChecker(Line line) {
        this.line = line;
    }

    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException();
    }
}
