package variablesGenerator;

import system.SystemConfiguration;
import variablesGenerator.library.Rvgs;
import variablesGenerator.library.Rngs;
import variablesGenerator.library.Rvms;


public class InitGenerator {

    private static InitGenerator instance = null;

    private Rvgs rvgs;
    private Rngs rngs;
    private Rvms rvms;

    private  InitGenerator(){
        rngs = new Rngs();
        rngs.plantSeeds(SystemConfiguration.SEED);
        rvgs = new Rvgs(this.rngs);

        rvms = new Rvms();
    }

    public void putNewSeed(long seed){
        rngs = new Rngs();
        rngs.plantSeeds(seed);
        rvgs = new Rvgs(this.rngs);
        SystemConfiguration.SEED = seed;
    }


    /**
     * Return singleton instance
     */

    public static InitGenerator getInstance(){
        if (instance == null){
            instance = new InitGenerator();
        }
        return instance;
    }

    public void selectStream (int indexStream){
        rvgs.rngs.selectStream(indexStream);
    }


    /**
     * Return distributions
     */
    public double exponential(double rate, int stream){
        selectStream(stream);
        return this.rvgs.exponential(1/rate);
    }

    public double uniform(){
        selectStream(5);
        return this.rvgs.uniform(0.0,1.0);
    }

    public double idfStudent(long n, double u){
        return rvms.idfStudent(n,u);
    }

}
