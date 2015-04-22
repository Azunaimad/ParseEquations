package interf;

import namedstruct.ArrayStructure;

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

    public LineChartDialog(ArrayStructure arrays, Object[][] arraysObj) {
        setTitle("График по периодам");
        setContentPane(contentPane);
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

        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

}
