package fr.ylecuyer.souritp.DAO;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "routes")
public class DaoRoute {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String name;

    @ForeignCollectionField
    private ForeignCollection<DaoStation> stations;

    public DaoRoute() {
    }

    public DaoRoute(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ForeignCollection<DaoStation> getStations() {
        return stations;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stations=" + stations +
                '}';
    }
}
