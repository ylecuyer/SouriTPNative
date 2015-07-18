package fr.ylecuyer.souritp.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ProgressBarIndeterminate;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.joda.time.LocalTime;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import fr.ylecuyer.souritp.BuildConfig;
import fr.ylecuyer.souritp.DAO.DaoRoute;
import fr.ylecuyer.souritp.DAO.DaoStation;
import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Stop;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.DAO.Type;
import fr.ylecuyer.souritp.R;
import fr.ylecuyer.souritp.StopComparator;
import fr.ylecuyer.souritp.database.DatabaseHelper;
import fr.ylecuyer.souritp.factories.StopFetcherFactory;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERStopFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramStopFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevStopFetcher;
import fr.ylecuyer.souritp.interfaces.StopFetcher;
import fr.ylecuyer.souritp.views.StopListAdapter;

@EActivity(R.layout.activity_show_route)
public class ShowRouteActivity extends Activity {

    @Extra
    long routeId;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<DaoRoute, Long> routeDao;

    DaoRoute route;
    private LayoutInflater inflater;

    @AfterInject
    void loadRoute() {
        try {
            route = routeDao.queryForId(routeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @ViewById
    ListView stopList;

    @ViewById
    FABProgressCircle fabProgressCircle;

    @Bean
    StopListAdapter adapter;

    private ActionBar actionBar;

    @AfterViews
    void init() {
        actionBar = getActionBar();
        actionBar.setTitle(route.getName());
        actionBar.setSubtitle("Last update: -- : --");
    }

    @AfterViews
    void bindAdapter() {
        updateStopsUI();
        stopList.setAdapter(adapter);
    }

    @UiThread
    void updateStopsUI() {
        fabProgressCircle.measure(15, 15);
        fabProgressCircle.show();
        updateStops();
    }

    @Background
    void updateStops() {

        if (!isNetworkAvailable()) {
            displayNetworkAlert();
            return;
        }

        if (BuildConfig.DEBUG)
            Log.d("SouriTP", "Update stops");

        ArrayList<Stop> stops = new ArrayList<Stop>();

        ForeignCollection<DaoStation> stations = route.getStations();

        for (DaoStation station : stations) {
            String company = station.getCompany();
            String mode = station.getMode();
            String name = station.getName();
            String stationId = station.getStationId();
            String terminusId = station.getTerminusId();
            String lineId = station.getLineId();
            String terminusName = station.getTerminusName();

            Type type = new Type(mode, "", company);
            Terminus terminus = new Terminus(terminusName, terminusId);
            Line line = new Line(lineId, terminus, type);
            Station sta = new Station(name, stationId, line);

            StopFetcher stopFetcher = StopFetcherFactory.getStopFetcher(company, mode, sta, terminus, line);

            stops.addAll(stopFetcher.nextStops());
        }

        Collections.sort(stops, new StopComparator());

        updateAdapter(stops);
    }

    @UiThread
    void updateAdapter(ArrayList<Stop> stops) {
        LocalTime now = LocalTime.now();
        actionBar.setSubtitle("Last update: " + now.toString("HH:mm"));
        adapter.setStops(stops);
        fabProgressCircle.hide();
    }

    @Click
    void fab() {
        fabProgressCircle.show();
        updateStopsUI();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    void displayNetworkAlert() {
        new MaterialDialog.Builder(this)
                .title("Error")
                .content("You must have internet active to use the app")
                .positiveText("OK")
                .show();
    }
}
