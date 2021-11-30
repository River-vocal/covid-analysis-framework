package yuyang.hyy.framework.covid.core;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to store a group of data (usually a country's data for one year).
 */
public class DataGroup {
    private final String topic;
    private final CovidDataCategory categoryName;
    private final List<DataPoint> data;

    /**
     * Constructor taking in the name of this group and the category name of data.
     * @param topic topic name of this data group
     * @param categoryName data category
     */
    public DataGroup(String topic, CovidDataCategory categoryName) {
        this.topic = topic;
        this.categoryName = categoryName;
        data = new ArrayList<>();
    }

    /**
     * data getter.
     * @return data list.
     */
    public List<DataPoint> getData() {
        return data;
    }

    /**
     * Add a data point.
     * @param p data point to be added
     */
    public void addData(DataPoint p) {
        data.add(p);
    }

    /**
     * Get all the coordinates of each data point on x axis.
     * @return a list of string, representing the x coordinates of this data group.
     */
    public List<String> getX() {
        List<String> result = new ArrayList<>();
        for (DataPoint p : data) {
            result.add(p.getX());
        }
        return result;
    }

    /**
     * Get all the value of each data point on y axis.
     * @return a list of double
     */
    public List<Double> getY() {
        List<Double> result = new ArrayList<>();
        for (DataPoint p : data) {
            result.add(p.getY());
        }
        return result;
    }

    @Override
    public String toString() {
        return "DataCountry{" +
                "topic='" + topic + '\'' +
                ", categoryName=" + categoryName +
                ", data=" + data +
                '}';
    }

    /**
     * Getter for DisplayPlugins.
     * @return name of category
     */
    public CovidDataCategory getCategoryName() {
        return categoryName;
    }

    /**
     * Getter for DisplayPlugins.
     * @return topic name
     */
    public String getTopic() {
        return topic;
    }
}
