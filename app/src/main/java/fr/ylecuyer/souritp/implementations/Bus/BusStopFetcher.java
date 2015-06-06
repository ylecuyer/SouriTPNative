package fr.ylecuyer.souritp.implementations.Bus;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Stop;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.interfaces.Direction;
import fr.ylecuyer.souritp.implementations.BaseStopFetcher;

public class BusStopFetcher extends BaseStopFetcher {

    public BusStopFetcher(Station station, Terminus terminus) {
        super(station, terminus);
    }

    @Override
    public ArrayList<Stop> nextStops() {

        String url = "http://www.ratp.fr/horaires/fr/ratp/bus/prochains_passages/PP/B"+station.getLine().getLineId()+"/"+station.getStationId()+"/"+terminus.getTerminusId();

        String html = HttpRequest.get(url).body();

        Document doc = Jsoup.parse(html);

        Elements elements = doc.select("#prochains_passages tbody tr");

        ArrayList<Stop> stops = new ArrayList<Stop>();

        for (Element element : elements) {

            Elements td = element.select("td");

            String terminus = td.first().ownText();
            String waitTime = td.last().ownText();

            Stop stop = new Stop(terminus, waitTime);

            Log.d("SouriTP", stop.toString());

            stops.add(stop);
        }

        return stops;
    }
}