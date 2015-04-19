package interf;

import equationparser.RandomGeneratorType;

import javax.swing.*;
import java.awt.event.*;

//TODO: добавить возможность указать генератор и шаг, добавить геттеры
public class SettingWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JSpinner periods;
    private JSpinner iterations;
    private JComboBox randomGenerator;
    private JComboBox step;

    private int tmpPeriods;
    private int tmpIterations;
    private RandomGeneratorType tmpRandomGenerator;
    private StepType tmpStep;

    public int getPeriods(){
        return (Integer) periods.getValue();
    }

    public int getIterations(){
        return (Integer) iterations.getValue();
    }

    public SettingWindow(int periodsVal, int iterationsVal,
                         RandomGeneratorType generatorType, StepType stepType) {
        this.periods.setValue(periodsVal);
        this.iterations.setValue(iterationsVal);

        tmpPeriods = periodsVal;
        tmpIterations = iterationsVal;

        tmpRandomGenerator = generatorType;
        tmpStep = stepType;

        periods.setModel(new SpinnerNumberModel(periodsVal,2,256,1));
        iterations.setModel(new SpinnerNumberModel(iterationsVal,1,10000,1));

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonSave);

        buttonSave.addActionListener(new ActionListener() {
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
        this.periods.setValue(tmpPeriods);
        this.iterations.setValue(tmpIterations);

        dispose();
    }

}
