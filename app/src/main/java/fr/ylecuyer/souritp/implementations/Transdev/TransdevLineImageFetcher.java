package fr.ylecuyer.souritp.implementations.Transdev;

import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.implementations.BaseLineImageFetcher;
import fr.ylecuyer.souritp.interfaces.LineImageFetcher;

/**
 * Created by ylecuyer on 11/07/15.
 */
public class TransdevLineImageFetcher extends BaseLineImageFetcher {
    public TransdevLineImageFetcher(Line line) {
        super(line);
    }

    @Override
    public String getLineImageURL() {
        return "http://www.transdev-idf.com/ip/icon/" + line.getType().getMode() + "-" + line.getLineId();
    }
}
