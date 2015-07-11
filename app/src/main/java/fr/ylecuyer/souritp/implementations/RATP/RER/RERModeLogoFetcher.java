package fr.ylecuyer.souritp.implementations.RATP.RER;

import fr.ylecuyer.souritp.R;
import fr.ylecuyer.souritp.implementations.BaseModeLogoFetcher;

/**
 * Created by ylecuyer on 11/07/15.
 */
public class RERModeLogoFetcher extends BaseModeLogoFetcher {
    public RERModeLogoFetcher() {
        super();
    }

    @Override
    public int getModeLogoDrawableId() {
        return R.drawable.rer;
    }
}
