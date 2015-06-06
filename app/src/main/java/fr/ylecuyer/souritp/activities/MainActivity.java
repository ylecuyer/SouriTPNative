package fr.ylecuyer.souritp.activities;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.ylecuyer.souritp.DAO.DaoRoute;
import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.R;
import fr.ylecuyer.souritp.database.DatabaseHelper;
import fr.ylecuyer.souritp.implementations.Bus.BusStationFetcher;
import fr.ylecuyer.souritp.implementations.Bus.BusStopFetcher;
import fr.ylecuyer.souritp.implementations.Metro.MetroStationFetcher;
import fr.ylecuyer.souritp.implementations.Metro.MetroStopFetcher;
import fr.ylecuyer.souritp.implementations.RER.RERStationFetcher;
import fr.ylecuyer.souritp.implementations.RER.RERStopFetcher;
import fr.ylecuyer.souritp.implementations.Tram.TramStationFetcher;
import fr.ylecuyer.souritp.implementations.Tram.TramStopFetcher;
import fr.ylecuyer.souritp.interfaces.Direction;
import fr.ylecuyer.souritp.views.RouteListAdapter;


@EActivity(R.layout.activity_main)
public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    public static final int ROUTE_ADDED = 0;

    @ViewById
    ListView listView;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<DaoRoute, Long> routeDao;

    @Bean
    RouteListAdapter adapter;

    @AfterViews
    void bindAdapter() {
        updateAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Click
    void fab() {
        NewRouteActivity_.intent(this).startForResult(ROUTE_ADDED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ROUTE_ADDED:
                updateAdapter();
                break;
        }
    }

    public void updateAdapter() {

        List<DaoRoute> routes = new ArrayList<DaoRoute>();

        try {
            routes = routeDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        adapter.setRoutes(routes);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d("SouriTP", "Showing route: " + id);

        ShowRouteActivity_.intent(this).routeId(id).start();
    }
}
