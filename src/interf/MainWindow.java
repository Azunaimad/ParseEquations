package interf;

import equationparser.EquationParser;
import equationparser.RandomGeneratorType;
import org.jfree.ui.RefineryUtilities;

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
    private JMenu chartMenu;
    private JMenuItem calculate;


    //Model variables
    private int periods = 5;
    private int iterations = 1;
    private StepType step = StepType.Year;
    private RandomGeneratorType randomGenerator = RandomGeneratorType.Mersenne;
    private EquationParser simulation;

    //Window parameters
    public int windowWidth = 1000;
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

        JMenu menu = new JMenu("Файл");
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
        calculate = new JMenuItem("Провести имитацию");
        calculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCalculate();
            }
        });
        menu.add(calculate);
        calculate.setEnabled(false);

        JMenuItem detect = new JMenuItem("Распознать формулы");
        detect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDetect();
            }
        });
        menu.add(detect);
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
                //TODO: исправить костыль. Блочит "Провести имитацию". Добавить методы set для всех параметров,
                //TODO: и сделать так, чтобы правильно собиралась задача.
                calculate.setEnabled(false);
            }
        });
        menu.add(settings);

        return menuBar;
    }


    public JMenuBar createChartBar(){
        chartMenu = new JMenu("Графики");
        menuBar.add(chartMenu);
        JMenuItem lineChart = new JMenuItem("График по периодам");
        lineChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLineChart();
            }
        });
        chartMenu.add(lineChart);
        chartMenu.setEnabled(false);
        return menuBar;
    }

    public void onLineChart(){
        LineChartDialog dialog = new LineChartDialog(simulation.arrays,
                mainTabs[jTabbedPane.getSelectedIndex()].arrayModel.getData());
        dialog.pack();
        RefineryUtilities.centerFrameOnScreen(dialog);
        dialog.setVisible(true);
    }

    //TODO: исправить баг, который заключается в том, что появляется пустая строка в таблице, элементов которой нет
    public void onDetect(){
        simulation = new EquationParser(periods,iterations,randomGenerator);
        String[] equations = mainTabs[jTabbedPane.getSelectedIndex()].getEquations().split("\n");
        for(String equation : equations) {
            simulation.detectArrays(equation);
            simulation.detectConstants(equation);
        }

        int rowCount = mainTabs[jTabbedPane.getSelectedIndex()].arrayModel.getRowCount();
        for(int i=rowCount -1 ; i>=0; i--)
            mainTabs[jTabbedPane.getSelectedIndex()].arrayModel.removeRow(i);

        Object[] arraysObj = new Object[3];
        for(int i=0; i<simulation.arrays.length(); i++){
            arraysObj[0] = simulation.arrays.getName(i);
            arraysObj[1] = "";
            arraysObj[2] = 0.0;
            mainTabs[jTabbedPane.getSelectedIndex()].arrayModel.addRow(arraysObj);
        }

        rowCount = mainTabs[jTabbedPane.getSelectedIndex()].paramModel.getRowCount();
        for(int i=rowCount - 1 ; i>=0; i--)
            mainTabs[jTabbedPane.getSelectedIndex()].paramModel.removeRow(i);

        arraysObj = new Object[3];
        for(int i=0; i < simulation.constants.length(); i++){
            arraysObj[0] = simulation.constants.getName(i);
            arraysObj[1] = "";
            arraysObj[2] = 0.0;
            mainTabs[jTabbedPane.getSelectedIndex()].paramModel.addRow(arraysObj);
        }

        calculate.setEnabled(true);

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
        for(int j=0; j<simulation.arrays.length(); j++)
            for (int i = 0; i < iterations; i++){
                String name = simulation.arrays.getName(j);
                double value = mainTabs[jTabbedPane.getSelectedIndex()].arrayModel.getValue(name);
                simulation.arrays.setElement(name,0,i,value);
            }
        for(int j=0; j<simulation.constants.length(); j++) {
            String name = simulation.constants.getName(j);
            double value = mainTabs[jTabbedPane.getSelectedIndex()].arrayModel.getValue(name);
            simulation.constants.setConstValue(name,value);
        }

        simulation.calculate(equationsArr);
        for(int i=0; i<periods; i++)
            System.out.println(simulation.arrays.getArray(0).getValue(i,0));
        for(int i=0; i<periods; i++)
            System.out.println(simulation.randArrays.getArray(0).getValue(i,0));

        chartMenu.setEnabled(true);


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
        RefineryUtilities.centerFrameOnScreen(settingWindow);
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
        frame.setJMenuBar(mainWindow.createChartBar());

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
