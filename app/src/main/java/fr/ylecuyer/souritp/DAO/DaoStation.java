package fr.ylecuyer.souritp.DAO;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "stations")
public class DaoStation {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private String stationId;

    @DatabaseField(canBeNull = false)
    private String lineId;

    @DatabaseField(canBeNull = false)
    private String terminusId;

    @DatabaseField(canBeNull = false)
    private String terminusName;

    @DatabaseField(canBeNull = false)
    private String type;

    @DatabaseField(canBeNull = false, columnName = "mode")
    private String typeId;

    @DatabaseField(canBeNull = false, foreign = true)
    private DaoRoute route;

    public DaoStation() {
    }

    public DaoStation(String name, String stationId, String lineId, String terminusId, String terminusName, String type, String typeId) {
        this.name = name;
        this.stationId = stationId;
        this.lineId = lineId;
        this.terminusId = terminusId;
        this.terminusName = terminusName;
        this.type = type;
        this.typeId = typeId;
    }

    public void setRoute(DaoRoute route) {
        this.route = route;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getName() {
        return name;
    }

    public String getStationId() {
        return stationId;
    }

    public String getLineId() {
        return lineId;
    }

    public String getTerminusId() {
        return terminusId;
    }

    public String getTerminusName() {
        return terminusName;
    }

    public String getType() {
        return type;
    }
}
