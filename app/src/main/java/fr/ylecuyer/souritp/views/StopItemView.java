package fr.ylecuyer.souritp.views;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import fr.ylecuyer.souritp.BuildConfig;
import fr.ylecuyer.souritp.DAO.DaoRoute;
import fr.ylecuyer.souritp.DAO.Stop;
import fr.ylecuyer.souritp.R;
import fr.ylecuyer.souritp.factories.LineImageFetcherFactory;
import fr.ylecuyer.souritp.interfaces.LineImageFetcher;

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

    public void bind(Stop stop, boolean displayStation) {

        stopTextView.setText(stop.getWaitTime());

        String mode = stop.getLine().getType().getTypeId();

        if (displayStation && !stop.getTerminus().isEmpty() && !mode.equalsIgnoreCase("BUS")) {
            terminusTextView.setText(stop.getStation().getName() + " - " + stop.getTerminus());
        } else {
            terminusTextView.setText(stop.getTerminus());
        }

        LineImageFetcher lineImageFetcher = LineImageFetcherFactory.getLineImageFetcher(stop.getLine().getType().getTypeCode(), mode, stop.getLine());

        Picasso.with(getContext()).load(lineImageFetcher.getLineImageURL()).into(imageView);

    }
}
