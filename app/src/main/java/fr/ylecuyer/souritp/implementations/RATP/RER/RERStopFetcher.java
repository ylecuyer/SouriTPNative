package fr.ylecuyer.souritp.implementations.RATP.RER;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

import org.joda.time.LocalTime;
import org.joda.time.Minutes;
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


public class RERStopFetcher extends BaseStopFetcher {

    public RERStopFetcher(Station station, Terminus terminus, Line line) {
        super(station, terminus, line);
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

        Type type = new Type("RER", "RER", "RATP");
        Line lineb = new Line(line.getLineId(), terminus, type);

        for (Element element : elements) {

            Elements td = element.select("td");

            String terminus = td.get(1).ownText();
            String ETA = td.last().ownText();

            String waitTime = "";

            try {

                String[] ETAsplitted = ETA.split(" ");
                String timeETAString = ETAsplitted[0];

                LocalTime time = LocalTime.parse(timeETAString);

                int minutes = Minutes.minutesBetween(now, time).getMinutes();

                if (minutes >= 0) {

                    waitTime = minutes + " mn";

                    if (ETAsplitted.length > 1) {
                        waitTime += " (";

                        for (int i = 1; i < ETAsplitted.length; i++) {
                            if (i != 1) {
                                waitTime += " ";
                            }
                            waitTime += ETAsplitted[i];
                        }

                        waitTime += ")";
                    }
                }
                else {
                    waitTime = "SERVICE TERMINE";
                }
            }
            catch (IllegalArgumentException ex) {
                waitTime = ETA;
            }

            Stop stop = new Stop(terminus, waitTime, lineb, station);

            if (BuildConfig.DEBUG)
                Log.d("SouriTP", stop.toString());

            stops.add(stop);
        }

        return stops;
    }
}
