package fr.ylecuyer.souritp.interfaces;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.DAO.Type;

public interface TypeFetcher {
    public ArrayList<Type> getAllTypes();
}
