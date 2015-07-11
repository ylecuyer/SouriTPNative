package fr.ylecuyer.souritp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.j256.ormlite.dao.Dao;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;

import de.timroes.android.listview.EnhancedListView;
import fr.ylecuyer.souritp.BuildConfig;
import fr.ylecuyer.souritp.DAO.DaoRoute;
import fr.ylecuyer.souritp.DAO.DaoStation;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.R;
import fr.ylecuyer.souritp.database.DatabaseHelper;
import fr.ylecuyer.souritp.views.StationListAdapter;

@EActivity(R.layout.activity_new_route)
public class NewRouteActivity extends Activity implements MaterialDialog.ListCallback {

    private static final int STATION_SELECTED = 0;
    private ArrayList<Station> stations = new ArrayList<Station>();

    @ViewById
    MaterialEditText nameEditText;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<DaoRoute, Long> routeDao;


    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<DaoStation, Long> stationDao;

    @ViewById
    EnhancedListView stationList;

    @Bean
    StationListAdapter adapter;


    @AfterViews
    void bindAdapter() {
        adapter.setStations(stations);
        stationList.setDismissCallback(new EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView enhancedListView, int i) {

                adapter.remove(i);
                adapter.notifyDataSetChanged();

                return null;
            }
        });
        stationList.enableSwipeToDismiss();

        View footerView = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        stationList.addFooterView(footerView);
        stationList.setAdapter(adapter);
    }

    public void addStationClick(View v) {

        new MaterialDialog.Builder(this)
                .title("Company")
                .items(R.array.companies)
                .itemsCallback(this)
                .show();
    }

    @Click
    void fab() {

        String routeName = nameEditText.getText().toString().trim();

        if (routeName.isEmpty()) {
            nameEditText.setError("Route name can't be empty");
            return;
        }

        if (stations.size() == 0) {
            new MaterialDialog.Builder(this)
                    .title("Error")
                    .content("You should add at least one station")
                    .positiveText("OK")
                    .show();
            return;
        }

        if (BuildConfig.DEBUG)
            Log.d("SouriTP", "Sauvegarde de la route");

        try {

            DaoRoute route = new DaoRoute(routeName);
            routeDao.create(route);

            for (Station station : stations) {
                DaoStation dao_station = new DaoStation(station.getName(), station.getStationId(), station.getLine().getLineId(), station.getLine().getTerminus().getTerminusId(), station.getLine().getTerminus().getName(), station.getLine().getType().getCompany(), station.getLine().getType().getMode());
                dao_station.setRoute(route);
                stationDao.create(dao_station);
            }

            finish();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
        switch (i) {
            case 0://RATP
                RATPStationSelectionActivity_.intent(this).startForResult(STATION_SELECTED);
                break;
            case 1://Transdev
                TransdevStationSelectionActivity_.intent(this).startForResult(STATION_SELECTED);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == STATION_SELECTED) {

            Station station = (Station)data.getSerializableExtra("station");

            stations.add(station);

            adapter.setStations(stations);
            adapter.notifyDataSetChanged();
        }
    }
}
