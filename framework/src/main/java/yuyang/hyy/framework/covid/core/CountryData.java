package yuyang.hyy.framework.covid.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Covid Data of one country
 */
public class CountryData {
    private String name;
    private int positiveCumulative;
    private int hospitalizedCumulative;
    private int deathCumulative;
    private Map<Integer, DateData> dateDataMap;

    /**
     * Constructor to create a class for a country's data/
     * @param n name of the country
     */
    public CountryData(String n) {
        name = n;
        positiveCumulative = 0;
        hospitalizedCumulative = 0;
        deathCumulative = 0;
        dateDataMap = new HashMap<>();
    }

    /**
     * Add one single day's data into the map.
     * @param d data of one day
     */
    public void addDateData(DateData d) {
        dateDataMap.put(d.getDate(), d);
    }

    /**
     * Update the cumulative data for all positive cases.
     * @param num addend
     */
    public void addPositiveCumulative(int num) {
        positiveCumulative += num;
    }

    /**
     * Update the cumulative data for all hospitalized cases.
     * @param num addend
     */
    public void addHospitalizedCumulative(int num) {
        hospitalizedCumulative += num;
    }

    /**
     * Update the cumulative data for all death cases.
     * @param num addend
     */
    public void addDeathCumulative(int num) {
        deathCumulative += num;
    }

    /**
     * Check if country data contains the date
     * @param date query date
     * @return if the data contains date
     */
    public boolean hasDateData(int date){
        return dateDataMap.containsKey(date);
    }

    /**
     * Get the value of the data with specified category.
     * @param date date number
     * @param c category that is specified
     * @return value
     */
    public int getDateData(int date, CovidDataCategory c) {
        if (c.equals(CovidDataCategory.HOSPITALIZED)) {
            return dateDataMap.get(date).getHospitalizedDaily();
        } else if (c.equals(CovidDataCategory.DEATH)) {
            return dateDataMap.get(date).getDeathDaily();
        } else {
            return dateDataMap.get(date).getPositiveDaily();
        }
    }

    /**
     * get the name of the country
     * @return country name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the specified category's cumulative number of the country data.
     * @param category category that to be asked
     * @return cumulative number of this category
     */
    public int getSpecifiedCategory(CovidDataCategory category) {
        if (category.equals(CovidDataCategory.DEATH)) {
            return getDeathCumulative();
        } else if (category.equals(CovidDataCategory.HOSPITALIZED)) {
            return getHospitalizedCumulative();
        } else {
            return getPositiveCumulative();
        }
    }

    /**
     * Get total number of deaths.
     * @return total number of deaths
     */
    private int getDeathCumulative() {
        return deathCumulative;
    }

    /**
     * Get total number of hospitalized patients.
     * @return total number of hospitalized patients
     */
    private int getHospitalizedCumulative() {
        return hospitalizedCumulative;
    }

    /**
     * Get total number of positive cases.
     * @return total number of positive cases
     */
    private int getPositiveCumulative() {
        return positiveCumulative;
    }

    @Override
    public String toString() {
        return "CountryData{" +
                "name='" + name + '\'' +
                ", positiveCumulative=" + positiveCumulative +
                ", hospitalizedCumulative=" + hospitalizedCumulative +
                ", deathCumulative=" + deathCumulative +
                ", dateDataMap=" + dateDataMap +
                '}';
    }
}
