package fr.ylecuyer.souritp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import fr.ylecuyer.souritp.BuildConfig;
import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.DAO.Type;
import fr.ylecuyer.souritp.EditTextWithButton;
import fr.ylecuyer.souritp.R;
import fr.ylecuyer.souritp.factories.LineCheckerFactory;
import fr.ylecuyer.souritp.factories.StationFetcherFactory;
import fr.ylecuyer.souritp.factories.TerminusFetcherFactory;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusLineChecker;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusStationFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Bus.BusTerminusFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroLineChecker;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroStationFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Metro.MetroTerminusFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERLineChecker;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERStationFetcher;
import fr.ylecuyer.souritp.implementations.RATP.RER.RERTerminusFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramLineChecker;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramStationFetcher;
import fr.ylecuyer.souritp.implementations.RATP.Tram.TramTerminusFetcher;
import fr.ylecuyer.souritp.interfaces.LineChecker;
import fr.ylecuyer.souritp.interfaces.StationFetcher;
import fr.ylecuyer.souritp.interfaces.TerminusFetcher;
import fr.ylecuyer.souritp.views.StationSpinnerAdapter;
import fr.ylecuyer.souritp.views.TerminusSpinnerAdapter;

@EActivity(R.layout.activity_ratp_station_selection)
public class RATPStationSelectionActivity extends Activity implements EditTextWithButton.ButtonListener, AdapterView.OnItemSelectedListener {

    @ViewById
    EditTextWithButton lineText;

    @ViewById
    Spinner modeSpinner;

    @ViewById
    Spinner terminusSpinner;

    @ViewById
    Spinner stationSpinner;

    @Bean
    StationSpinnerAdapter stationSpinnerAdapter;

    @Bean
    TerminusSpinnerAdapter terminusSpinnerAdapter;

    @AfterViews
    void addListener() {
        lineText.setButtonListener(this);
        terminusSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void buttonClicked() {
        updateDirections();
    }

    @Background
    public void updateDirections() {

        if (BuildConfig.DEBUG)
            Log.d("SouriTP", "Updating directions");

        if (!isNetworkAvailable()) {
            displayNetworkAlert();
            return;
        }

        String mode = (String) modeSpinner.getSelectedItem();

        String lineId = lineText.getText().toString();

        Type type = new Type(mode.toUpperCase(), mode, "RATP");

        Line line = new Line(lineId, null, type);

        LineChecker lineChecker = LineCheckerFactory.getLineChecker("RATP", mode, line);

        if (!lineChecker.isValid()) {
            displayLineError();
            return;
        }

        ArrayList<Terminus> terminuses = new ArrayList<Terminus>();

        TerminusFetcher terminusFetcher = TerminusFetcherFactory.getTerminusFetcher("RATP", mode, line);

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

        String mode = (String) modeSpinner.getSelectedItem();

        if (BuildConfig.DEBUG)
            Log.d("SouriTP", "mode: " + mode);

        String lineId = lineText.getText().toString();

        if (BuildConfig.DEBUG)
            Log.d("SouriTP", "line: " + lineId);

        Terminus terminus = (Terminus)terminusSpinner.getSelectedItem();

        if (BuildConfig.DEBUG)
            Log.d("SouriTP", "terminus: " + terminus);

        Type type = new Type(mode.toUpperCase(), mode, "RATP");
        Line line = new Line(lineId, terminus, type);

        StationFetcher stationFetcher = StationFetcherFactory.getStationFetcher("RATP", mode, line, terminus);

        ArrayList<Station> stations = stationFetcher.getAllStations();

        updateStationSpinner(stations);
    }

    @UiThread
    void updateStationSpinner(ArrayList<Station> stations) {
        stationSpinnerAdapter.setStations(stations);
        stationSpinner.setAdapter(stationSpinnerAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateStations();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    @Click
    void add() {
        Intent data = new Intent();
        data.putExtra("station", (Station)stationSpinner.getSelectedItem());
        setResult(RESULT_OK, data);
        finish();
    }
}
