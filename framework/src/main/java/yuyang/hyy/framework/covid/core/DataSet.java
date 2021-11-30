package yuyang.hyy.framework.covid.core;

import java.util.ArrayList;
import java.util.List;

/**
 * The class to feed the display plugins, containing a list of data groups.
 */
public class DataSet {
    private final List<DataGroup> groups;
    private Operation opLabel;

    /**
     * Default constructor.
     */
    public DataSet() {
        groups = new ArrayList<>();
    }

    /**
     * Add a data from a country into the dataset.
     * @param d new data group
     */
    public void addData(DataGroup d) {
        groups.add(d);
    }

    /**
     * Get the groups in the data set
     * @return list of groups
     */
    public List<DataGroup> getGroups() {
        return groups;
    }


    /**
     * Get the x-axis labels of the dataset.
     * @return string of labels
     */
    public List<String> getXLabel() {
        if (groups == null || groups.size() < 1) {
            throw new IllegalArgumentException("incorrect data!");
        }
        return groups.get(0).getX();
    }

    /**
     * Get all the y values of the dataset.
     * @return first string layer would be each country, internal layer of string would be for different x's
     */
    public List<List<Double>> getAllYs() {
        List<List<Double>> result = new ArrayList<>();
        if (groups == null || groups.size() < 1) {
            throw new IllegalArgumentException("incorrect data!");
        }
        for (DataGroup c : groups) {
            result.add(c.getY());
        }
        return result;
    }

    /**
     * Getter for DisplayPlugins.
     * @return Category name.
     */
    public List<CovidDataCategory> getCategory() {
        List<CovidDataCategory> list = new ArrayList<>();
        for (DataGroup dg: groups) {
            list.add(dg.getCategoryName());
        }
        return list;
    }

    /**
     * Getter for topics (usually country names).
     * @return topics
     */
    public List<String> getTopics() {
        List<String> results = new ArrayList<>();
        for (DataGroup dg: groups) {
            results.add(dg.getTopic());
        }
        return results;
    }

    /**
     * Set the operation label of the dataset for future plot.
     * @param op update the operation
     */
    public void setOpLabel(Operation op) {
        opLabel = op;
    }

    /**
     * Get the name of the operation label of the dataset.
     * @return string operation label
     */
    public String getOpLabel() {
        return opLabel.getName();
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "groups=" + groups +
                '}';
    }
}
