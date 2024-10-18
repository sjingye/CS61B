package main;

import java.util.List;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private final NGramMap map;
    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }

    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "";

        for (String word : words) {
            response += word + ": " + map.weightHistory(word, startYear, endYear).toString() + "\n";
        }

        return response;
    }
}
