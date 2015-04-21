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


    //Model variables
    private int periods = 5;
    private int iterations = 1;
    private StepType step = StepType.Year;
    private RandomGeneratorType randomGenerator = RandomGeneratorType.Mersenne;
    private EquationParser[] simulation;

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

        JMenu menu = new JMenu("Меню");
        menuBar.add(menu);

        JMenuItem newModel = new JMenuItem("Новая модель");
        newModel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNewModel();
            }
        });
        menu.add(newModel);

        JMenuItem open = new JMenuItem("Открыть");
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

        JMenuItem save = new JMenuItem("Сохранить как");
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
        JMenu menu = new JMenu("Имитация");
        menuBar.add(menu);
        JMenuItem calculate = new JMenuItem("Провести имитацию");
        calculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCalculate();
            }
        });
        menu.add(calculate);

        JMenuItem detect = new JMenuItem("Распознать формулы");
        detect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDetect();
            }
        });
        menu.add(detect);

        return menuBar;
    }

    public JMenuBar createSettingsBar(){
        JMenu menu = new JMenu("Настройки");
        menuBar.add(menu);
        JMenuItem settings = new JMenuItem("Настройки имитации");
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSettings();
                periods = settingWindow.getPeriods();
                iterations = settingWindow.getIterations();
                step = settingWindow.getStepType();
                randomGenerator = settingWindow.getRandomGeneratorType();
            }
        });
        menu.add(settings);
        return menuBar;
    }

    public void onDetect(){
        EquationParser eq = new EquationParser(periods,iterations,randomGenerator);
        String[] equations = mainTabs[jTabbedPane.getSelectedIndex()].getEquations().split("\n");
        for(String equation : equations)
            eq.detectArrays(equation);
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
        jTabbedPane.addTab(defaultTabName + tabNumber, newPanel);
    }

    //TODO: переписать
    private void onCalculate(){

        String equationsStr = mainTabs[jTabbedPane.getSelectedIndex()].getEquations();
        String[] equationsArr = equationsStr.split("\n");
        EquationParser equationParser = new EquationParser(periods, iterations, randomGenerator);
        for(String s : equationsArr)
            equationParser.detectArrays(s);
        equationParser.arrays.setElement(0,0,0,10);
        equationParser.calculate(equationsArr);

    }

    private void onOpen(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line);
                sb.append(";");
            }
            onNewModel();
            mainTabs[mainTabs.length-1].setEquations(sb.toString().split(";"));
            jTabbedPane.setSelectedIndex(mainTabs.length-1);
            jTabbedPane.setTitleAt(jTabbedPane.getSelectedIndex(),file.getName());
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
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
            jTabbedPane.setTitleAt(jTabbedPane.getSelectedIndex(),file.getName());

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

    public static void createAndShowGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        JFrame frame = new JFrame("Имитационнное моделирование");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

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
                try {
                    createAndShowGUI();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
