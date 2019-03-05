package variablesGenerator;

import java.util.ArrayList;

public class Services {

    private static InitGenerator init;

    private static Services instance = new Services();

    private Services(){
        init =  InitGenerator.getInstance();
    }

    public static Services getInstance(){
        return instance;
    }

    /**
     * Return service times
     */
    public double getServiceTime(){
        double exporate = 0.25;
        return init.exponential(exporate, 0);
    }
}
