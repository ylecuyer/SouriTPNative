package fr.ylecuyer.souritp.DAO;

import java.io.Serializable;

public class Type implements Serializable {

    private String mode;
    private String name;
    private String company;

    public Type(String mode, String name, String company) {
        this.mode = mode;
        this.name = name;
        this.company = company;
    }

    public String getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }
}
