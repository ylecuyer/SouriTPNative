package fr.ylecuyer.souritp.implementations;

import android.graphics.drawable.Drawable;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.interfaces.LineImageFetcher;
import fr.ylecuyer.souritp.interfaces.ModeLogoFetcher;

public class BaseModeLogoFetcher implements ModeLogoFetcher {

    public BaseModeLogoFetcher() {
    }

    @Override
    public int getModeLogoDrawableId() {
        throw new UnsupportedOperationException();
    }
}
