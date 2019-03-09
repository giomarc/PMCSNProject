package variablesGenerator;

import config.SystemConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Arrivals {

    private static InitGenerator init  = null;

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
        return init.getInstance().exponential(total_rate, 0);
    }


    /**
     * Return job class
     */
    public int determineJobClass(){
        double p1 = SystemConfiguration.ARRIVAL_RATE_1/getTotalRate();
        double p = init.getInstance().uniform();

        if(p <= p1)
            return 1;
        else
            return 2;
    }

}

