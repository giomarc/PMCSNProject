package variablesGenerator;

import system.SystemConfiguration;
import variablesGenerator.library.Rvgs;
import variablesGenerator.library.Rngs;

public class InitGenerator {

    private static InitGenerator instance = null;

    private Rvgs rvgs;
    private Rngs rngs;

    private  InitGenerator(){
        rngs = new Rngs();
        rngs.plantSeeds(SystemConfiguration.SEED);
        rvgs = new Rvgs(this.rngs);
    }

    public void putNewSeed(long seed){
        rngs = new Rngs();
        rngs.plantSeeds(SystemConfiguration.SEED);
        rvgs = new Rvgs(this.rngs);
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
    public double exponential(double rate,int stream){
        selectStream(stream);
        return this.rvgs.exponential(1/rate);
    }

    public double uniform(){
        selectStream(15);
        return this.rvgs.uniform(0.0,1.0);
    }


}
