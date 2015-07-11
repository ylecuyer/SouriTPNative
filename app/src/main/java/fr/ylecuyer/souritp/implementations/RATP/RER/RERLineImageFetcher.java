package fr.ylecuyer.souritp.implementations.RATP.RER;

import android.graphics.drawable.Drawable;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.implementations.BaseLineImageFetcher;

/**
 * Created by ylecuyer on 11/07/15.
 */
public class RERLineImageFetcher extends BaseLineImageFetcher {
    public RERLineImageFetcher(Line line) {
        super(line);
    }

    @Override
    public String getLineImageURL() {
        return "http://www.ratp.fr/informer/picts/moteur/ligne/p_rer_"+line.getLineId().toLowerCase()+".gif";
    }
}
