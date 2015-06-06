package fr.ylecuyer.souritp.interfaces;

public enum Direction {
    NORMAL("A"), RETURN("R");

    private final String sens;

    Direction(String sens) {
        this.sens = sens;
    }

    public String getValue() {
        return sens;
    }
}