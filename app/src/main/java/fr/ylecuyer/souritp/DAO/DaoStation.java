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

    @DatabaseField(canBeNull = false, columnName = "type")
    private String company;//RATP,TRANSDEV

    @DatabaseField(canBeNull = false)
    private String mode;//BUS,METRO,RER,TRAM

    @DatabaseField(canBeNull = false, foreign = true)
    private DaoRoute route;

    public DaoStation() {
    }

    public DaoStation(String name, String stationId, String lineId, String terminusId, String terminusName, String company, String mode) {
        this.name = name;
        this.stationId = stationId;
        this.lineId = lineId;
        this.terminusId = terminusId;
        this.terminusName = terminusName;
        this.company = company;
        this.mode = mode;
    }

    public void setRoute(DaoRoute route) {
        this.route = route;
    }

    public String getMode() {
        return mode;
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

    public String getCompany() {
        return company;
    }
}
