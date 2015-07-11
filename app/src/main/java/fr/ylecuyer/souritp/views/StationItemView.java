package fr.ylecuyer.souritp.views;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.R;
import fr.ylecuyer.souritp.factories.LineImageFetcherFactory;
import fr.ylecuyer.souritp.factories.ModeLogoFetcherFactory;
import fr.ylecuyer.souritp.interfaces.LineImageFetcher;
import fr.ylecuyer.souritp.interfaces.ModeLogoFetcher;

@EViewGroup(R.layout.station_item)
public class StationItemView extends LinearLayout {

    @ViewById
    TextView stationName;

    @ViewById
    TextView terminusName;

    @ViewById
    ImageView modeImageView;

    @ViewById
    ImageView lineImageView;

    public StationItemView(Context context) {
        super(context);
    }

    public void bind(Station station) {

        stationName.setText(station.getName());
        terminusName.setText(station.getLine().getTerminus().getName());


        ModeLogoFetcher modeLogoFetcher = ModeLogoFetcherFactory.getModeLogoFetcher(station.getLine().getType().getTypeCode(), station.getLine().getType().getTypeId());
        modeImageView.setImageResource(modeLogoFetcher.getModeLogoDrawableId());

        LineImageFetcher lineImageFetcher = LineImageFetcherFactory.getLineImageFetcher(station.getLine().getType().getTypeCode(), station.getLine().getType().getTypeId(), station.getLine());
        Picasso.with(getContext()).load(lineImageFetcher.getLineImageURL()).into(lineImageView);

    }

}
