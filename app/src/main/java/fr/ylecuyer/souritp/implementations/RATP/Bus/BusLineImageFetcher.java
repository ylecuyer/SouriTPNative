package fr.ylecuyer.souritp.implementations.RATP.Bus;

import android.graphics.drawable.Drawable;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.implementations.BaseLineImageFetcher;

/**
 * Created by ylecuyer on 11/07/15.
 */
public class BusLineImageFetcher extends BaseLineImageFetcher {
    public BusLineImageFetcher(Line line) {
        super(line);
    }

    @Override
    public String getLineImageURL() {
        return "http://www.ratp.fr/informer/picts/moteur/ligne/b"+line.getLineId()+".gif";
    }
}
