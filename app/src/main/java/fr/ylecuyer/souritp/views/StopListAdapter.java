package fr.ylecuyer.souritp.views;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import fr.ylecuyer.souritp.DAO.DaoRoute;
import fr.ylecuyer.souritp.DAO.Stop;

@EBean
public class StopListAdapter extends BaseAdapter {

    List<Stop> stops = new ArrayList<Stop>();

    private boolean displayStation = false;

    @RootContext
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StopItemView stopItemView;
        if (convertView == null) {
            stopItemView = StopItemView_.build(context);
        } else {
            stopItemView = (StopItemView) convertView;
        }

        stopItemView.bind(getItem(position), displayStation);

        return stopItemView;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;

        displayStation = false;

        int nb_stops = stops.size();

        outerLoop:
        for (int i = 0; i < nb_stops; i++) {
            Stop stopi = stops.get(i);
            for (int j = i+1; j < nb_stops; j++) {
                Stop stopj = stops.get(j);

                String stopi_mode = stopi.getLine().getType().getTypeId();
                String stopi_line = stopi.getLine().getLineId();
                String stopi_stationId = stopi.getStation().getStationId();

                String stopj_mode = stopj.getLine().getType().getTypeId();
                String stopj_line = stopj.getLine().getLineId();
                String stopj_stationId = stopj.getStation().getStationId();

                if (stopi_mode.equalsIgnoreCase(stopj_mode) && stopi_line.equalsIgnoreCase(stopj_line) && !stopi_stationId.equalsIgnoreCase(stopj_stationId)) {
                    displayStation = true;
                    break outerLoop;
                }
            }

            Log.d("SouriTP",  ""+displayStation);
        }

        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return stops.size();
    }

    @Override
    public Stop getItem(int position) {
        return stops.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}