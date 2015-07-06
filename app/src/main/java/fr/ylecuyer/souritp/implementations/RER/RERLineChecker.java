package fr.ylecuyer.souritp.implementations.RER;

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

public class RERLineChecker extends BaseLineChecker {

    public RERLineChecker(Line line) {
        super(line);
    }

    @Override
    public boolean isValid() {
        return line.getLineId().toLowerCase().matches("a|b");
    }
}
