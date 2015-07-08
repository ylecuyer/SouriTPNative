package fr.ylecuyer.souritp.implementations.RATP.Tram;

import com.github.kevinsawicki.http.HttpRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.BaseTerminusFetcher;

public class TramTerminusFetcher extends BaseTerminusFetcher {

    public TramTerminusFetcher(Line line) {
        super(line);
    }

    @Override
    public ArrayList<Terminus> getAllTerminuses() {

        String url = "http://wap.ratp.fr/siv/schedule-tram";

        String html = HttpRequest.get(url).body();

        Document doc = Jsoup.parse(html);

        Elements elements = doc.select(".bg1");

        ArrayList<Terminus> terminuses = new ArrayList<Terminus>();

        int offset = 0;

        switch (line.getLineId().toUpperCase()) {
            case "1":
                offset += 0*2;
                break;
            case "2":
                offset += 1*2;
                break;
            case "3A":
                offset += 2*2;
                break;
            case "3B":
                offset += 3*2;
                break;
            case "5":
                offset += 4*2;
                break;
            case "6":
                offset += 5*2;
                break;
            case "7":
                offset += 6*2;
                break;
            case "8":
                offset += 7*2;
                break;
        }

        for (int i = 0; i < 2; i++, offset++) {

            Element element = elements.get(offset+i);

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
