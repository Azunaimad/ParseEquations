package rewrote.ns.basics;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.*;


public class NamedRandVector extends NamedVector implements RandomNamed {
    private RandomGenerator generator = new MersenneTwister();
    private RealDistribution distribution = new NormalDistribution();

    public NamedRandVector(String name) {
        super(name);
    }

    public NamedRandVector(String name, int periods) {
        super(name, periods);
    }

    public NamedRandVector(String name, int periods, RandomGenerator generator){
        super(name, periods);
        this.generator = generator;
    }

    public NamedRandVector(String name, int periods, RandomGenerator generator, RealDistribution distribution){
        super(name, periods);
        this.generator = generator;
        this.distribution = distribution;
    }

    @Override
    public void generate() {
       for(int i = 0; i < getNOfElements(); i++)
           setValue(distribution.inverseCumulativeProbability(generator.nextDouble()),i);
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
