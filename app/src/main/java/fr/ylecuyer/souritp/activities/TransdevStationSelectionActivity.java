package fr.ylecuyer.souritp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import fr.ylecuyer.souritp.implementations.Transdev.TransdevLineChecker;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevStationFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevTerminusFetcher;
import fr.ylecuyer.souritp.implementations.Transdev.TransdevTypeFetcher;
import fr.ylecuyer.souritp.interfaces.LineChecker;
import fr.ylecuyer.souritp.interfaces.StationFetcher;
import fr.ylecuyer.souritp.interfaces.TerminusFetcher;
import fr.ylecuyer.souritp.views.StationSpinnerAdapter;
import fr.ylecuyer.souritp.views.TerminusSpinnerAdapter;
import fr.ylecuyer.souritp.views.TypeSpinnerAdapter;

@EActivity(R.layout.activity_transdev_station_selection)
public class TransdevStationSelectionActivity extends Activity implements EditTextWithButton.ButtonListener, AdapterView.OnItemSelectedListener {

    @ViewById
    Spinner typeSpinner;

    @ViewById
    EditTextWithButton lineText;

    @ViewById
    Spinner terminusSpinner;

    @ViewById
    Spinner stationSpinner;

    @Bean
    StationSpinnerAdapter stationSpinnerAdapter;

    @Bean
    TypeSpinnerAdapter typeSpinnerAdapter;

    @Bean
    TerminusSpinnerAdapter terminusSpinnerAdapter;

    ArrayList<Type> types = new ArrayList<Type>();

    @AfterViews
    void initAdapters() {
        lineText.setButtonListener(this);
        typeSpinnerAdapter.setTypes(types);
        typeSpinner.setAdapter(typeSpinnerAdapter);
        terminusSpinner.setOnItemSelectedListener(this);
    }

    @AfterViews
    @Background
    void updateTypes() {
        TransdevTypeFetcher typeFetcher = new TransdevTypeFetcher();
        types = typeFetcher.getAllTypes();
        updateTypeSpinner(types);
    }

    @UiThread
    void updateTypeSpinner(ArrayList<Type> types) {
        typeSpinnerAdapter.setTypes(types);
        typeSpinnerAdapter.notifyDataSetChanged();
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

        Type type = (Type)typeSpinner.getSelectedItem();

        String lineId = lineText.getText().toString();

        Line line = new Line(lineId, null, type);

        LineChecker lineChecker = new TransdevLineChecker(line);

        if (!lineChecker.isValid()) {
            displayLineError();
            return;
        }

        ArrayList<Terminus> terminuses = new ArrayList<Terminus>();

        TerminusFetcher terminusFetcher = new TransdevTerminusFetcher(line);

        terminuses = terminusFetcher.getAllTerminuses();

        updateDirectionSpinner(terminuses);
    }

    @UiThread
    void updateDirectionSpinner(ArrayList<Terminus> terminuses) {
        terminusSpinnerAdapter.setTerminuses(terminuses);
        terminusSpinnerAdapter.notifyDataSetChanged();
        terminusSpinner.setAdapter(terminusSpinnerAdapter);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateStations();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Background
    public void updateStations() {

        Type type = (Type)typeSpinner.getSelectedItem();

        if (BuildConfig.DEBUG)
            Log.d("SouriTP", "mode: " + type.getTypeId());

        String lineId = lineText.getText().toString();

        if (BuildConfig.DEBUG)
            Log.d("SouriTP", "line: " + lineId);

        Terminus terminus = (Terminus)terminusSpinner.getSelectedItem();

        if (BuildConfig.DEBUG)
            Log.d("SouriTP", "terminus: " + terminus);

        Line line = new Line(lineId, terminus, type);

        StationFetcher stationFetcher = new TransdevStationFetcher(line, terminus);

        ArrayList<Station> stations = stationFetcher.getAllStations();

        updateStationSpinner(stations);
    }

    @UiThread
    void updateStationSpinner(ArrayList<Station> stations) {
        stationSpinnerAdapter.setStations(stations);
        stationSpinner.setAdapter(stationSpinnerAdapter);
    }

    @Click
    void add() {
        Intent data = new Intent();
        data.putExtra("station", (Station)stationSpinner.getSelectedItem());
        setResult(RESULT_OK, data);
        finish();
    }
}
