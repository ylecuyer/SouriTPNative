package fr.ylecuyer.souritp.implementations.Transdev;

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

import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.BaseTerminusFetcher;

public class TransdevTerminusFetcher extends BaseTerminusFetcher {

    public TransdevTerminusFetcher(Line line) {
        super(line);
    }

    @Override
    public ArrayList<Terminus> getAllTerminuses() {

        String url = "http://www.transdev-idf.com/ip/lines_of_linegroup_2/"+line.getType().getTypeId();

        String csv = HttpRequest.get(url).body();

        Reader csvReader = new StringReader(csv);

        CSVStrategy strategy = new CSVStrategy('|', '"', '#', false, true);

        CSVReader<String[]> csvParser = new CSVReaderBuilder<String[]>(csvReader).entryParser(new DefaultCSVEntryParser()).strategy(strategy).build();

        String data[] = null;
        ArrayList<Terminus> terminuses = new ArrayList<Terminus>();

        try {
            while( (data = csvParser.readNext()) != null) {
                if (data[4].equalsIgnoreCase(line.getLineId())) {
                    break;
                }
            }


            Terminus terminus = new Terminus(data[5], "0");
            terminuses.add(terminus);

            if (data[9].equalsIgnoreCase("2")) {
                terminus = new Terminus(data[7], "1");
            }
            else {
                terminus = new Terminus(data[7], "0");
            }
            terminuses.add(terminus);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return terminuses;
    }

    private String extractTerminusId(String href) {
        return href.substring(href.length() - 1);
    }

}
