package fr.ylecuyer.souritp.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.RootContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.ylecuyer.souritp.DAO.DaoRoute;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.database.DatabaseHelper;

@EBean
public class StationListAdapter extends BaseAdapter {

    List<Station> stations;

    @RootContext
    Context context;

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public void remove(int position) {
        stations.remove(position);
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StationItemView stationItemView;
        if (convertView == null) {
            stationItemView = StationItemView_.build(context);
        } else {
            stationItemView = (StationItemView) convertView;
        }

        stationItemView.bind(getItem(position));

        return stationItemView;
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

    @Override
    public boolean isEmpty() {
        return false;
    }
}