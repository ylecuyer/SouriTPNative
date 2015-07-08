package fr.ylecuyer.souritp;

import com.googlecode.jcsv.reader.CSVEntryParser;

import fr.ylecuyer.souritp.DAO.Type;

public class TransdevTypeEntryParser implements CSVEntryParser<Type> {

    @Override
    public Type parseEntry(String... data) {
        String typeId = data[0];
        String name = data[1];

        return new Type(typeId, name, "TRANSDEV");
    }
}
