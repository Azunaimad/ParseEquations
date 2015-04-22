package interf;

import javafx.geometry.VerticalDirection;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Created by Azunai on 19.04.2015.
 */
public class MainTab extends JPanel {
    private JTextArea equationArea;
    private JTable dataTable;
    private Font font = new Font("Verdana",Font.PLAIN,12);
    private JPanel arrayParamPanel;

    private JTable paramTable;
    private JTable arrayTable;
    public interf.TableModel paramModel = new interf.TableModel();
    public interf.TableModel arrayModel = new interf.TableModel();

    private int paramTableWidth = 240;
    private int paramTableHeight = 237;
    private int arrayTableWidth = 240;
    private int arrayTableHeight = 237;
    public MainTab(int rows, int columns, int dataTableWidth, int dataTableHeight,
                   int equationSPAreaWidth, int equationSPAreaHeight){
        super();


        arrayParamPanel = new JPanel();
        arrayParamPanel.setSize(400,400);
        paramTable = new JTable(paramModel);
        paramTable.setSize(paramTableWidth, paramTableHeight);
        paramTable.getColumnModel().getColumn(0).setMaxWidth(40);
        paramTable.getColumnModel().getColumn(2).setMaxWidth(100);

        JScrollPane pSP = new JScrollPane(paramTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pSP.setPreferredSize(new Dimension(paramTableWidth, paramTableHeight));
        pSP.setLayout(new ScrollPaneLayout());

        arrayTable = new JTable(arrayModel);
        arrayTable.setSize(arrayTableWidth, arrayTableHeight);
        arrayTable.getColumnModel().getColumn(0).setMaxWidth(40);
        arrayTable.getColumnModel().getColumn(2).setMaxWidth(100);

        JScrollPane aSP = new JScrollPane(arrayTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        aSP.setPreferredSize(new Dimension(arrayTableWidth, arrayTableHeight));
        aSP.setLayout(new ScrollPaneLayout());


        GroupLayout groupLayout = new GroupLayout(arrayParamPanel);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup()
                                .addComponent(aSP)
                                .addComponent(pSP))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                .addComponent(aSP)
                .addComponent(pSP)
        );

        arrayParamPanel.setLayout(groupLayout);

        equationArea = new JTextArea(rows,columns);
        equationArea.setFont(font);
        dataTable = new JTable(0,0);
        dataTable.setSize(dataTableWidth, dataTableHeight);

        JScrollPane eqSP = new JScrollPane(equationArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        eqSP.setPreferredSize(new Dimension(equationSPAreaWidth, equationSPAreaHeight));
        eqSP.setLayout(new ScrollPaneLayout());

        JScrollPane dataSP = new JScrollPane(dataTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        dataSP.setPreferredSize(new Dimension(dataTableWidth,dataTableHeight));

        add(arrayParamPanel);
        add(eqSP, BorderLayout.CENTER);
        add(dataSP);
    }

    public String getEquations(){
        return equationArea.getText();
    }
    public void setEquations(String[] text){
        for (String aText : text)
            equationArea.append(aText + "\n");
    }

   /* public Object[][] getParams(){
        return paramModel.getData();
    }

    public void setParams(Object[][] obj){
        paramModel.setData(obj);
    }

    public Object[][] getArrays(){
        return arrayModel.getData();
    }

    public void setArrays(Object[][] obj){
        arrayModel.setData(obj);
    }

    public void addParamsRow(Object[] obj){
        paramModel.addRow(obj);
    }*/

}
