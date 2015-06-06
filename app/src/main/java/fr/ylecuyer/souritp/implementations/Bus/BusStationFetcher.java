package fr.ylecuyer.souritp.implementations.Bus;

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
import fr.ylecuyer.souritp.interfaces.StopFetcher;

public class BusStationFetcher extends BaseStationFetcher {

    public BusStationFetcher(Line line, Direction direction) {
        super(line, direction);

        //if (lineId.matches("100|13[06]|14[29]|155|19[38]|20[024-59]|21[8-9]|22[2-9]|23[1-3]|24[2-35-6]|25[79]|26[03-6]|27[137]|28[0247-8]|29[68]|30[59]|31[13-6]|32[46-9]|33[1-25-68-9]|34[2-5]|35[39]|36[24-59]|37[13-7]|38[0-56-7]|39[7-8]|40\\d|41\\d|42[02-57-9]|43\\d|44\\d|45[0-8]|46[1-68]|47[02-9]|48[0-49]|"))
        //    throw new UnsupportedOperationException("Contact yoann.lecuyer@gmail.com");
    }

    @Override
    public ArrayList<Station> getAllStations() {

        String url = "http://wap.ratp.fr/siv/schedule?service=next&reseau=bus&lineid=B"+line.getLineId()+"&referer=station&stationname=*";

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
