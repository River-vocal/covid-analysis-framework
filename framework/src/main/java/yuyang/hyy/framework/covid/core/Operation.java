package yuyang.hyy.framework.covid.core;

/**
 * Data process operations
 */
public interface Operation {
    /**
     * process the data according to the operation
     * @param dataSet pre processed dataset
     * @return processed data
     */
    DataSet processData(DataSet dataSet);

    /**
     * Get the name of the operation
     * @return name of the operation
     */
    String getName();
}
