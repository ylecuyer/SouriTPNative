package fr.ylecuyer.souritp.implementations.RATP.Bus;

import fr.ylecuyer.souritp.R;
import fr.ylecuyer.souritp.implementations.BaseModeLogoFetcher;

/**
 * Created by ylecuyer on 11/07/15.
 */
public class BusModeLogoFetcher extends BaseModeLogoFetcher {
    public BusModeLogoFetcher() {
        super();
    }

    @Override
    public int getModeLogoDrawableId() {
        return R.drawable.bus;
    }
}
