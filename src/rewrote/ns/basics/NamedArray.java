package rewrote.ns.basics;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.sql.Array;
import java.util.function.Predicate;

public class NamedArray extends AbstractNamed{
    private double[][] array;

    public NamedArray(String name){
        setName(name);
        array = null;
    }

    public NamedArray(String name, int nOfPeriods, int nOfInterations){
        setName(name);
        array = new double[nOfPeriods][nOfInterations];
    }

    public void setValue(double value, int period, int iteration){
        try{
            array[period][iteration] = value;
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    public double getValue(int period, int iteration){
        double value= Double.NaN;
        try{
            value = array[period][iteration];
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return value;
    }

    public void setArray(double[][] array){
        this.array = array;
    }

    public double[][] getArray(){
        return array;
    }

    public int getNOfElements(){
        return array.length;
    }
    public int getNOfIterations(){
        return array[0].length;
    }

    public double[] getPeriodIterations(int period){
        double[] result = new double[getNOfIterations()];
        try {
            System.arraycopy(array[period], 0, result, 0, getNOfIterations());
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

        return result;
    }


    public double[] getMean(){
        double[] result = new double[getNOfElements()];
        for(int i=0; i<getNOfElements(); i++){
            SummaryStatistics stats = new SummaryStatistics();
            for (double val : getPeriodIterations(i))
                stats.addValue(val);
            result[i] = stats.getMean();
        }
        return result;
    }

    public double[] getStDev(){
        double[] result = new double[getNOfElements()];
        for(int i=0; i<getNOfElements(); i++){
            SummaryStatistics stats = new SummaryStatistics();
            for (double val : getPeriodIterations(i))
                stats.addValue(val);
            result[i] = stats.getStandardDeviation();
        }
        return result;
    }

    public double[] getMax(){
        double[] result = new double[getNOfElements()];
        for(int i=0; i<getNOfElements(); i++){
            SummaryStatistics stats = new SummaryStatistics();
            for (double val : getPeriodIterations(i))
                stats.addValue(val);
            result[i] = stats.getMax();
        }
        return result;
    }

    public double[] getMin(){
        double[] result = new double[getNOfElements()];
        for(int i=0; i<getNOfElements(); i++){
            SummaryStatistics stats = new SummaryStatistics();
            for (double val : getPeriodIterations(i))
                stats.addValue(val);
            result[i] = stats.getMin();
        }
        return result;
    }


    public double[][] getMeanCI(double level){
        double[] CI = new double[getNOfElements()];
        for(int i=0; i < getNOfElements(); i++){
            SummaryStatistics stats = new SummaryStatistics();
            for (double val : getPeriodIterations(i))
                stats.addValue(val);
            CI[i] = calcSingleMeanCI(stats, level);
        }

        double[][] result = new double[CI.length][2];

        double[] mean = getMean();
        for(int i=0; i<result.length; i++){
            result[i][0] = mean[i] - CI[i];
            result[i][1] = mean[i] + CI[i];
        }
        return result;
    }

    private double calcSingleMeanCI(SummaryStatistics stats, double level){
        try{
            TDistribution tDist = new TDistribution(stats.getN() - 1);
            double critVal = tDist.inverseCumulativeProbability(1.0 - (1-level) / 2);
            return critVal * stats.getStandardDeviation() / Math.sqrt(stats.getN());
        } catch (MathIllegalArgumentException e){
            return Double.NaN;
        }
    }
}
