package fr.ylecuyer.souritp.implementations.RATP.Metro;

import fr.ylecuyer.souritp.R;
import fr.ylecuyer.souritp.implementations.BaseModeLogoFetcher;

/**
 * Created by ylecuyer on 11/07/15.
 */
public class MetroModeLogoFetcher extends BaseModeLogoFetcher {
    public MetroModeLogoFetcher() {
        super();
    }

    @Override
    public int getModeLogoDrawableId() {
        return R.drawable.metro;
    }
}
