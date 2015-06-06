package fr.ylecuyer.souritp.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Terminus;

@EBean
public class TerminusSpinnerAdapter extends BaseAdapter {

    List<Terminus> terminuses;

    @RootContext
    Context context;

    public void setTerminuses(List<Terminus> terminuses) {
        this.terminuses = terminuses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TerminusSpinnerItemView terminusSpinnerItemView;
        if (convertView == null) {
            terminusSpinnerItemView = TerminusSpinnerItemView_.build(context);
        } else {
            terminusSpinnerItemView = (TerminusSpinnerItemView) convertView;
        }

        terminusSpinnerItemView.bind(getItem(position));

        return terminusSpinnerItemView;
    }

    @Override
    public int getCount() {
        return terminuses.size();
    }

    @Override
    public Terminus getItem(int position) {
        return terminuses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}