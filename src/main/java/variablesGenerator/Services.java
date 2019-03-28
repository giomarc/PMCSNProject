package variablesGenerator;

import system.SystemConfiguration;

public class Services {

    private static InitGenerator init = null;
    private static Services instance = null;

    private double serviceRatePhase1Class1;
    private double serviceRatePhase1Class2;
    private double serviceRatePhase2Class1;
    private double serviceRatePhase2Class2;



    public static Services getInstance(){
        if(instance == null)
            instance = new Services();
        return instance;
    }

    private Services(){
        double p = SystemConfiguration.PHASE_P;
        serviceRatePhase1Class1 = 2*p*SystemConfiguration.CLOUDLET_M1;
        serviceRatePhase1Class2 = 2*p*SystemConfiguration.CLOUDLET_M2;
        serviceRatePhase2Class1 = 2*(1-p)*SystemConfiguration.CLOUDLET_M1;
        serviceRatePhase2Class2 = 2*(1-p)*SystemConfiguration.CLOUDLET_M2;
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
        return InitGenerator.getInstance().exponential(service_rate, 2);
    }


    public double getJobOperations(){
        return InitGenerator.getInstance().exponential(1, 3);
    }

    public double getCloudletExpServiceTimePerOperation(int jobClass, double numberOfOperations){
        if(jobClass == 1)
            return (1/SystemConfiguration.CLOUDLET_M1) * numberOfOperations;
        else
            return (1/SystemConfiguration.CLOUDLET_M2) * numberOfOperations;
    }

    public double getCloudExpServiceTimePerOperation(int jobClass, double numberOfOperations){
        if(jobClass == 1)
            return (1/SystemConfiguration.CLOUD_M1) * numberOfOperations;
        else
            return (1/SystemConfiguration.CLOUD_M2) * numberOfOperations;
    }

    public double getCloudletHyperExpServiceTimePerOperation(int jobClass, double numberOfOperations){
        double serviceRate;
        double p = SystemConfiguration.PHASE_P;
        if(InitGenerator.getInstance().uniform() <= p){
            if(jobClass == 1)
                serviceRate = serviceRatePhase1Class1;
            else
                serviceRate = serviceRatePhase1Class2;
        }
        else{
            if(jobClass == 1)
                serviceRate = serviceRatePhase2Class1;
            else
                serviceRate = serviceRatePhase2Class2;
        }
        return (1/serviceRate) * numberOfOperations;
    }




}
