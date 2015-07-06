package fr.ylecuyer.souritp.implementations.Metro;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import fr.ylecuyer.souritp.BuildConfig;
import fr.ylecuyer.souritp.DAO.Line;
import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.implementations.BaseLineChecker;
import fr.ylecuyer.souritp.implementations.BaseTerminusFetcher;

public class MetroLineChecker extends BaseLineChecker {

    public MetroLineChecker(Line line) {
        super(line);
    }

    @Override
    public boolean isValid() {
        return line.getLineId().toLowerCase().matches("[1-2]|3b?|[4-6]|7b?|[8-9]|1[0-4]");
    }
}
