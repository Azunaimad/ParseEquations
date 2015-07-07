package rewrote.ns.basics;

import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.RandomGenerator;


public interface RandomNamed {

    void generate();

    void setGenerator(RandomGenerator generator);

    RandomGenerator getGenerator();

    void setDistribution(RealDistribution distribution);

    RealDistribution getDistribution();

    void setSeed(int seed);
}
