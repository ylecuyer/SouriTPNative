package fr.ylecuyer.souritp.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import fr.ylecuyer.souritp.DAO.DaoRoute;
import fr.ylecuyer.souritp.DAO.Stop;
import fr.ylecuyer.souritp.R;

@EViewGroup(R.layout.stop_item)
public class StopItemView extends LinearLayout {

    @ViewById
    TextView stopTextView;

    @ViewById
    TextView terminusTextView;

    public StopItemView(Context context) {
        super(context);
    }

    public void bind(Stop stop) {
        stopTextView.setText(stop.getWaitTime());
        terminusTextView.setText(stop.getTerminus());
    }

}
