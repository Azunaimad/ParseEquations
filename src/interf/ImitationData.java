package interf;

import namedstruct.Array;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class ImitationData extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTable table1;

    public ImitationData(Array array) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        Object[] columnNames = new Object[array.getNOfIterations()+1];
        columnNames[0] = "Периоды";
        for(int i=1; i< array.getNOfIterations()+1; i++){
            columnNames[i] = "Итерация "+i;
        }
        Object[][] data =  new Object[array.getNOfElements()][array.getNOfIterations()+1];
        for(int i=0; i < array.getNOfElements();i++) {
            data[i][0] = i;
            for (int j = 1; j < array.getNOfIterations(); j++) {
                data[i][j]=array.getValue(i,j);
            }
        }



        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        table1.setModel(tableModel);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });



    }

    private void onOK() {
// add your code here
        dispose();
    }


}
