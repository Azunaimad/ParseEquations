package interf;

import equationparser.EquationParser;
import equationparser.RandomGeneratorType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainWindow extends JFrame {

    //Window Components
    private JFileChooser fc;
    private File file;

    private JPanel jPanel;
    private JTabbedPane jTabbedPane;
    private JMenuBar menuBar = new JMenuBar();
    private MainTab[] mainTabs;
    private SettingWindow settingWindow;


    //Model parameters
    private int periods = 5;
    private int iterations = 1;
    private StepType step = StepType.Year;
    private RandomGeneratorType randomGenerator = RandomGeneratorType.Mersenne;
    private EquationParser simulation;

    //Window parameters
    public int windowWidth = 800;
    public int windowHeight = 600;

    private int equationSPAreaWidth = 320;
    private int equationSPAreaHeight = 480;

    private int nOfColumns = 20;
    private int nOfRows = 5;

    private int dataTableWidth = 320;
    private int dataTableHeight = 480;

    private int tabNumber = 1;
    String defaultTabName = "Untitled";

    public JMenuBar createMenuBar(){
        fc=new JFileChooser();

        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        JMenuItem newModel = new JMenuItem("New Model");
        newModel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNewModel();
            }
        });
        menu.add(newModel);

        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(MainWindow.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                    onOpen();
                }
            }
        });
        menu.add(open);

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showSaveDialog(MainWindow.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                    onSave();
                }
            }
        });
        menu.add(save);
        return menuBar;
    }

    public JMenuBar createCalculateBar(){
        JMenu menu = new JMenu("Calculate");
        menuBar.add(menu);
        JMenuItem calculate = new JMenuItem("Calculate");
        calculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCalculate();
            }
        });
        menu.add(calculate);
        return menuBar;
    }

    public JMenuBar createSettingsBar(){
        JMenu menu = new JMenu("Settings");
        menuBar.add(menu);
        JMenuItem settings = new JMenuItem("Settings");
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSettings();
                periods = settingWindow.getPeriods();
                iterations = settingWindow.getIterations();
            }
        });
        menu.add(settings);
        return menuBar;
    }

    private void onNewModel(){
        MainTab newPanel = new MainTab(nOfRows, nOfColumns,
                dataTableWidth, dataTableHeight,
                equationSPAreaWidth, equationSPAreaHeight);

        MainTab[] tmp = mainTabs;
        mainTabs = new MainTab[tmp.length+1];
        System.arraycopy(tmp, 0, mainTabs, 0, mainTabs.length - 1);
        mainTabs[mainTabs.length-1] = newPanel;
        tabNumber++;
        jTabbedPane.add(defaultTabName + tabNumber, newPanel);
    }

    private void onCalculate(){
        String equationsStr = mainTabs[jTabbedPane.getSelectedIndex()].getEquations();
        String[] equationsArr = equationsStr.split("\n");
        EquationParser equationParser = new EquationParser(5);
        for(String s : equationsArr)
            equationParser.detectArrays(s);
        equationParser.arrays.setElement(0,0,10);
        equationParser.calculate(equationsArr);
    }

    private void onOpen(){

    }

    private void onSettings(){

        settingWindow = new SettingWindow(periods,iterations, randomGenerator,step);
        settingWindow.setSize(350,200);
        settingWindow.setFocusable(true);
        settingWindow.setVisible(true);


    }

    private void onSave(){
        try {
            String equationsStr = mainTabs[jTabbedPane.getSelectedIndex()].getEquations();
            String[] equationsArr = equationsStr.split("\n");
            PrintWriter pw = new PrintWriter(file);
            for(String s : equationsArr) {
                pw.println(s);
            }
            pw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private JTabbedPane createTabbedPane(){
        JTabbedPane jTabbedPane = new JTabbedPane();
        mainTabs = new MainTab[1];
        mainTabs[0] = new MainTab(nOfRows, nOfColumns,
                dataTableWidth, dataTableHeight,
                equationSPAreaWidth, equationSPAreaHeight);
        jTabbedPane.addTab(defaultTabName + tabNumber, mainTabs[0]);
        return jTabbedPane;
    }

    public JPanel createContentPane(){
        JPanel contentPane = new JPanel(new BorderLayout());
        jTabbedPane = createTabbedPane();
        contentPane.add(jTabbedPane);
        return contentPane;
    }

    public static void createAndShowGUI(){
        JFrame frame = new JFrame("Contacts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainWindow mainWindow = new MainWindow();
        frame.setJMenuBar(mainWindow.createMenuBar());
        frame.setJMenuBar(mainWindow.createCalculateBar());
        frame.setJMenuBar(mainWindow.createSettingsBar());

        mainWindow.jPanel = mainWindow.createContentPane();
        frame.setContentPane(mainWindow.jPanel);

        frame.setSize(mainWindow.windowWidth, mainWindow.windowHeight);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });

    }

}
