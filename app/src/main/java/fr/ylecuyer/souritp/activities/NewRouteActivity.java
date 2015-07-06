package fr.ylecuyer.souritp.activities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.Timer;
import java.util.TimerTask;

import de.timroes.android.listview.EnhancedListView;
import fr.ylecuyer.souritp.BuildConfig;
import fr.ylecuyer.souritp.DAO.DaoRoute;
import fr.ylecuyer.souritp.DAO.DaoStation;
import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.R;
import fr.ylecuyer.souritp.database.DatabaseHelper;
import fr.ylecuyer.souritp.implementations.Bus.BusLineChecker;
import fr.ylecuyer.souritp.implementations.Bus.BusStationFetcher;
import fr.ylecuyer.souritp.implementations.Bus.BusTerminusFetcher;
import fr.ylecuyer.souritp.implementations.Metro.MetroLineChecker;
import fr.ylecuyer.souritp.implementations.Metro.MetroStationFetcher;
import fr.ylecuyer.souritp.implementations.Metro.MetroTerminusFetcher;
import fr.ylecuyer.souritp.implementations.RER.RERLineChecker;
import fr.ylecuyer.souritp.implementations.RER.RERStationFetcher;
import fr.ylecuyer.souritp.implementations.RER.RERTerminusFetcher;
import fr.ylecuyer.souritp.implementations.Tram.TramLineChecker;
import fr.ylecuyer.souritp.implementations.Tram.TramStationFetcher;
import fr.ylecuyer.souritp.implementations.Tram.TramTerminusFetcher;
import fr.ylecuyer.souritp.interfaces.Direction;
import fr.ylecuyer.souritp.interfaces.LineChecker;
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
        boolean wrapInScrollView = true;
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .cancelable(false)
                .title("Add new route")
                .customView(R.layout.dialog_add_to_route, wrapInScrollView)
                .positiveText("Add")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        if (BuildConfig.DEBUG)
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

        lineText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            private Timer timer = new Timer();
            private final long DELAY = 1000;//ms

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timer.cancel();

                if (s.length() != 0) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            updateDirections();
                        }
                    }, DELAY);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.show();
    }

    @Background
    public void updateDirections() {

        if (BuildConfig.DEBUG)
            Log.d("SouriTP", "Updating directions");

        if (!isNetworkAvailable()) {
            displayNetworkAlert();
            return;
        }

        String mode = (String)modeSpinner.getSelectedItem();

        String lineId = lineText.getText().toString();

        Line line = new Line(lineId, null, mode.toUpperCase());

        LineChecker lineChecker = null;

        switch (mode.toUpperCase()) {
            case "BUS":
                lineChecker = new BusLineChecker(line);
                break;
            case "METRO":
                lineChecker = new MetroLineChecker(line);
                break;
            case "TRAM":
                lineChecker = new TramLineChecker(line);
                break;
            case "RER":
                lineChecker = new RERLineChecker(line);
                break;
        }

        if (!lineChecker.isValid()) {
            displayLineError();
            return;
        }

        ArrayList<Terminus> terminuses = new ArrayList<Terminus>();

        TerminusFetcher terminusFetcher = null;

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

        if (BuildConfig.DEBUG)
            Log.d("SouriTP", "mode: " + mode);

        String lineId = lineText.getText().toString();

        if (BuildConfig.DEBUG)
            Log.d("SouriTP", "line: " + lineId);

        Terminus terminus = (Terminus)terminusSpinner.getSelectedItem();

        if (BuildConfig.DEBUG)
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

        if (BuildConfig.DEBUG)
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
            ex.printStackTrace();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    @UiThread
    void displayNetworkAlert() {
        new MaterialDialog.Builder(this)
                .title("Error")
                .content("You must have internet active to use the app")
                .positiveText("OK")
                .show();
    }

    @UiThread
    void displayLineError() {
        new MaterialDialog.Builder(this)
                .title("Error")
                .content("This line is not valid")
                .positiveText("OK")
                .show();
    }
}
