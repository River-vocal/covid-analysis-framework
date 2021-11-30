package yuyang.hyy.framework.covid.core;

import javax.swing.JPanel;
import java.util.List;

/**
 * An implementation of the covid analysis framework
 */
public class CovidAnalysisFrameworkImpl implements CovidAnalysisFramework{
    private CountryDataMap countryDataMap;
    private CovidAnalysisFrameworkListener listener;
    private DataSet originalData;
    private DataSet transformedData;
    private DataPlugin dataPlugin;
    private DisplayPlugin displayPlugin;
    private static final int PERIOD = 15;
    private static final String COUNTRY = "Mixed Countries";
    private Operation op = Op.ORIGIN;

    @Override
    public void setCountryDataMap(CountryDataMap countryDataMap) {
        this.countryDataMap = countryDataMap;
    }

    @Override
    public void loadDataSet(int countryNum, CovidDataCategory categoryName,
                            boolean isCumulative) {
        if (countryDataMap == null) {
            throw new IllegalArgumentException("Update Source First");
        }
        if (getCountryNumber() < countryNum) {
            throw new IllegalArgumentException("More Countries Than Available");
        }
        List<CountryData> sorted = countryDataMap.generateSpecifiedCountryData
                (countryNum, categoryName);
        originalData = new DataSet();
        originalData.setOpLabel(Op.ORIGIN);
        if (countryNum == 1) {
            if (isCumulative) {
                listener.errorMessage("1 Country Has Discrete Only");
            }
            CountryData c = sorted.get(0);
            DataGroup country = new DataGroup(c.getName(), categoryName);
            for (int i = 1; i <= PERIOD; i++) {
                if (c.hasDateData(i)){
                    DataPoint tmp = new DataPoint(Integer.toString(i),
                            c.getDateData(i, categoryName));
                    country.addData(tmp);
                }
            }
            originalData.addData(country);
        } else {
            if (!isCumulative) {
                if (!displayPlugin.ifSupportMultiDiscrete()) {
                    listener.errorMessage("Not Support Discrete, Default Sum");
                    isCumulative = true;
                }
            }
            if (isCumulative) {
                DataGroup group = new DataGroup(COUNTRY, categoryName);
                for (CountryData country : sorted) {
                    DataPoint tmp = new DataPoint(country.getName(),
                            country.getSpecifiedCategory(categoryName));
                    group.addData(tmp);
                }
                originalData.addData(group);
            } else {
                for (CountryData c : sorted) {
                    DataGroup country = new DataGroup(c.getName(), categoryName);
                    for (int i = 1; i <= PERIOD; i++) {
                        if (c.hasDateData(i)){
                            DataPoint tmp = new DataPoint(Integer.toString(i),
                                    c.getDateData(i, categoryName));
                            country.addData(tmp);
                        }
                    }
                    originalData.addData(country);
                }
            }
        }
    }

    @Override
    public DataSet getDisplayData() {
        if (op.equals(Op.ORIGIN)) {
            return originalData;
        }
        return transformedData;
    }

    @Override
    public DataSet applyOperation(Operation operation) {
        if (originalData == null) {
            throw new IllegalArgumentException("Click Display To Generate");
        }
        op = operation;
        if (op.equals(Op.ORIGIN)) {
            return originalData;
        } else {
            transformedData = op.processData(originalData);
            transformedData.setOpLabel(operation);
            return transformedData;
        }
    }

    /**
     * Set the data plugin
     * @param dataPlugin the registered data plugin
     */
    public void registerDataPlugin(DataPlugin dataPlugin){
        this.dataPlugin = dataPlugin;
    }

    /**
     * Set the display plugin
     * @param displayPlugin the registered display plugin
     */
    public void registerDisplayPlugin(DisplayPlugin displayPlugin){
        this.displayPlugin = displayPlugin;
    }

    /**
     * Access registered data plugin
     * @return registered data plugin
     */
    public DataPlugin getDataPlugin() {
        return dataPlugin;
    }


    /**
     * Access registered display plugin
     * @return registered display plugin
     */
    public DisplayPlugin getDisplayPlugin() {
        return displayPlugin;
    }

    /**
     * Get data wrapper of data plugin
     * @param route source of data
     */
    public void getData(String route){
        setCountryDataMap(dataPlugin.getData(route));
    }

    /**
     * Set the listener
     * @param listener gui listener
     */
    public void setListener(CovidAnalysisFrameworkListener listener) {
        this.listener = listener;
    }

    /**
     * Display the visual using the registered display plugin
     * @return a JPanel where the graph is displayed.
     */
    public JPanel displayVisual() {
        DataSet d = getDisplayData();
        return displayPlugin.displayPlot(d);
    }

    /**
     * Get the number of countries in current country data map.
     * @return number of countries
     */
    public int getCountryNumber() {
        return countryDataMap.size();
    }

    /**
     * Get the operations that is supported by current display plugin.
     * @return array of operation names
     */
    public String[] getSupportedOperation() {
        if (displayPlugin != null) {
            int length = displayPlugin.supportedOperation().length;
            String[] result = new String[length];
            for (int i = 0; i < length; i++) {
                result[i] = displayPlugin.supportedOperation()[i].getName();
            }
            return result;
        }
        return null;
    }

    /**
     * Get the categories that is accessible from current data plugin.
     * @return array of category names
     */
    public String[] getSupportedCategory() {
        if (dataPlugin != null) {
            int length = dataPlugin.supportCategory().length;
            String[] result = new String[length];
            for (int i = 0; i < length; i++) {
                result[i] = dataPlugin.supportCategory()[i].getName();
            }
            return result;
        }
        return null;
    }

    /**
     * When you change a display plugin, you would clear the current operation to default.
     */
    public void clearOperation() {
        op = Op.ORIGIN;
    }
}
