package yuyang.hyy.framework.covid.core;

import javax.swing.JPanel;

/**
 * Display plugin interface for the covid analysis framework
 */
public interface DisplayPlugin {
    /**
     * display the visual from the framework
     * @param dataSet dataset from the framework
     * @return JPanel containing the plot we need
     */
    JPanel displayPlot(DataSet dataSet);

    /**
     * Get the name of the plugin
     * @return name of the plugin
     */
    String getName();

    /**
     * Get the operation that is supported by the display plugin.
     * @return list of supported operations
     */
    Operation[] supportedOperation();

    /**
     * If the display plugin support displaying multiple countries with discrete data.
     * @return true if support, false otherwise
     */
    boolean ifSupportMultiDiscrete();
}
