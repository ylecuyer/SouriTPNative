package fr.ylecuyer.souritp.implementations.Transdev;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.reader.internal.DefaultCSVEntryParser;

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
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.BaseStationFetcher;

public class TransdevStationFetcher extends BaseStationFetcher {

    public TransdevStationFetcher(Line line, Terminus terminus) {
        super(line, terminus);
    }

    @Override
    public ArrayList<Station> getAllStations() {

        String url = "http://www.transdev-idf.com/ip/schema/"+line.getType().getMode()+"-"+line.getLineId()+"/"+terminus.getTerminusId();

        String csv = HttpRequest.get(url).body();

        Reader csvReader = new StringReader(csv);

        CSVStrategy strategy = new CSVStrategy('|', '"', '#', false, true);

        CSVReader<String[]> csvParser = new CSVReaderBuilder<String[]>(csvReader).entryParser(new DefaultCSVEntryParser()).strategy(strategy).build();

        String data[] = null;
        ArrayList<Station> stations = new ArrayList<Station>();

        try {
            while( (data = csvParser.readNext()) != null) {
                Station station = new Station(data[1], data[0], line);
                stations.add(station);
                if (BuildConfig.DEBUG)
                    Log.d("SouriTP", station.toString());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
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
