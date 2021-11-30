package yuyang.hyy.framework.covid.core;

/**
 * Data plugin
 */
public interface DataPlugin {
    /**
     * Get the name of the plugin
     * @return name of the plugin
     */
    String getName();

    /**
     * Get the data from the source. Throw illegalArgumentException if the route is not legal.
     * @param route source route
     * @return country data map from the source
     */
    CountryDataMap getData(String route);

    /**
     * Get the covid category that is accessible from the data plugin.
     * @return list of accessible category
     */
    CovidDataCategory[] supportCategory();
}
