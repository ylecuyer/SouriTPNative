package fr.ylecuyer.souritp.activities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.j256.ormlite.dao.Dao;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;

import de.timroes.android.listview.EnhancedListView;
import fr.ylecuyer.souritp.DAO.DaoRoute;
import fr.ylecuyer.souritp.DAO.DaoStation;
import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.R;
import fr.ylecuyer.souritp.database.DatabaseHelper;
import fr.ylecuyer.souritp.implementations.Bus.BusStationFetcher;
import fr.ylecuyer.souritp.implementations.Bus.BusTerminusFetcher;
import fr.ylecuyer.souritp.implementations.Metro.MetroStationFetcher;
import fr.ylecuyer.souritp.implementations.Metro.MetroTerminusFetcher;
import fr.ylecuyer.souritp.implementations.RER.RERStationFetcher;
import fr.ylecuyer.souritp.implementations.RER.RERTerminusFetcher;
import fr.ylecuyer.souritp.implementations.Tram.TramStationFetcher;
import fr.ylecuyer.souritp.implementations.Tram.TramTerminusFetcher;
import fr.ylecuyer.souritp.interfaces.Direction;
import fr.ylecuyer.souritp.interfaces.StationFetcher;
import fr.ylecuyer.souritp.interfaces.TerminusFetcher;
import fr.ylecuyer.souritp.views.StationListAdapter;
import fr.ylecuyer.souritp.views.StationSpinnerAdapter;
import fr.ylecuyer.souritp.views.TerminusSpinnerAdapter;

@EActivity(R.layout.activity_new_route)
public class NewRouteActivity extends Activity {

    private Spinner terminusSpinner;
    private EditText lineText;
    private Spinner stationSpinner;
    private Spinner modeSpinner;

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

    @Bean
    StationSpinnerAdapter stationSpinnerAdapter;

    @Bean
    TerminusSpinnerAdapter terminusSpinnerAdapter;

    @AfterViews
    void bindAdapter() {
        adapter.setStations(stations);
        stationList.setAdapter(adapter);
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
    }

    public void addStationClick(View v) {
        boolean wrapInScrollView = true;
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .cancelable(false)
                .title("Add new route")
                .customView(R.layout.dialog_add_to_route, wrapInScrollView)
                .positiveText("Add")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Log.d("SouriTP", "Add station");

                        Station station = (Station)stationSpinner.getSelectedItem();

                        stations.add(station);

                        adapter.setStations(stations);
                        adapter.notifyDataSetChanged();
                    }
                })
                .negativeText("Cancel").build();

        View view = dialog.getCustomView();

        modeSpinner = (Spinner)view.findViewById(R.id.modeSpinner);

        terminusSpinner = (Spinner)view.findViewById(R.id.terminusSpinner);
        stationSpinner = (Spinner)view.findViewById(R.id.stationSpinner);

        terminusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateStations();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lineText = (EditText)view.findViewById(R.id.lineId);

        lineText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    //hide keyboard
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                    updateDirections();

                    return true;
                }

                return false;
            }
        });

        dialog.show();
    }

    @Background
    public void updateDirections() {
        Log.d("SouriTP", "Updating directions");

        ArrayList<Terminus> terminuses = new ArrayList<Terminus>();

        TerminusFetcher terminusFetcher = null;

        String mode = (String)modeSpinner.getSelectedItem();

        String lineId = lineText.getText().toString();

        Line line = new Line(lineId, null, mode.toUpperCase());

        switch (mode.toUpperCase()) {
            case "BUS":
                terminusFetcher = new BusTerminusFetcher(line);
                break;
            case "METRO":
                terminusFetcher = new MetroTerminusFetcher(line);
                break;
            case "TRAM":
                terminusFetcher = new TramTerminusFetcher(line);
                break;
            case "RER":
                terminusFetcher = new RERTerminusFetcher(line);
                break;
        }

        terminuses = terminusFetcher.getAllTerminuses();

        updateDirectionSpinner(terminuses);
    }

    @UiThread
    void updateDirectionSpinner(ArrayList<Terminus> terminuses) {
        terminusSpinnerAdapter.setTerminuses(terminuses);
        terminusSpinnerAdapter.notifyDataSetChanged();
        terminusSpinner.setAdapter(terminusSpinnerAdapter);
    }

    @Background
    public void updateStations() {

        String mode = (String)modeSpinner.getSelectedItem();
        Log.d("SouriTP", "mode: " + mode);

        String lineId = lineText.getText().toString();
        Log.d("SouriTP", "line: " + lineId);

        Terminus terminus = (Terminus)terminusSpinner.getSelectedItem();
        Log.d("SouriTP", "terminus: " + terminus);

        Line line = new Line(lineId, terminus, mode.toUpperCase());

        Direction direction = null;

        switch (terminus.getTerminusId().toString().toUpperCase()) {
            case "A":
                direction = Direction.NORMAL;
                break;
            case "R":
                direction = Direction.RETURN;
                break;
        }

        StationFetcher stationFetcher = null;

        switch (mode.toUpperCase()) {
            case "BUS":
                stationFetcher = new BusStationFetcher(line, direction);
                break;
            case "METRO":
                stationFetcher = new MetroStationFetcher(line, direction);
                break;
            case "TRAM":
                stationFetcher = new TramStationFetcher(line, direction);
                break;
            case "RER":
                stationFetcher = new RERStationFetcher(line, direction);
                break;
        }

        ArrayList<Station> stations = stationFetcher.getAllStations();

        updateStationSpinner(stations);
    }

    @UiThread
    void updateStationSpinner(ArrayList<Station> stations) {
        stationSpinnerAdapter.setStations(stations);
        stationSpinner.setAdapter(stationSpinnerAdapter);
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

        Log.d("SouriTP", "Sauvegarde de la route");

        try {

            DaoRoute route = new DaoRoute(routeName);
            routeDao.create(route);

            for (Station station : stations) {
                DaoStation dao_station = new DaoStation(station.getName(), station.getStationId(), station.getLine().getLineId(), station.getLine().getTerminus().getTerminusId(), station.getLine().getTerminus().getName(), station.getLine().getMode());
                dao_station.setRoute(route);
                stationDao.create(dao_station);
            }

            finish();
        }
        catch (SQLException ex){
            Log.d("SouriTP", "error");
        }
    }
}