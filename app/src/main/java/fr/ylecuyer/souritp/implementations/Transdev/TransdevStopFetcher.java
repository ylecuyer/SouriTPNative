package fr.ylecuyer.souritp.implementations.Transdev;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.reader.internal.DefaultCSVEntryParser;

import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import fr.ylecuyer.souritp.BuildConfig;
import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Station;
import fr.ylecuyer.souritp.DAO.Stop;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.DAO.Type;
import fr.ylecuyer.souritp.implementations.BaseStopFetcher;

public class TransdevStopFetcher extends BaseStopFetcher {

    public TransdevStopFetcher(Station station, Terminus terminus, Line line) {
        super(station, terminus, line);
    }

    @Override
    public ArrayList<Stop> nextStops() {

        String url = "http://www.transdev-idf.com/ip/next_bus/" + line.getType().getMode()+"-"+line.getLineId() + "/" + station.getStationId() + "/" + terminus.getTerminusId();

        url = HttpRequest.encode(url);

        String csv = HttpRequest.get(url).body();

        Reader csvReader = new StringReader(csv);

        CSVStrategy strategy = new CSVStrategy('|', '"', '#', false, true);

        CSVReader<String[]> csvParser = new CSVReaderBuilder<String[]>(csvReader).entryParser(new DefaultCSVEntryParser()).strategy(strategy).build();

        String data[] = null;
        ArrayList<Stop> stops = new ArrayList<Stop>();

        try {
            while( (data = csvParser.readNext()) != null) {

                String waitTime = Integer.parseInt(data[1])/60 + " mn";

                Stop stop = new Stop(terminus.getName(), waitTime, station.getLine(), station);
                stops.add(stop);
                if (BuildConfig.DEBUG)
                    Log.d("SouriTP", station.toString());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = stops.size(); i < 2; i++) {
            Stop stop = new Stop(terminus.getName(), "SERVICE TERMINE", station.getLine(), station);
            stops.add(stop);
        }

        return stops;
    }

}
