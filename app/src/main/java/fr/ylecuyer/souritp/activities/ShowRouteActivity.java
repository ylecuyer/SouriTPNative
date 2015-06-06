package fr.ylecuyer.souritp.activities;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.joda.time.LocalTime;
import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import fr.ylecuyer.souritp.DAO.DaoRoute;
import fr.ylecuyer.souritp.DAO.DaoStation;
import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Stop;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.R;
import fr.ylecuyer.souritp.StopComparator;
import fr.ylecuyer.souritp.database.DatabaseHelper;
import fr.ylecuyer.souritp.implementations.Bus.BusStopFetcher;
import fr.ylecuyer.souritp.implementations.Metro.MetroStopFetcher;
import fr.ylecuyer.souritp.implementations.RER.RERStopFetcher;
import fr.ylecuyer.souritp.implementations.Tram.TramStopFetcher;
import fr.ylecuyer.souritp.interfaces.Direction;
import fr.ylecuyer.souritp.interfaces.StopFetcher;
import fr.ylecuyer.souritp.views.RouteListAdapter;
import fr.ylecuyer.souritp.views.StopListAdapter;

@EActivity(R.layout.activity_show_route)
public class ShowRouteActivity extends Activity {

    @Extra
    long routeId;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<DaoRoute, Long> routeDao;

    DaoRoute route;

    @AfterInject
    void loadRoute() {
        try {
            route = routeDao.queryForId(routeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @ViewById
    TextView nameView;

    @ViewById
    TextView lastRefreshView;

    @ViewById
    ListView stopList;

    @Bean
    StopListAdapter adapter;

    @AfterViews
    void init() {
        nameView.setText(route.getName());
        lastRefreshView.setText("-- : --");
    }

    @AfterViews
    void bindAdapter() {
        updateStops();
        stopList.setAdapter(adapter);
    }

    @Background
    void updateStops() {
        Log.d("SouriTP", "Update stops");
        ArrayList<Stop> stops = new ArrayList<Stop>();

        ForeignCollection<DaoStation> stations = route.getStations();

        for (DaoStation station : stations) {
            String mode = station.getMode();
            String name = station.getName();
            String stationId = station.getStationId();
            String terminusId = station.getTerminusId();
            String lineId = station.getLineId();
            String terminusName = station.getTerminusName();

            Terminus terminus = new Terminus(terminusName, terminusId);
            Line line = new Line(lineId, terminus, mode);
            Station sta = new Station(name, stationId, line);

            StopFetcher stopFetcher = null;

            switch (mode.toUpperCase()) {
                case "BUS":
                    stopFetcher = new BusStopFetcher(sta, terminus);
                    break;
                case "METRO":
                    stopFetcher = new MetroStopFetcher(sta, terminus);
                    break;
                case "RER":
                    stopFetcher = new RERStopFetcher(sta, terminus);
                    break;
                case "TRAM":
                    stopFetcher = new TramStopFetcher(sta, terminus);
                    break;
            }

            stops.addAll(stopFetcher.nextStops());
        }

        Collections.sort(stops, new StopComparator());

        updateAdapter(stops);
    }

    @UiThread
    void updateAdapter(ArrayList<Stop> stops) {
        LocalTime now = LocalTime.now();
        lastRefreshView.setText(now.toString("HH:mm"));
        adapter.setStops(stops);
    }

    @Click
    void fab() {
        updateStops();
    }
}