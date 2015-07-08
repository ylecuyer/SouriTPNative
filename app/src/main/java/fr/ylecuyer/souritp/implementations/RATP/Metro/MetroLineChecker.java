package fr.ylecuyer.souritp.implementations.RATP.Metro;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.implementations.BaseLineChecker;

public class MetroLineChecker extends BaseLineChecker {

    public MetroLineChecker(Line line) {
        super(line);
    }

    @Override
    public boolean isValid() {
        return line.getLineId().toLowerCase().matches("[1-2]|3b?|[4-6]|7b?|[8-9]|1[0-4]");
    }
}
