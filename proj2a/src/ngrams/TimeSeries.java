package ngrams;

import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        // TODO: Fill in this constructor.
        Map<Integer, Double> sub = ts.subMap(startYear, true, endYear, true);
        this.putAll(sub);
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        // TODO: Fill in this method.
        Set<Integer> yearsSet = this.keySet();
        return new ArrayList<>(yearsSet);
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        // TODO: Fill in this method.
        List<Integer> years = this.years();
        List<Double> values = new ArrayList<>();
        for (Integer year : years) {
            values.add(this.get(year));
        }
        return values;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();
        Set<Integer> allYears = new TreeSet<>(this.keySet());
        allYears.addAll(ts.keySet());

        for (Integer year : allYears) {
            double valueFromThis = this.get(year) != null ? this.get(year) : 0; // Check if the value is null
            double valueFromTS = ts.get(year) != null ? ts.get(year) : 0; // Check if the value is null

            // Sum the values and put them in the result TimeSeries
            result.put(year, valueFromThis + valueFromTS);
        }
        return result;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();
        Set<Integer> years = new TreeSet<>(this.keySet());
        for (Integer year : years) {
            if (ts.containsKey(year)) {
                double valueFromTS = ts.get(year);
                double value = valueFromTS != 0.0 ? this.get(year) / valueFromTS : 0.0;
                result.put(year, value);
            } else {
                throw new IllegalArgumentException();
            }
        }
        return result;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
