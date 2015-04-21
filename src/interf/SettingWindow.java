package interf;

import equationparser.RandomGeneratorType;

import javax.swing.*;
import java.awt.event.*;

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

    private void setStep(StepType stepType){
        switch (stepType){
            case Year:
                step.setSelectedIndex(0);
                break;
            case Quarter:
                step.setSelectedIndex(1);
                break;
            case Month:
                step.setSelectedIndex(2);
                break;
            case Week:
                step.setSelectedIndex(3);
                break;
            case Day:
                step.setSelectedIndex(4);
                break;
        }
    }

    private void setRandomGenerator(RandomGeneratorType generatorType){
        switch (generatorType){
            case Mersenne:
                randomGenerator.setSelectedIndex(0);
                break;
            case JDKRandomGenerator:
                randomGenerator.setSelectedIndex(1);
                break;
            case HaltonSequence:
                randomGenerator.setSelectedIndex(2);
                break;
            case SobolSequence:
                randomGenerator.setSelectedIndex(3);
                break;
            case Well19937c:
                randomGenerator.setSelectedIndex(4);
                break;
            case Well44497b:
                randomGenerator.setSelectedIndex(5);
                break;
        }
    }

    public StepType getStepType(){
        switch (step.getSelectedIndex()){
            case 0:
                return StepType.Year;
            case 1:
                return StepType.Quarter;
            case 2:
                return StepType.Month;
            case 3:
                return StepType.Week;
            case 4:
                return StepType.Day;
            default:
                return StepType.Year;
        }
    }

    public RandomGeneratorType getRandomGeneratorType(){
        switch (randomGenerator.getSelectedIndex()){
            case 0:
                return RandomGeneratorType.Mersenne;
            case 1:
                return RandomGeneratorType.JDKRandomGenerator;
            case 2:
                return RandomGeneratorType.HaltonSequence;
            case 3:
                return RandomGeneratorType.SobolSequence;
            case 4:
                return RandomGeneratorType.Well19937c;
            case 5:
                return RandomGeneratorType.Well44497b;
            default:
                return RandomGeneratorType.Mersenne;
        }
    }

    public SettingWindow(int periodsVal, int iterationsVal,
                         RandomGeneratorType generatorType, StepType stepType) {

        this.periods.setValue(periodsVal);
        this.iterations.setValue(iterationsVal);
        setStep(stepType);
        setRandomGenerator(generatorType);

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
        setRandomGenerator(tmpRandomGenerator);
        setStep(tmpStep);
        dispose();
    }

}
