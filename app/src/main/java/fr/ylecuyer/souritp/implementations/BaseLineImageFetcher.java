package fr.ylecuyer.souritp.implementations;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Type;
import fr.ylecuyer.souritp.interfaces.LineImageFetcher;
import fr.ylecuyer.souritp.interfaces.TypeFetcher;

public class BaseLineImageFetcher implements LineImageFetcher {

    protected Line line;

    public BaseLineImageFetcher(Line line) {
        this.line = line;
    }

    @Override
    public String getLineImageURL() {
        throw new UnsupportedOperationException();
    }
}
