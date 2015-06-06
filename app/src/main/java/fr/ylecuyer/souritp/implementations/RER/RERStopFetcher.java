package fr.ylecuyer.souritp.implementations.RER;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;

import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Stop;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.interfaces.Direction;
import fr.ylecuyer.souritp.implementations.BaseStopFetcher;


public class RERStopFetcher extends BaseStopFetcher {

    public RERStopFetcher(Station station, Terminus terminus) {
        super(station, terminus);
    }

    @Override
    public ArrayList<Stop> nextStops() {

        String url = "http://www.ratp.fr/horaires/fr/ratp/rer/prochains_passages/R"+station.getLine().getLineId()+"/"+station.getName()+"/"+terminus.getTerminusId();

        url = HttpRequest.encode(url);

        String html = HttpRequest.get(url).body();

        Document doc = Jsoup.parse(html);

        Elements elements = doc.select("#prochains_passages tbody tr");

        ArrayList<Stop> stops = new ArrayList<Stop>();

        LocalTime now = new LocalTime();

        for (Element element : elements) {

            Elements td = element.select("td");

            String terminus = td.get(1).ownText();
            String ETA = td.last().ownText();

            String waitTime = "";

            try {
                LocalTime time = LocalTime.parse(ETA);
                waitTime = Minutes.minutesBetween(now, time).getMinutes() + " mn";
            }
            catch (IllegalArgumentException ex) {
                waitTime = ETA;
            }


            Stop stop = new Stop(terminus, waitTime);

            Log.d("SouriTP", stop.toString());

            stops.add(stop);
        }

        return stops;
    }
}
