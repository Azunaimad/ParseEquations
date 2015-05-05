package interf;

import equationparser.EquationParser;
import equationparser.RandomGeneratorType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.io.*;
import java.util.Iterator;
import java.util.Vector;

public class MainWindow extends JFrame {

    //Window Components
    private JFileChooser fc=new JFileChooser();
    private File file;

    private JPanel jPanel;
    private JTabbedPane jTabbedPane;
    private JMenuBar menuBar = new JMenuBar();
    private MainTab[] mainTabs;
    private SettingWindow settingWindow;
    private JMenu chartMenu;
    private JMenuItem calculate;
    private JMenuItem data;
    private JMenuItem optimize;
    private JComboBox[] realDataHeaderNames;


    //Model variables
    private int periods = 5;
    private int iterations = 1;
    private StepType step = StepType.Year;
    private RandomGeneratorType randomGenerator = RandomGeneratorType.Mersenne;
    private EquationParser simulation;

    //Window parameters
    public int windowWidth = 1000;
    public int windowHeight = 600;

    private int equationSPAreaWidth = 350;
    private int equationSPAreaHeight = 480;

    private int nOfColumns = 20;
    private int nOfRows = 5;

    private int dataTableWidth = 320;
    private int dataTableHeight = 480;

    private int tabNumber = 1;
    String defaultTabName = "Untitled";

    public JMenuBar createMenuBar(){

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

        optimize = new JMenuItem("Найти параметры модели");
        optimize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOptimize();
            }
        });
        optimize.setEnabled(false);
        menu.add(optimize);

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

        data = new JMenuItem("Фактические распределения");
        data.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(MainWindow.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                    onOpenData();
                }
            }
        });
        menu.add(data);
        data.setEnabled(false);

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

    //TODO: реализовать поиск параметров с помощью генетического алгоритма
    public void onOptimize(){

    }

    public void onOpenData(){
        try {
            FileInputStream fis = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            String[] sheetNames = new String[workbook.getNumberOfSheets()];
            for(int i=0; i<workbook.getNumberOfSheets(); i++)
                sheetNames[i] = workbook.getSheetName(i);
            ExcelData excelData = new ExcelData(sheetNames);
            excelData.pack();
            RefineryUtilities.centerFrameOnScreen(excelData);
            excelData.setVisible(true);


            HSSFSheet sheet = workbook.getSheet(excelData.sheetName);
            int rowCounter=1;

            int rowNum = sheet.getLastRowNum()+1;
            int colNum = sheet.getRow(0).getLastCellNum();
            Object[][] realData = new Object[rowNum][colNum];
            for(int i=0; i<rowNum; i++) {
                if (excelData.rowNumber > rowCounter) {
                    rowCounter++;
                } else {
                    HSSFRow row = sheet.getRow(i);
                    for (int j = 0; j < colNum; j++) {
                        HSSFCell cell = row.getCell(j);
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC:
                                realData[i][j] = cell.getNumericCellValue();
                                break;
                            case Cell.CELL_TYPE_STRING:
                                realData[i][j] = cell.getStringCellValue();
                                break;
                        }
                    }
                }
            }
            DefaultTableModel model = new DefaultTableModel(realData[0],0);
            mainTabs[jTabbedPane.getSelectedIndex()].dataTable.setModel(model);
            for(int i=1; i<realData.length; i++)
                model.addRow(realData[i]);

            fis.close();
            workbook.close();
            optimize.setEnabled(true);
            /*realDataHeaderNames = new JComboBox[maxRowLength];
            for(int i=0; i<realDataHeaderNames.length; i++){
                realDataHeaderNames[i] = new JComboBox();
                for(int j=0; j<simulation.arrays.length(); j++){
                    realDataHeaderNames[i].addItem(simulation.arrays.getName(j));
                    System.out.println(simulation.arrays.getName(j));
                }
            }
            TableColumnModel tcm = mainTabs[jTabbedPane.getSelectedIndex()].dataTable.getColumnModel();
            for(int i=0; i<maxRowLength; i++){
                TableColumn tc = new TableColumn();
                tcm.addColumn(tc);
            }
            mainTabs[jTabbedPane.getSelectedIndex()].dataTable.setTableHeader(new EditableHeader(tcm));
            for(int i=0; i<maxRowLength; i++){
                EditableHeaderTableColumn col;
                col = (EditableHeaderTableColumn) mainTabs[jTabbedPane.getSelectedIndex()].dataTable
                        .getColumnModel().getColumn(i);
                col.setHeaderValue(realDataHeaderNames[i].getItemAt(0));
                col.setHeaderEditor(new DefaultCellEditor(realDataHeaderNames[i]));
            }

            DefaultTableModel model = (DefaultTableModel) mainTabs[jTabbedPane.getSelectedIndex()].dataTable.getModel();
            System.out.println(realData.length);
            model.addRow(realData[0]);*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onLineChart(){
        String xLabel;
        switch (step){
            case Month:
                xLabel = "Месяц";
                break;
            case Quarter:
                xLabel = "Квартал";
                break;
            case Week:
                xLabel = "Неделя";
                break;
            case Day:
                xLabel = "День";
                break;
            default:
                xLabel = "Год";
        }


        LineChartDialog dialog = new LineChartDialog(simulation.arrays,
                mainTabs[jTabbedPane.getSelectedIndex()].arrayModel,
                xLabel);
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
        data.setEnabled(true);

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
                System.out.println(value);
                simulation.arrays.setElement(name,0,i,value);
            }
        for(int j=0; j<simulation.constants.length(); j++) {
            String name = simulation.constants.getName(j);
            double value = mainTabs[jTabbedPane.getSelectedIndex()].paramModel.getValue(name);
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
