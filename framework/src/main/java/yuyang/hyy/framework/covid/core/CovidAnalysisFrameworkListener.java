package yuyang.hyy.framework.covid.core;

/**
 * Gui listener interface
 */
public interface CovidAnalysisFrameworkListener {
    /**
     * Displaying new visual plot
     */
    void onNewVisual();

    /**
     * Change the source of data
     * @param dataSource source name
     */
    void onDataSourceChange(String dataSource);

    /**
     * Change the registered data plugin name
     * @param dataPlugin new data plugin
     */
    void onDataPluginChange(DataPlugin dataPlugin);

    /**
     * Change the registered display plugin name
     * @param displayPlugin new display plugin
     */
    void onDisplayPluginChange(DisplayPlugin displayPlugin);

    /**
     * Apply operation
     * @param operation the operation selected
     */
    void onOperation(Operation operation);

    /**
     * Error happens
     * @param message error message
     */
    void errorMessage(String message);


}
