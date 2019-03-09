package variablesGenerator;

import config.SystemConfiguration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Services {

    private static InitGenerator init;

    private static Services instance = new Services();

    private Services(){}

    public static Services getInstance(){
        return instance;
    }

    /**
     * Return cloudlet service times
     * @param job_class
     */
    public double getCloudletServiceTime(int job_class){
        double service_rate;
        if(job_class == 1)
             service_rate = SystemConfiguration.CLOUDLET_M1;
        else
            service_rate = SystemConfiguration.CLOUDLET_M2;
        return init.getInstance().exponential(service_rate, job_class);
    }

    /**
     * Return cloudlet service times
     * @param job_class
     */
    public double getCloudServiceTime(int job_class){
        double service_rate;
        if(job_class == 1)
            service_rate = SystemConfiguration.CLOUD_M1;
        else
            service_rate = SystemConfiguration.CLOUD_M2;
        return init.getInstance().exponential(service_rate, job_class);
    }


}
