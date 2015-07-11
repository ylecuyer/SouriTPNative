package fr.ylecuyer.souritp.DAO;

import java.io.Serializable;
import java.util.ArrayList;

public class Line implements Serializable {

    private String lineId;
    private Terminus terminus;
    private Type type;

    public Line(String lineId, Terminus terminus, Type type) {
        this.lineId = lineId;
        this.terminus = terminus;
        this.type = type;
    }

    public String getLineId() {
        return lineId;
    }

    public Terminus getTerminus() {
        return terminus;
    }

    public Type getType() {
        return type;
    }

    public String getHash() {
        return type.getMode() + lineId;
    }

    @Override
    public String toString() {
        return "Line{" +
                "lineId='" + lineId + '\'' +
                ", terminus=" + terminus +
                ", type=" + type +
                '}';
    }
}
