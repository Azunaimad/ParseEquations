package rewrote.ui;

import rewrote.ui.latex.LatexPanel;
import rewrote.ui.tables.ArraysTableModel;
import rewrote.ui.tables.ConstantsTableModel;
import rewrote.ui.tables.RealDataTableModel;

import javax.swing.*;
import java.awt.*;


public class Tab extends JPanel {
    private JTextArea equationArea;
    private JScrollPane equationScrollPane;
    private static final int EQUATION_SCROLL_PANE_WIDTH = 200;
    private static final int EQUATION_SCROLL_PANE_HEIGHT = 100;

    private ArraysTableModel arraysTableModel;
    private JTable arraysTable;
    private JScrollPane arraysTableScrollPane;
    private static final int ARRAYS_TABLE_SCROLL_PANE_WIDTH = 200;
    private static final int ARRAYS_TABLE_SCROLL_PANE_HEIGHT = 100;

    private ConstantsTableModel constantsTableModel;
    private JTable constantsTable;
    private JScrollPane constantsTableScrollPane;
    private static final int CONSTANTS_TABLE_SCROLL_PANE_WIDTH = 200;
    private static final int CONSTANTS_TABLE_SCROLL_PANE_HEIGHT = 100;

    private RealDataTableModel realDataTableModel;
    private JTable realDataTable;
    private JScrollPane realDataTableScrollPane;
    private static final int REAL_DATA_TABLE_SCROLL_PANE_WIDTH = 200;
    private static final int REAL_DATA_TABLE_SCROLL_PANE_HEIGHT = 100;

    private JPanel latexPanel;
    private JScrollPane latexPanelScrollPane;
    private static final int LATEX_PANEL_SCROLL_PANE_WIDTH = 200;
    private static final int LATEX_PANEL_SCROLL_PANE_HEIGHT = 100;


    private static final int HORIZONTAL_GAP = 5;
    private static final int VERTICAL_GAP = 5;


    public Tab(){
        super(new BorderLayout(HORIZONTAL_GAP,VERTICAL_GAP));

        //Create equation area
        equationArea = new JTextArea();
        equationScrollPane = new JScrollPane(equationArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        TextLineNumber lineNumbers = new TextLineNumber(equationArea);
        equationScrollPane.setRowHeaderView(lineNumbers);
        equationScrollPane.setPreferredSize(new Dimension(EQUATION_SCROLL_PANE_WIDTH,
                EQUATION_SCROLL_PANE_HEIGHT));
        equationScrollPane.setLayout(new ScrollPaneLayout());

        //Create constants table
        constantsTableModel = new ConstantsTableModel();
        constantsTable = new JTable(constantsTableModel);
        constantsTableScrollPane = new JScrollPane(constantsTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        constantsTableScrollPane.setPreferredSize(new Dimension(CONSTANTS_TABLE_SCROLL_PANE_WIDTH,
                                                                CONSTANTS_TABLE_SCROLL_PANE_HEIGHT));
        constantsTableScrollPane.setLayout(new ScrollPaneLayout());

        //Create arrays table
        arraysTableModel = new ArraysTableModel();
        arraysTable = new JTable(arraysTableModel);
        arraysTableScrollPane = new JScrollPane(arraysTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        arraysTableScrollPane.setPreferredSize(new Dimension(ARRAYS_TABLE_SCROLL_PANE_WIDTH,
                                                             ARRAYS_TABLE_SCROLL_PANE_HEIGHT));
        arraysTableScrollPane.setLayout(new ScrollPaneLayout());

        //Create latex pane
        //TODO: сделать так, чтобы jscrollpane работала
        latexPanel = new LatexPanel(equationArea);
        latexPanelScrollPane = new JScrollPane(latexPanel,
                                               ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                                               ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        latexPanelScrollPane.setPreferredSize(new Dimension(LATEX_PANEL_SCROLL_PANE_WIDTH,
                LATEX_PANEL_SCROLL_PANE_HEIGHT));
        latexPanelScrollPane.setLayout(new ScrollPaneLayout());

        //Create real data table
        realDataTableModel = new RealDataTableModel();
        realDataTable = new JTable(realDataTableModel);
        realDataTableScrollPane = new JScrollPane(realDataTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        realDataTableScrollPane.setPreferredSize(new Dimension(REAL_DATA_TABLE_SCROLL_PANE_WIDTH,
                                                               REAL_DATA_TABLE_SCROLL_PANE_HEIGHT));
        realDataTableScrollPane.setLayout(new ScrollPaneLayout());


        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(constantsTableScrollPane)
                                .addComponent(arraysTableScrollPane))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(equationScrollPane)
                                .addComponent(latexPanelScrollPane))
                        .addComponent(realDataTableScrollPane)
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(realDataTableScrollPane)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(equationScrollPane)
                                .addComponent(latexPanelScrollPane))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(constantsTableScrollPane)
                                .addComponent(arraysTableScrollPane))
        );

        setLayout(layout);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");

        frame.setSize(500,500);
        Tab tab = new Tab();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(tab);
        frame.setVisible(true);
    }
}
