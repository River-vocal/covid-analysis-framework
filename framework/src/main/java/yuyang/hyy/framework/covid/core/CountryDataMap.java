package yuyang.hyy.framework.covid.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Complete data the plugin get from the data source
 */
public class CountryDataMap {
    private Map<String, CountryData> countryMap;
    private String source;

    /**
     * Constructor to create a map.
     * @param sourceName name of the source dataPlugin.
     */
    public CountryDataMap(String sourceName) {
        countryMap = new HashMap<>();
        source = sourceName;
    }

    /**
     * Add a countryData into this map.
     * @param c country to be stored in
     */
    public void addCountryData(CountryData c) {
        countryMap.put(c.getName(), c);
    }

    /**
     * Get the number of countries.
     * @return country number
     */
    public int size() {
        return countryMap.size();
    }

    /**
     * Get the source of the data
     * @return the source of the data
     */
    public String getSource() {
        return source;
    }

    /**
     * Get country data map
     * @return country data map
     */
    public Map<String, CountryData> getCountryMap() {
        return countryMap;
    }

    /**
     * Get data of specific country
     * @param country country name
     * @return the country data of the input country name
     */
    public CountryData getCountryData(String country){
        return countryMap.get(country);
    }

    /**
     * Get the specified number of country data in descending order of value by the category name.
     * @param countryNum number of countries needed
     * @param categoryName category to be compared by
     * @return list of countries in descending order
     */
    public List<CountryData> generateSpecifiedCountryData(int countryNum, CovidDataCategory categoryName) {
        // Map the number belong this category to a set of possible countries (might have duplicate number)
        Map<Integer, Set<String>> numMap = new HashMap<>();
        // All value from the specified category of the countries
        List<Integer> values = new ArrayList<>();
        for (Map.Entry<String, CountryData> e: countryMap.entrySet()) {
            int val = -e.getValue().getSpecifiedCategory(categoryName);
            if (numMap.containsKey(val)) {
                Set<String> existed = numMap.get(val);
                if (existed.add(e.getKey())) {
                    numMap.put(val, existed);
                }
            } else {
                Set<String> tmp = new HashSet<>();
                if (tmp.add(e.getKey())) {
                    numMap.put(val, new HashSet<>(tmp));
                }
            }
            values.add(val);
        }

        Collections.sort(values);
        List<CountryData> result = new ArrayList<>();
        int index = 0;
        int i = 0;
        while (i < countryNum) {
            Set<String> v = numMap.get(values.get(index));
            for (String s : v) {
                result.add(countryMap.get(s));
            }
            index += 1;
            i += v.size();
        }
        return result;
    }

    @Override
    public String toString() {
        return "CountryDataMap{" +
                "countryMap=" + countryMap +
                ", source='" + source + '\'' +
                '}';
    }
}
