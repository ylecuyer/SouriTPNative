package fr.ylecuyer.souritp.implementations.RATP.Metro;

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
import fr.ylecuyer.souritp.DAO.Stop;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.DAO.Type;
import fr.ylecuyer.souritp.implementations.BaseStopFetcher;

public class MetroStopFetcher extends BaseStopFetcher {

    public MetroStopFetcher(Station station, Terminus terminus, Line line) {
        super(station, terminus, line);
    }

    @Override
    public ArrayList<Stop> nextStops() {

        String url = "http://www.ratp.fr/horaires/fr/ratp/metro/prochains_passages/PP/"+station.getName()+"/"+station.getLine().getLineId()+"/"+terminus.getTerminusId();

        url = HttpRequest.encode(url);

        String html = HttpRequest.get(url).body();

        Document doc = Jsoup.parse(html);

        Elements elements = doc.select("#prochains_passages tbody tr");

        ArrayList<Stop> stops = new ArrayList<Stop>();

        Type type = new Type("METRO", "Metro", "RATP");
        Line lineb = new Line(line.getLineId(), terminus, type);

        for (Element element : elements) {

            Elements td = element.select("td");

            String terminus = td.first().ownText();
            String waitTime = td.last().ownText();

            Stop stop = new Stop(terminus, waitTime, lineb, station);

            if (BuildConfig.DEBUG)
                Log.d("SouriTP", stop.toString());

            stops.add(stop);
        }

        return stops;
    }
}
