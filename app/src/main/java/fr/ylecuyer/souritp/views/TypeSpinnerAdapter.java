package fr.ylecuyer.souritp.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.DAO.Type;

@EBean
public class TypeSpinnerAdapter extends BaseAdapter {

    List<Type> types;

    @RootContext
    Context context;

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TypeSpinnerItemView typeSpinnerItemView;
        if (convertView == null) {
            typeSpinnerItemView = TypeSpinnerItemView_.build(context);
        } else {
            typeSpinnerItemView = (TypeSpinnerItemView) convertView;
        }

        typeSpinnerItemView.bind(getItem(position));

        return typeSpinnerItemView;
    }

    @Override
    public int getCount() {
        return types.size();
    }

    @Override
    public Type getItem(int position) {
        return types.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}