package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;
import static utils.Utils.SHORT_WORDS_FILE;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    private final Map<String, TimeSeries> wordsToTimeSeries;
    private final TimeSeries countsToTimeSeries;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        wordsToTimeSeries = new TreeMap<>();
        // handle words file
        In wordsIn = new In(wordsFilename);
        while (!wordsIn.isEmpty()) {
            String nextLine = wordsIn.readLine();
            String[] splitLine = nextLine.split("\t");
            TimeSeries timeSeries = wordsToTimeSeries.getOrDefault(splitLine[0], new TimeSeries());
            timeSeries.put(Integer.valueOf(splitLine[1]), Double.valueOf(splitLine[2]));
            wordsToTimeSeries.put(splitLine[0], timeSeries);
        }

        // handle counts file
        countsToTimeSeries = new TimeSeries();
        In countsIn = new In(countsFilename);
        while (!countsIn.isEmpty()) {
            String nextLine = countsIn.readLine();
            String[] splitLine = nextLine.split(",");
            countsToTimeSeries.put(Integer.valueOf(splitLine[0]), Double.valueOf(splitLine[1]));
        }

    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries timeSeries = wordsToTimeSeries.getOrDefault(word, new TimeSeries());
        return new TimeSeries(timeSeries, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        return wordsToTimeSeries.getOrDefault(word, new TimeSeries());
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        return new TimeSeries(countsToTimeSeries, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries timeSeries = countHistory(word, startYear, endYear);
        return timeSeries.dividedBy(new TimeSeries(countsToTimeSeries, startYear, endYear));
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        TimeSeries timeSeries = countHistory(word);
        return timeSeries.dividedBy(totalCountHistory());
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                    int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();
        for (String s : words) {
            result = result.plus(weightHistory(s, startYear, endYear));
        }
        return result;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();
        for (String s : words) {
            result = result.plus(weightHistory(s));
        }
        return result;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
