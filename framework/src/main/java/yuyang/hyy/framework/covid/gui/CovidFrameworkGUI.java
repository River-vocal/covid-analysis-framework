package yuyang.hyy.framework.covid.gui;

import yuyang.hyy.framework.covid.core.CovidAnalysisFrameworkImpl;
import yuyang.hyy.framework.covid.core.CovidAnalysisFrameworkListener;
import yuyang.hyy.framework.covid.core.CovidDataCategory;
import yuyang.hyy.framework.covid.core.DataPlugin;
import yuyang.hyy.framework.covid.core.DisplayPlugin;
import yuyang.hyy.framework.covid.core.Op;
import yuyang.hyy.framework.covid.core.Operation;
import yuyang.hyy.framework.covid.core.PluginLoader;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.util.Objects;

/**
 * An implementation of gui interface for the covid analysis framework.
 */
public class CovidFrameworkGUI implements CovidAnalysisFrameworkListener {
    private static final String DEFAULT_TITLE = "Covid Analysis Framework";
    private static final String DATA_PLUGINS = "Data Plugin";
    private static final String DISPLAY_PLUGINS = "Display Plugin";
    private static final int WIDTH = 1250;
    private static final int HEIGHT = 800;
    private CovidDataCategory dataCategory = CovidDataCategory.DEATH;
    private static final String[] CATEGORY_LIST = {"DEATH", "POSITIVE", "HOSPITALIZED"};
    private static final String[] COUNTRY_NUMBER_LIST = {"1", "3", "5", "10"};
    private static final String[] CUMULATIVE_LIST = {"Discrete","Sum"};
    private static final String[] OPERATION_LIST = {"ORIGIN", "PERCENTAGE_CHANGE", "PERCENTAGE", "DIFFERENCE"};

    private final JFrame frame;
    private final JPanel panel;
    private final JPanel displayPanel;
    private JLabel pluginInfo;
    private JTextField source;
    private final JComboBox<String> categoryBox = new JComboBox<String>(CATEGORY_LIST);
    private final JComboBox<String> countryNumberBox = new JComboBox<String>(COUNTRY_NUMBER_LIST);
    private final JComboBox<String> cumulativeBox = new JComboBox<String>(CUMULATIVE_LIST);
    private JComboBox<String> operationBox = new JComboBox<String>(OPERATION_LIST);
    private final JLabel hintMessage = new JLabel("");

    private CovidAnalysisFrameworkImpl core;
    private DataPlugin currentDataPlugin;
    private DisplayPlugin currentDisplayPlugin;
    private String dataSource;
    private boolean cumulativeChoice;
    private int countryNumber;
    private JLabel currentChoiceInfo;

