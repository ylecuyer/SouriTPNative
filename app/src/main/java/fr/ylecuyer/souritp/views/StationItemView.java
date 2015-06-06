package fr.ylecuyer.souritp.views;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.R;

@EViewGroup(R.layout.station_item)
public class StationItemView extends LinearLayout {

    @ViewById
    TextView stationName;

    @ViewById
    TextView terminusName;

    @ViewById
    ImageView modeImageView;

    @ViewById
    ImageView lineImageView;

    public StationItemView(Context context) {
        super(context);
    }

    public void bind(Station station) {

        stationName.setText(station.getName());
        terminusName.setText(station.getLine().getTerminus().getName());

        switch (station.getLine().getMode().toUpperCase()) {
            case "BUS":
                modeImageView.setImageResource(R.drawable.bus);
                lineImageView.setImageResource(getResources().getIdentifier("b"+station.getLine().getLineId(), "drawable", getContext().getPackageName()));
                break;
            case "METRO":
                modeImageView.setImageResource(R.drawable.metro);
                lineImageView.setImageResource(getResources().getIdentifier("m"+station.getLine().getLineId().toLowerCase()+"_t", "drawable", getContext().getPackageName()));
                break;
            case "RER":
                modeImageView.setImageResource(R.drawable.rer);
                lineImageView.setImageResource(getResources().getIdentifier("p_rer_"+station.getLine().getLineId().toLowerCase()+"_1", "drawable", getContext().getPackageName()));
                break;
            case "TRAM":
                modeImageView.setImageResource(R.drawable.tram);
                lineImageView.setImageResource(getResources().getIdentifier("tram_t"+station.getLine().getLineId().toLowerCase()+"_1", "drawable", getContext().getPackageName()));
                break;
        }

    }

}
