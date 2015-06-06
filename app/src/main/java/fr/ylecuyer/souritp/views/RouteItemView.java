package fr.ylecuyer.souritp.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import fr.ylecuyer.souritp.DAO.DaoRoute;
import fr.ylecuyer.souritp.R;

@EViewGroup(R.layout.route_item)
public class RouteItemView extends LinearLayout {

    @ViewById
    TextView nameTextView;

    public RouteItemView(Context context) {
        super(context);
    }

    public void bind(DaoRoute route) {
        nameTextView.setText(route.getName());
    }

}
