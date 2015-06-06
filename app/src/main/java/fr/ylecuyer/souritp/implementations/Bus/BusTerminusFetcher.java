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
import fr.ylecuyer.souritp.DAO.Stop;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.BaseStopFetcher;
import fr.ylecuyer.souritp.implementations.BaseTerminusFetcher;
import fr.ylecuyer.souritp.interfaces.Direction;

public class BusTerminusFetcher extends BaseTerminusFetcher {

    public BusTerminusFetcher(Line line) {
        super(line);
    }

    @Override
    public ArrayList<Terminus> getAllTerminuses() {

        String url = "http://wap.ratp.fr/siv/schedule?referer=line&service=next&reseau=bus&stationname=&linecode="+line.getLineId()+"&submitAction=Valider";

        String html = HttpRequest.get(url).body();

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
