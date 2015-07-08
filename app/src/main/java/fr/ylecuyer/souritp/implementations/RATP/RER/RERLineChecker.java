package fr.ylecuyer.souritp.implementations.RATP.RER;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.implementations.BaseLineChecker;

public class RERLineChecker extends BaseLineChecker {

    public RERLineChecker(Line line) {
        super(line);
    }

    @Override
    public boolean isValid() {
        return line.getLineId().toLowerCase().matches("a|b");
    }
}
