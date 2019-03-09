package variablesGenerator;

import system.SystemConfiguration;

public class Services {

    private static InitGenerator init = null;
    private static Services instance = null;



    public static Services getInstance(){
        if(instance == null)
            instance = new Services();
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
