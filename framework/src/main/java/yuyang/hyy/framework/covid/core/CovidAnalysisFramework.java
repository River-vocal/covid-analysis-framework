package yuyang.hyy.framework.covid.core;

/**
 * Core covid analysis framework methods
 */
public interface CovidAnalysisFramework {
    /**
     * Process the map passed in from data plugin
     * @param countryDataMap the map contains data from data plugin
     */
    void setCountryDataMap(CountryDataMap countryDataMap);

    /**
     * Convert the Data in map into displayable DataSet.
     * Get the parameter required by the client along with the number of countries.
     * @param countryNum number of countries to be shown
     * @param categoryName category that to be looked into
     * @param isCumulative if we are requesting for cumulative or discrete value (if only request for 1 country,
     *                     then it has to be discrete)
     */
    void loadDataSet(int countryNum, CovidDataCategory categoryName, boolean isCumulative);

    /**
     * Pass the display data to display plugins
     * @return display data
     */
    DataSet getDisplayData();

    /**
     * Apply an operation to the original data
     * @param operation operations to be applied on data
     * @return the processed display data
     */
    DataSet applyOperation(Operation operation);
}
