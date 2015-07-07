package rewrote.ns.basics;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;


public class NamedRandArray extends NamedArray implements RandomNamed {

    private RandomGenerator generator = new MersenneTwister();
    private RealDistribution distribution = new NormalDistribution();

    public NamedRandArray(String name){
        super(name);
    }

    public NamedRandArray(String name,
                          int nOfPeriods,
                          int nOfIterations){

        super(name, nOfPeriods, nOfIterations);
    }

    public NamedRandArray(String name,
                          int nOfPeriods,
                          int nOfIterations,
                          RandomGenerator generator){

        super(name, nOfPeriods, nOfIterations);
        this.generator = generator;
    }

    public NamedRandArray(String name,
                          int nOfPeriods,
                          int nOfIterations,
                          RandomGenerator generator,
                          RealDistribution distribution){

        super(name, nOfPeriods, nOfIterations);
        this.generator = generator;
        this.distribution = distribution;
    }

    @Override
    public void generate() {
        for(int i = 0; i < getNOfElements(); i++)
            for(int j = 0; j < getNOfIterations(); j++)
                setValue(distribution.inverseCumulativeProbability(generator.nextDouble()),i,j);
    }

    @Override
    public void setGenerator(RandomGenerator generator) {
        this.generator = generator;
    }

    @Override
    public RandomGenerator getGenerator() {
        return generator;
    }

    @Override
    public void setDistribution(RealDistribution distribution) {
        this.distribution = distribution;
    }

    @Override
    public RealDistribution getDistribution() {
        return distribution;
    }

    @Override
    public void setSeed(int seed) {
        generator.setSeed(seed);
    }
}
