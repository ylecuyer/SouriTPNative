package fr.ylecuyer.souritp.implementations.RATP.Bus;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

import fr.ylecuyer.souritp.BuildConfig;
import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.BaseTerminusFetcher;

public class BusTerminusFetcher extends BaseTerminusFetcher {

    public BusTerminusFetcher(Line line) {
        super(line);
    }

    @Override
    public ArrayList<Terminus> getAllTerminuses() {

        String url = "http://wap.ratp.fr/siv/schedule?referer=line&service=next&reseau=bus&stationname=&linecode="+line.getLineId()+"&submitAction=Valider";

        String html = HttpRequest.get(url).body();

        if (BuildConfig.DEBUG)
            Log.d("SouriTP", html);

        Document doc = Jsoup.parse(html);

        Element element = doc.getElementsByClass("bwhite").get(1);

        ArrayList<Terminus> terminuses = new ArrayList<Terminus>();

        String[] terminusesString = element.ownText().split(" / ");

        Terminus terminusA = new Terminus(terminusesString[0], "A");
        Terminus terminusR = new Terminus(terminusesString[1], "R");

        terminuses.add(terminusA);
        terminuses.add(terminusR);

        return terminuses;
    }
}
