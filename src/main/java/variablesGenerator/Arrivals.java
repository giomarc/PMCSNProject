package variablesGenerator;

import system.SystemConfiguration;

public class Arrivals {

    private static InitGenerator init  = InitGenerator.getInstance();

    private static Arrivals instance = new Arrivals();

    private Arrivals(){}

    public static Arrivals getInstance(){
        return instance;
    }



    /**
     * Return total rate
     */
    public double getTotalRate(){
        return SystemConfiguration.ARRIVAL_RATE_1 + SystemConfiguration.ARRIVAL_RATE_2;
    }


    /**
     * Return arrival times
     */
    public double getArrival(){
        double total_rate = getTotalRate();
        return init.exponential(total_rate,0);
    }


    /**
     * Return job class
     */
    public int determineJobClass(){
        double p1 = SystemConfiguration.ARRIVAL_RATE_1/getTotalRate();
        double p = init.uniform();

        if(p <= p1){
            return 1;
        }
        else {
            return 2;
        }
    }

}

