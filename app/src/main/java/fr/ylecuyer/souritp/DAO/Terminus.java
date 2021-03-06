package fr.ylecuyer.souritp.DAO;

import java.io.Serializable;

public class Terminus implements Serializable {

    private String terminusId;
    private String name;

    public Terminus(String name, String terminusId) {
        this.name = name;
        this.terminusId = terminusId;
    }

    public String getTerminusId() {
        return terminusId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Terminus{" +
                "terminusId='" + terminusId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
