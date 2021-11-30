package yuyang.hyy.framework.covid.core;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Load data and display plugins
 * Code adapted from CMU 17-214 Piazza
 */
public class PluginLoader {

    /**
     * Display the list of data plugin names
     */
    public static void listDataPlugins() {
        for (DataPlugin p : ServiceLoader.load(DataPlugin.class))
            System.out.println(p.getName());
    }

    /**
     * Load all data plugins
     * @return data plugins in the service loader
     */
    public static List<DataPlugin> loadDataPlugins(){
        List<DataPlugin> dataPluginList = new ArrayList<>();
        for (DataPlugin p : ServiceLoader.load(DataPlugin.class))
            dataPluginList.add(p);
        return dataPluginList;
    }

    /**
     * Load all display plugins
     * @return display plugins in the service loader
     */
    public static List<DisplayPlugin> loadDisplayPlugins(){
        List<DisplayPlugin> displayPluginList = new ArrayList<>();
        for (DisplayPlugin p : ServiceLoader.load(DisplayPlugin.class))
            displayPluginList.add(p);
        return displayPluginList;
    }

}
