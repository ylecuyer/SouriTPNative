package fr.ylecuyer.souritp.implementations.Transdev;

import fr.ylecuyer.souritp.R;
import fr.ylecuyer.souritp.implementations.BaseModeLogoFetcher;

/**
 * Created by ylecuyer on 11/07/15.
 */
public class TransdevModeLogoFetcher extends BaseModeLogoFetcher {
    public TransdevModeLogoFetcher() {
        super();
    }

    @Override
    public int getModeLogoDrawableId() {
        return R.drawable.transdev;
    }
}
