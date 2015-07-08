package fr.ylecuyer.souritp.implementations.Transdev;

import com.github.kevinsawicki.http.HttpRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.implementations.BaseLineChecker;

public class TransdevLineChecker extends BaseLineChecker {

    public TransdevLineChecker(Line line) {
        super(line);
    }

    @Override
    public boolean isValid() {
        String url = "http://www.transdev-idf.com/ip/schema/"+line.getType().getTypeId()+"-"+line.getLineId()+"/0";

        String html = HttpRequest.get(url).body();

        return !html.isEmpty();
    }

}
