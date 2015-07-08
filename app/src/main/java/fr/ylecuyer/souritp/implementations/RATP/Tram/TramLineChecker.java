package fr.ylecuyer.souritp.implementations.RATP.Tram;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.implementations.BaseLineChecker;

public class TramLineChecker extends BaseLineChecker {

    public TramLineChecker(Line line) {
        super(line);
    }

    @Override
    public boolean isValid() {
        return line.getLineId().toLowerCase().matches("[1-2]|3[ab]|[4-8]");
    }

}
