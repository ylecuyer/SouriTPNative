package fr.ylecuyer.souritp.implementations.Bus;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

import fr.ylecuyer.souritp.BuildConfig;
import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.BaseLineChecker;
import fr.ylecuyer.souritp.implementations.BaseTerminusFetcher;

public class BusLineChecker extends BaseLineChecker {

    public BusLineChecker(Line line) {
        super(line);
    }

    @Override
    public boolean isValid() {

        String url = "http://wap.ratp.fr/siv/schedule?referer=line&service=next&reseau=bus&stationname=&linecode="+line.getLineId()+"&submitAction=Valider";

        String html = HttpRequest.get(url).body();

        if (BuildConfig.DEBUG)
            Log.d("SouriTP", html);

        if (html.contains("Ligne non reconnue")) {
            return false;
        }

        return true;
    }
}
