package fr.ylecuyer.souritp.implementations.RATP.Tram;

import android.graphics.drawable.Drawable;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.R;
import fr.ylecuyer.souritp.implementations.BaseLineImageFetcher;
import fr.ylecuyer.souritp.implementations.BaseModeLogoFetcher;

/**
 * Created by ylecuyer on 11/07/15.
 */
public class TramModeLogoFetcher extends BaseModeLogoFetcher {
    public TramModeLogoFetcher() {
        super();
    }

    @Override
    public int getModeLogoDrawableId() {
        return R.drawable.tram;
    }
}
