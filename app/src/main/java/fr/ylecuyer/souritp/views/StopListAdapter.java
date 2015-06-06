package fr.ylecuyer.souritp.views;

import android.content.Context;
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

        stopItemView.bind(getItem(position));

        return stopItemView;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
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