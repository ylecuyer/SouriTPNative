package fr.ylecuyer.souritp;

import java.util.Comparator;

import fr.ylecuyer.souritp.DAO.Stop;

public class StopComparator implements Comparator<Stop> {
    @Override
    public int compare(Stop lhs, Stop rhs) {

        boolean lhsArrete = isArrete(lhs);
        boolean rhsArrete = isArrete(rhs);

        if (lhsArrete && rhsArrete) {
            return 0;
        }
        if (lhsArrete) {
            return -1;
        }
        if (rhsArrete) {
            return 1;
        }

        boolean lhsEnApproche = isEnApproche(lhs);
        boolean rhsEnApproche = isEnApproche(rhs);

        if (lhsEnApproche && rhsEnApproche) {
            return 0;
        }
        if (lhsEnApproche) {
            return -1;
        }
        if (rhsEnApproche) {
            return 1;
        }

        String lhsWaittimeString = lhs.getWaitTime().split(" ")[0];
        String rhsWaittimeString = rhs.getWaitTime().split(" ")[0];

        boolean lhsIsNaN = false;
        boolean rhsIsNaN = false;

        int lhsWaittime = 0;
        int rhsWaittime = 0;

        try {
            lhsWaittime = Integer.parseInt(lhsWaittimeString);
        }
        catch (NumberFormatException ex) {
            lhsIsNaN = true;
        }

        try {
            rhsWaittime = Integer.parseInt(rhsWaittimeString);
        }
        catch (NumberFormatException ex) {
            rhsIsNaN = true;
        }

        if (lhsIsNaN && rhsIsNaN) {
            return 0;
        }
        if (lhsIsNaN) {
            return 1;
        }
        if (rhsIsNaN) {
            return -1;
        }

        if (lhsWaittime == rhsWaittime) {
            return 0;
        }
        if (lhsWaittime > rhsWaittime) {
            return 1;
        }
        if (lhsWaittime < rhsWaittime) {
            return -1;
        }

        return 0;
    }

    private boolean isArrete(Stop stop) {
        return stop.getWaitTime().contains("l'arret") || stop.getWaitTime().contains("quai");
    }

    private boolean isEnApproche(Stop stop) {
        return stop.getWaitTime().contains("l'approche");
    }
}
