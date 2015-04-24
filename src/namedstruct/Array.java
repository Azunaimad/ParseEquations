package namedstruct;


import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 * Named array
 * @author Ponomarev George
 */
public class Array {
    private String name;
    private double[][] value;

    public Array(String name, int nOfElements, int nOfIterations){
        this.name = name;
        value = new double[nOfElements][nOfIterations];
    }

    /**
     * Set element value
     * @param val - value, which we want to set
     * @param position - position in array
     */
    public void setValue(double val, int position, int iteration){
        try{
            value[position][iteration] = val;
        } catch (ArrayIndexOutOfBoundsException aioobe){
            aioobe.printStackTrace();
        }
    }

    /**
     * Get element value
     * @param position - position in array
     * @return - element value
     */
    public double getValue(int position, int iteration){
        double res = Double.NaN;
        try{
            res = value[position][iteration];
        } catch (ArrayIndexOutOfBoundsException aioobe){
            aioobe.printStackTrace();
        }
        return res;
    }

    /**
     * Get array name
     * @return - name
     */
    public String getName(){
        return name;
    }

    public int getNOfElements(){
        return value.length;
    }
    public int getNOfIterations(){
        return value[0].length;
    }

    public double[] getPeriodIterations(int period){
        double[] result = new double[value[0].length];
        System.arraycopy(value[period], 0, result, 0, value[0].length);
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

    public double[][] getMeanCI(double level){
        double[] CI = new double[getNOfElements()];
        for(int i=0; i< getNOfElements(); i++){
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
