package rewrote.ns.basics;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.*;


public class NamedRandomConst extends NamedConst implements RandomNamed {

    private RandomGenerator generator = new MersenneTwister();
    private RealDistribution distribution = new NormalDistribution();

    public NamedRandomConst(String name){
       super(name);
    }

    public NamedRandomConst(String name, RandomGenerator generator){
        super(name);
        this.generator = generator;
    }

    public NamedRandomConst(String name, RandomGenerator generator, RealDistribution distribution){
        super(name);
        this.generator = generator;
        this.distribution = distribution;
    }

    @Override
    public void generate() {
        setValue(distribution.inverseCumulativeProbability(generator.nextDouble()));
    }

    @Override
    public void setGenerator(RandomGenerator generatorType) {
        this.generator = generatorType;
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
