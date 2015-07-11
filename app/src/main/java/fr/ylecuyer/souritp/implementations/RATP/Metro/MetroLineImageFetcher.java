package fr.ylecuyer.souritp.implementations.RATP.Metro;

import android.graphics.drawable.Drawable;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.implementations.BaseLineImageFetcher;

/**
 * Created by ylecuyer on 11/07/15.
 */
public class MetroLineImageFetcher extends BaseLineImageFetcher {
    public MetroLineImageFetcher(Line line) {
        super(line);
    }

    @Override
    public String getLineImageURL() {
        return "http://www.ratp.fr/informer/picts/moteur/ligne/m"+line.getLineId().toLowerCase()+".gif";
    }
}
