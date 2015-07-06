package fr.ylecuyer.souritp.implementations.Tram;

import com.github.kevinsawicki.http.HttpRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.BaseLineChecker;
import fr.ylecuyer.souritp.implementations.BaseTerminusFetcher;

public class TramLineChecker extends BaseLineChecker {

    public TramLineChecker(Line line) {
        super(line);
    }

    @Override
    public boolean isValid() {
        return line.getLineId().toLowerCase().matches("[1-2]|3[ab]|[4-8]");
    }

}
