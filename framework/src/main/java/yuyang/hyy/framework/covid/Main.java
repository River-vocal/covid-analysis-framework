package yuyang.hyy.framework.covid;

import yuyang.hyy.framework.covid.core.CovidAnalysisFrameworkImpl;
import yuyang.hyy.framework.covid.core.PluginLoader;
import yuyang.hyy.framework.covid.gui.CovidFrameworkGUI;

import javax.swing.SwingUtilities;

/**
 * Main class to initiate.
 */
public class Main {
    /**
     * The main method to start
     * @param args arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndStartFramework);
    }

    private static void createAndStartFramework() {
        CovidAnalysisFrameworkImpl core = new CovidAnalysisFrameworkImpl();
        CovidFrameworkGUI gui = new CovidFrameworkGUI(core);
        core.setListener(gui);
        PluginLoader.listDataPlugins();

    }



}
