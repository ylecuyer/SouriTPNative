package fr.ylecuyer.souritp.DAO;

import java.util.ArrayList;

public class Line {

    private String lineId;
    private Terminus terminus;
    private String mode;

    public Line(String lineId, Terminus terminus, String mode) {
        this.lineId = lineId;
        this.terminus = terminus;
        this.mode = mode;
    }

    public String getLineId() {
        return lineId;
    }

    public Terminus getTerminus() {
        return terminus;
    }

    public String getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return "Line{" +
                "lineId='" + lineId + '\'' +
                ", terminus='" + terminus + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }
}
