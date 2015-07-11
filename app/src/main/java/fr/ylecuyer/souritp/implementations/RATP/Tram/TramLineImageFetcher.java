package fr.ylecuyer.souritp.implementations.RATP.Tram;

import android.graphics.drawable.Drawable;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.implementations.BaseLineImageFetcher;

/**
 * Created by ylecuyer on 11/07/15.
 */
public class TramLineImageFetcher extends BaseLineImageFetcher {
    public TramLineImageFetcher(Line line) {
        super(line);
    }

    @Override
    public String getLineImageURL() {
        return "http://www.ratp.fr/horaires/images/lines/tramway/T"+line.getLineId().toLowerCase()+".png";
    }
}
