package interf;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Azunai on 19.04.2015.
 */
public class MainTab extends JPanel {
    private JTextArea equationArea;
    private JTable dataTable;
    private Font font = new Font("Verdana",Font.PLAIN,12);

    public MainTab(int rows, int columns, int dataTableWidth, int dataTableHeight,
                   int equationSPAreaWidth, int equationSPAreaHeight){
        super();

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
}
