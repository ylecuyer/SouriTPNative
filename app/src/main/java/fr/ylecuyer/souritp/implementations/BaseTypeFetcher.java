package fr.ylecuyer.souritp.implementations;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.DAO.Type;
import fr.ylecuyer.souritp.interfaces.TerminusFetcher;
import fr.ylecuyer.souritp.interfaces.TypeFetcher;

public class BaseTypeFetcher implements TypeFetcher {

    public BaseTypeFetcher() {
    }

    @Override
    public ArrayList<Type> getAllTypes() {
        throw new UnsupportedOperationException();
    }
}
