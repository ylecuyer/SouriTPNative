package fr.ylecuyer.souritp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.DaoRoute;
import fr.ylecuyer.souritp.DAO.DaoStation;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.R;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "souritpdb";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, DaoRoute.class);
            TableUtils.createTable(connectionSource, DaoStation.class);
          } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {

        try {

            Dao<DaoStation, Integer> dao = getDao(DaoStation.class);

            if (oldVersion < 2) {
                dao.executeRaw("ALTER TABLE stations ADD COLUMN type VARCHAR");
                dao.updateRaw("UPDATE stations SET type='RATP'");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}