    /**
     * Constructor that build the frame
     * @param cf core analysis framework
     */
    public CovidFrameworkGUI(CovidAnalysisFrameworkImpl cf) {
        core = cf;
        frame = new JFrame(DEFAULT_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        pluginInfo = new JLabel();
        source = new JTextField(10);
        displayPanel = new JPanel();
        panel = initialBoard();
        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        cumulativeChoice = false;

        categoryBox.setSelectedIndex(0);
        countryNumberBox.setSelectedIndex(0);
        cumulativeBox.setSelectedIndex(0);
        operationBox.setSelectedIndex(0);

        categoryBox.addActionListener(e -> {
            clearMessage();
        });
        countryNumberBox.addActionListener(e -> {
            clearMessage();
        });
        cumulativeBox.addActionListener(e -> {
            clearMessage();
        });
        operationBox.addActionListener(e -> {
            clearMessage();
        });
    }

    /**
     * Set up the initial board
     * @return the initial board
     */
    private JPanel initialBoard(){
        JPanel initialPanel = new JPanel();
        initialPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        JMenuBar menuBar = new JMenuBar();

        JMenu dataMenu = new JMenu(DATA_PLUGINS);
        menuBar.add(dataMenu);

        List<DataPlugin> dataPluginList = PluginLoader.loadDataPlugins();
        for (DataPlugin p: dataPluginList){
            JMenuItem menuItem = new JMenuItem(p.getName());
            menuItem.addActionListener(e -> {
                clearMessage();
                onDataPluginChange(p);
            });
            dataMenu.add(menuItem);
        }

        JMenu displayMenu = new JMenu(DISPLAY_PLUGINS);

        List<DisplayPlugin> displayPluginList = PluginLoader.loadDisplayPlugins();
        for (DisplayPlugin p:displayPluginList){
            JMenuItem menuItem = new JMenuItem(p.getName());
            menuItem.addActionListener(e -> {
                clearMessage();
                onDisplayPluginChange(p);
            });
            displayMenu.add(menuItem);
        }

        menuBar.add(dataMenu);
        menuBar.add(displayMenu);

        updatePlugins();
        initialPanel.setLayout(new BorderLayout());
        initialPanel.add(menuBar, BorderLayout.NORTH);
        initialPanel.add(pluginInfo, BorderLayout.SOUTH);
        initialPanel.add(infoPanel(), BorderLayout.WEST);
        initialPanel.add(displayPanel, BorderLayout.CENTER);
        initialPanel.setVisible(true);
        return initialPanel;
    }

    /**
     * Update plugin names on the panel
     */
    private void updatePlugins() {
        String dataName = "";
        String displayName = "";
        currentDataPlugin = core.getDataPlugin();
        currentDisplayPlugin = core.getDisplayPlugin();
        if (currentDisplayPlugin != null){
            displayName = currentDisplayPlugin.getName();
            String[] newOp = core.getSupportedOperation();
            operationBox.removeAllItems();
            for (String s : newOp) {
                operationBox.addItem(s);
            }
            operationBox.setSelectedItem(0);
        }
        if (currentDataPlugin != null){
            dataName = currentDataPlugin.getName();
            String[] newCate = core.getSupportedCategory();
            categoryBox.removeAllItems();
            for (String s : newCate) {
                categoryBox.addItem(s);
            }
            categoryBox.setSelectedItem(0);
        }
        pluginInfo.setText("Current data plugin: " + dataName + "; Current display plugin: " + displayName);
    }

    private JPanel infoPanel(){
        JPanel infoPanel = new JPanel();
        JLabel sourceTag = new JLabel("Source: ");
        JLabel sourceContent = new JLabel(dataSource);
        JButton dataSourceButton = new JButton("Load data from source");
        JLabel operationTag = new JLabel("Operation:");
        JButton operationButton = new JButton("Apply Operation");
        operationButton.addActionListener(e->{
            clearMessage();
            Operation op = Op.valueOf((String) operationBox.getSelectedItem());
            onOperation(op);
        });

        currentChoiceInfo = new JLabel();

        dataSourceButton.addActionListener(e->{
            clearMessage();
            onDataSourceChange(source.getText());
        });

        JButton displayButton = new JButton("Display");
        displayButton.addActionListener(e->{
            clearMessage();
            core.clearOperation();
            if (core.getDisplayPlugin() == null) {
                setMessage("No Display Plugin Chosen");
            } else if (core.getDataPlugin() == null) {
                setMessage("No Data Plugin Chosen");
            } else {
                try {
                    loadDataSet();
                    onNewVisual();
                } catch (IllegalArgumentException f) {
                    setMessage(f.getMessage());
                }
            }
        });

        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
        infoPanel.add(sourceTag);
        infoPanel.add(sourceContent);
        infoPanel.add(source);
        infoPanel.add(dataSourceButton);
        infoPanel.add(new JLabel("Data Category:"));
        infoPanel.add(categoryBox);
        infoPanel.add(new JLabel("Number of Country:"));
        infoPanel.add(countryNumberBox);
        infoPanel.add(new JLabel("Cumulative Choice:"));
        infoPanel.add(cumulativeBox);
        updateCurrentChoice();
        infoPanel.add(currentChoiceInfo);
        infoPanel.add(displayButton);
        infoPanel.add(operationTag);
        infoPanel.add(operationBox);
        infoPanel.add(operationButton);
        JLabel tmp = new JLabel(" ");
        tmp.setPreferredSize(new Dimension(100, 800));
        infoPanel.add(tmp);
        hintMessage.setForeground(Color.RED);
        infoPanel.add(hintMessage);
        return infoPanel;
    }

    private void updateCurrentChoice() {
        if (cumulativeChoice) {
            currentChoiceInfo.setText(countryNumber + " nation; "+ dataCategory + "; sum");
        } else {
            currentChoiceInfo.setText(countryNumber + " nation; "+ dataCategory + "; discrete");
        }
    }

    /**
     * The client is requiring to get some sort of data from the core.
     */
    private void loadDataSet() {
        countryNumber = Integer.parseInt((String) Objects.requireNonNull(countryNumberBox.getSelectedItem()));
        dataCategory = CovidDataCategory.valueOf((String) categoryBox.getSelectedItem());
        cumulativeChoice = Objects.equals((String) cumulativeBox.getSelectedItem(), "Sum");
        updateCurrentChoice();
        core.loadDataSet(countryNumber, dataCategory, cumulativeChoice);
    }

    @Override
    public void onNewVisual() {
        if (core.getDataPlugin() != null && core.getDisplayPlugin() != null){
            JPanel plot = core.displayVisual();
            displayPanel.removeAll();
            displayPanel.revalidate();
            displayPanel.repaint();
            displayPanel.add(plot);
        }
    }



    @Override
    public void onDataSourceChange(String dataSource) {
        if (core.getDataPlugin() != null) {
            try {
                this.dataSource = dataSource;
                core.getData(dataSource);
                setMessage("Succeed Load Source");
            } catch (IllegalArgumentException e) {
                setMessage(e.getMessage());
            }
        } else {
            setMessage("No Data Plugin Chosen");
        }
    }

    @Override
    public void onDataPluginChange(DataPlugin dataPlugin) {
        core.registerDataPlugin(dataPlugin);
        updatePlugins();
    }

    @Override
    public void onDisplayPluginChange(DisplayPlugin displayPlugin) {
        core.registerDisplayPlugin(displayPlugin);
        updatePlugins();
        core.clearOperation();
    }

    @Override
    public void onOperation(Operation operation) {
        if (core.getDataPlugin() != null && core.getDisplayPlugin() != null) {
            try {
                core.applyOperation(operation);
                onNewVisual();
            } catch (IllegalArgumentException e) {
                setMessage(e.getMessage());
            }
        } else {
            setMessage("Not Enough Info Yet");
        }
    }

    @Override
    public void errorMessage(String message) {
        setMessage(message);
    }

    private void setMessage(String message) {
        hintMessage.setText(message);
    }

    private void clearMessage() {
        hintMessage.setText("");
    }
}
