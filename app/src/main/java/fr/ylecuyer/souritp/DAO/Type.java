package fr.ylecuyer.souritp.DAO;

import java.io.Serializable;

public class Type implements Serializable {

    private String typeId;
    private String name;
    private String typeCode;

    public Type(String typeId, String name, String typeCode) {
        this.typeId = typeId;
        this.name = name;
        this.typeCode = typeCode;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getName() {
        return name;
    }

    public String getTypeCode() {
        return typeCode;
    }

    @Override
    public String toString() {
        return "Type{" +
                "typeId='" + typeId + '\'' +
                ", name='" + name + '\'' +
                ", typeCode='" + typeCode + '\'' +
                '}';
    }
}
