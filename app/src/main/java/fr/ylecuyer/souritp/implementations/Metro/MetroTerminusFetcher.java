package fr.ylecuyer.souritp.implementations.Metro;

import com.github.kevinsawicki.http.HttpRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.BaseTerminusFetcher;

public class MetroTerminusFetcher extends BaseTerminusFetcher {

    public MetroTerminusFetcher(Line line) {
        super(line);
    }

    @Override
    public ArrayList<Terminus> getAllTerminuses() {

        String url = "http://wap.ratp.fr/siv/schedule?service=next&reseau=metro&linecode="+line.getLineId()+"&referer=line";

        String html = HttpRequest.get(url).body();

        Document doc = Jsoup.parse(html);

        Elements elements = doc.select(".bg1");

        ArrayList<Terminus> terminuses = new ArrayList<Terminus>();

        for (Element element : elements) {

            Element link = element.select("a").first();
            String href = link.attr("href");

            String name = link.ownText();
            String terminusId = extractTerminusId(href);

            Terminus terminus = new Terminus(name, terminusId);

            terminuses.add(terminus);
        }

        return terminuses;
    }

    private String extractTerminusId(String href) {
        return href.substring(href.length() - 1);
    }

}
