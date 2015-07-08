package fr.ylecuyer.souritp.implementations.Transdev;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

import java.io.BufferedInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import fr.ylecuyer.souritp.DAO.Type;
import fr.ylecuyer.souritp.TransdevTypeEntryParser;
import fr.ylecuyer.souritp.implementations.BaseTypeFetcher;

public class TransdevTypeFetcher extends BaseTypeFetcher {

    @Override
    public ArrayList<Type> getAllTypes() {

        String url = "http://www.transdev-idf.com/ip/linegroups";

        String csv = HttpRequest.get(url).body();

        Reader csvReader = new StringReader(csv);

        CSVStrategy strategy = new CSVStrategy('|', '"', '#', false, true);

        CSVReader<Type> typeReader = new CSVReaderBuilder<Type>(csvReader).entryParser(new TransdevTypeEntryParser()).strategy(strategy).build();

        ArrayList<Type> types = new ArrayList<Type>();

        try {
            types = (ArrayList<Type>)typeReader.readAll();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return types;
    }
}
