package fr.ylecuyer.souritp.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import fr.ylecuyer.souritp.DAO.Station;

@EBean
public class StationSpinnerAdapter extends BaseAdapter {

    List<Station> stations;

    @RootContext
    Context context;

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StationSpinnerItemView stationSpinnerItemView;
        if (convertView == null) {
            stationSpinnerItemView = StationSpinnerItemView_.build(context);
        } else {
            stationSpinnerItemView = (StationSpinnerItemView) convertView;
        }

        stationSpinnerItemView.bind(getItem(position));

        return stationSpinnerItemView;
    }

    @Override
    public int getCount() {
        return stations.size();
    }

    @Override
    public Station getItem(int position) {
        return stations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}