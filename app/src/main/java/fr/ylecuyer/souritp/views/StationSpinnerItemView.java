package fr.ylecuyer.souritp.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.R;

@EViewGroup(android.R.layout.simple_spinner_dropdown_item)
public class StationSpinnerItemView extends LinearLayout {

    @ViewById(android.R.id.text1)
    TextView stationName;

    public StationSpinnerItemView(Context context) {
        super(context);
    }

    public void bind(Station station) {
        stationName.setText(station.getName());
    }

}
