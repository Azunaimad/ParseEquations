package statistics;


import namedstruct.Array;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 * Created by Azunai on 24.04.2015.
 */
public class ConfidenceInterval{

    public double[] calcMatrixMeanCI(Array array, double level){
        double[] CI = new double[array.getNOfElements()];
        for(int i=0; i< array.getNOfElements(); i++){
            SummaryStatistics stats = new SummaryStatistics();
            for (double val : array.getPeriodIterations(i))
                stats.addValue(val);
            CI[i] = calcSingleMeanCI(stats, level);
        }
        return CI;
    }

    public double calcSingleMeanCI(SummaryStatistics stats, double level){
        try{
            TDistribution tDist = new TDistribution(stats.getN() - 1);
            double critVal = tDist.inverseCumulativeProbability(1.0 - (1-level) / 2);
            return critVal * stats.getStandardDeviation() / Math.sqrt(stats.getN());
        } catch (MathIllegalArgumentException e){
            return Double.NaN;
        }
    }
}
