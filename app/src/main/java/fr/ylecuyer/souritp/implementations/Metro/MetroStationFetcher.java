package fr.ylecuyer.souritp.implementations.Metro;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.implementations.BaseStationFetcher;
import fr.ylecuyer.souritp.interfaces.Direction;

public class MetroStationFetcher extends BaseStationFetcher {

    public MetroStationFetcher(Line line, Direction direction) {
        super(line, direction);

        if (!line.getLineId().toLowerCase().matches("[1-2]|3b?|[4-6]|7b?|[8-9]|1[0-4]"))
            throw new UnsupportedOperationException("Contact yoann.lecuyer@gmail.com");
    }

    @Override
    public ArrayList<Station> getAllStations() {

        String url = "http://wap.ratp.fr/siv/schedule?service=next&reseau=metro&lineid=M"+line.getLineId().toUpperCase()+"&directionsens="+direction.getValue()+"&referer=station&stationname=*";

        String html = HttpRequest.get(url).body();

        Document doc = Jsoup.parse(html);

        Elements elements = doc.select(".bg1");

        ArrayList<Station> stations = new ArrayList<Station>();

        for (Element element : elements) {

            Element link = element.select("a").first();
            String href = link.attr("href");

            String stationName = clean(link.ownText());
            String stationId = extractStationId(href);

            Station station = new Station(stationName, stationId, line);

            stations.add(station);

            Log.d("SouriTP", station.toString());
        }


        return stations;
    }

    private String extractStationId(String href) {
        return href.substring(href.lastIndexOf('=')+1);
    }

    private String clean(String s) {
        return s.substring(" > ".length()-1);
    }
}
