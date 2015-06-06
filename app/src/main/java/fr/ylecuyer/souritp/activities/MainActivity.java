package fr.ylecuyer.souritp.activities;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;

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
import fr.ylecuyer.souritp.DAO.DaoStation;
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

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<DaoStation, Long> stationDao;

    @Bean
    RouteListAdapter adapter;

    @AfterViews
    void bindAdapter() {
        updateAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu_main, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        long routeId = listView.getAdapter().getItemId(position);

        switch(item.getItemId()) {
            case R.id.edit:
                // edit stuff here
                return true;
            case R.id.delete:

                try {
                    DaoRoute route = routeDao.queryForId(routeId);
                    ForeignCollection<DaoStation> stations = route.getStations();

                    stationDao.delete(stations);
                    routeDao.delete(route);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                updateAdapter();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
