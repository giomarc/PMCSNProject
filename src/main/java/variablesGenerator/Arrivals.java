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
     * Return arrival times
     */
    /*public double getArrival(int job_class){
        double arrival_rate = 0.0;
        //int job_class = getComingJobClass();
        if(job_class == 1)
            arrival_rate = SystemConfiguration.ARRIVAL_RATE_1;

        else if (job_class == 2)
            arrival_rate = SystemConfiguration.ARRIVAL_RATE_2;

        else{
            System.out.println("packet not recognized");
            System.exit(-1);
        }
        return init.getInstance().exponential(arrival_rate, 0);
    }*/


    /*public double getArrival(int job_class){
        double arrival_rate = 0.0;
        if(job_class == 1)
            arrival_rate = SystemConfiguration.ARRIVAL_RATE_1;

        else if (job_class == 2)
            arrival_rate = SystemConfiguration.ARRIVAL_RATE_2;

        else{
            System.out.println("packet not recognized");
            System.exit(-1);
        }
        return init.getInstance().exponential(arrival_rate, 0);
    }*/

    public double getTotalRate(){
        return SystemConfiguration.ARRIVAL_RATE_1 + SystemConfiguration.ARRIVAL_RATE_2;
    }



    public double getArrival(){
        double total_rate = getTotalRate();
        return init.getInstance().exponential(total_rate, 0);
    }


    public int determineJobClass(){
        double p1 = SystemConfiguration.ARRIVAL_RATE_1/getTotalRate();
        double p = init.getInstance().uniform();

        if(p <= p1)
            return 1;
        else
            return 2;
    }




    /*public int getComingJobClass(){

        double rate_class1 = SystemConfiguration.ARRIVAL_RATE_1;
        double rate_class2 = SystemConfiguration.ARRIVAL_RATE_2;
        double total_rate = rate_class1 + rate_class2;
        double prob_class1 = (rate_class1/total_rate);
        double prob_class2 = (rate_class2/total_rate);
        //new arrival is of class 2?
        double p = init.getInstance().uniform();
        if(p <= prob_class1)
            //is a class 1 job
            return 1;
        else
            return 2;
    }*/



}

