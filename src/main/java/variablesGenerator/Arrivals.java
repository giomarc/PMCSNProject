package variablesGenerator;

import config.SystemConfiguration;

public class Arrivals {

    private static InitGenerator init;

    private static Arrivals instance = new Arrivals();

    private Arrivals(){
        init =  InitGenerator.getInstance();
    }

    public static Arrivals getInstance(){
        return instance;
    }


    /**
     * Return arrival times
     */
    public double getArrival(){
        double exporate = 0.1;
        return init.exponential(exporate, 0);
    }
}

