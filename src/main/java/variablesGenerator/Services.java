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
        return init.getInstance().exponential(service_rate, 1);
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
        return init.getInstance().exponential(service_rate, 2);
    }


    public double getJobOperations(){
        return InitGenerator.getInstance().exponential(1, 3);
    }

    public double getCloudletServiceTimePerOperation(int jobClass, double numberOfOperations){
        if(jobClass == 1)
            return (1/SystemConfiguration.CLOUDLET_M1) * numberOfOperations;
        else
            return (1/SystemConfiguration.CLOUDLET_M2) * numberOfOperations;
    }

    public double getCloudServiceTimePerOperation(int jobClass, double numberOfOperations){
        if(jobClass == 1)
            return (1/SystemConfiguration.CLOUD_M1) * numberOfOperations;
        else
            return (1/SystemConfiguration.CLOUD_M2) * numberOfOperations;
    }


}
