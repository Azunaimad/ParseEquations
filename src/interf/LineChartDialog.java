package interf;

import namedstruct.Array;
import namedstruct.ArrayStructure;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.event.*;

public class LineChartDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner start;
    private JSpinner end;
    private JTextField textField1;
    private JCheckBox ExpValCheckBox;
    private JCheckBox MaxMinCheckBox;
    private JCheckBox realCheckBox;
    private JCheckBox CICheckBox;
    private JCheckBox ExpValStDevCheckBox;
    private JComboBox comboBox1;

    private String xLabel;
    private ArrayStructure arrays;
    private TableModel arrayModel;

    public LineChartDialog(ArrayStructure arrays, TableModel arrayModel, String step) {
        setTitle("График по периодам");
        setContentPane(contentPane);
        xLabel = step;
        this.arrays = arrays;
        this.arrayModel = arrayModel;

        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        for(int i=0; i<arrays.length(); i++)
            comboBox1.addItem(arrays.getName(i));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
// add your code here

        Array plotArray;
        String yLabel;
        plotArray = arrays.getArray(comboBox1.getSelectedIndex());
        yLabel = arrayModel.getDefinition(arrays.getName(comboBox1.getSelectedIndex()));

        PlotLineChart chart = new PlotLineChart(textField1.getText(),
                                                plotArray,
                                                (Integer) start.getValue(),
                                                (Integer) end.getValue(),
                                                xLabel,
                                                yLabel);


        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

}
