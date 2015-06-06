package fr.ylecuyer.souritp.views;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;
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

    @ViewById
    ImageView imageView;

    public StopItemView(Context context) {
        super(context);
    }

    public void bind(Stop stop) {
        stopTextView.setText(stop.getWaitTime());
        terminusTextView.setText(stop.getTerminus());

        switch (stop.getLine().getMode().toUpperCase()) {
            case "BUS":
                imageView.setImageResource(getResources().getIdentifier("b"+stop.getLine().getLineId(), "drawable", getContext().getPackageName()));
                break;
            case "METRO":
                imageView.setImageResource(getResources().getIdentifier("m"+stop.getLine().getLineId().toLowerCase(), "drawable", getContext().getPackageName()));
                break;
            case "RER":
                imageView.setImageResource(getResources().getIdentifier("p_rer_"+stop.getLine().getLineId().toLowerCase()+"_1", "drawable", getContext().getPackageName()));
                break;
            case "TRAM":
                imageView.setImageResource(getResources().getIdentifier("tram_t"+stop.getLine().getLineId().toLowerCase()+"_1", "drawable", getContext().getPackageName()));
                break;
        }    }

}
