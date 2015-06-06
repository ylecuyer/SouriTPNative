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
import java.util.List;

import fr.ylecuyer.souritp.DAO.DaoRoute;
import fr.ylecuyer.souritp.database.DatabaseHelper;

@EBean
public class RouteListAdapter extends BaseAdapter {

    List<DaoRoute> routes;

    @RootContext
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RouteItemView routeItemView;
        if (convertView == null) {
            routeItemView = RouteItemView_.build(context);
        } else {
            routeItemView = (RouteItemView) convertView;
        }

        routeItemView.bind(getItem(position));

        return routeItemView;
    }

    public void setRoutes(List<DaoRoute> routes) {
        this.routes = routes;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return routes.size();
    }

    @Override
    public DaoRoute getItem(int position) {
        return routes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }
}