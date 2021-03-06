package interf;

import javax.swing.*;
import java.awt.event.*;

public class ExcelData extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBox1;
    private JSpinner spinner1;

    public String sheetName;
    private String[] sheetsName;
    public int rowNumber;

    public ExcelData(String[] sheetsName) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.sheetsName = sheetsName;
        for (String aSheetsName : sheetsName) comboBox1.addItem(aSheetsName);

        spinner1.setModel(new SpinnerNumberModel(1,1,30000,1));

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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        rowNumber = (Integer) spinner1.getValue();
        sheetName = sheetsName[comboBox1.getSelectedIndex()];
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

}
