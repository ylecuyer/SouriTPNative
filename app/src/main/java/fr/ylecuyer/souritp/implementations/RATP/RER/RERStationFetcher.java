package fr.ylecuyer.souritp.implementations.RATP.RER;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import fr.ylecuyer.souritp.BuildConfig;
import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.BaseStationFetcher;

public class RERStationFetcher extends BaseStationFetcher {

    public RERStationFetcher(Line line, Terminus terminus) {
        super(line, terminus);
    }

    @Override
    public ArrayList<Station> getAllStations() {

        String url = "http://wap.ratp.fr/siv/schedule?service=next&reseau=rer&lineid=R"+line.getLineId()+"&directionsens="+terminus.getTerminusId()+"&referer=station&stationname=*";

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

            if (BuildConfig.DEBUG)
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
