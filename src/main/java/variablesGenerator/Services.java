package variablesGenerator;

import system.SystemConfiguration;

public class Services {

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




    // SERVICE TIME CLOUD

    public double getCloudServiceTime(int jobclass, double numberOfOperations){
        if(SystemConfiguration.OPERATIONS_ENABLED)
            return getCloudServiceTimePerOperation(jobclass, numberOfOperations);
        else
            return getCloudServiceTimeWithoutOperations(jobclass);
    }

    private double getCloudServiceTimeWithoutOperations(int job_class){
        double service_rate;
        int stream;
        if(job_class == 1){
            service_rate = SystemConfiguration.CLOUD_M1;
            stream = 4;
        }
        else{
            service_rate = SystemConfiguration.CLOUD_M2;
            stream = 5;
        }
        return InitGenerator.getInstance().exponential(service_rate, stream);
    }

    private double getCloudServiceTimePerOperation(int jobClass, double numberOfOperations){
        if(jobClass == 1)
            return (1/SystemConfiguration.CLOUD_M1) * numberOfOperations;
        else
            return (1/SystemConfiguration.CLOUD_M2) * numberOfOperations;
    }



    // SERVICE TIME CLOUDLET

    public double getCloudletServiceTime(int jobclass, double numberOfOperations){
        //hyperexponential using operations
        if(SystemConfiguration.HYPEREXPO_ENABLED && SystemConfiguration.OPERATIONS_ENABLED)
            return getCloudletHyperExpServiceTimePerOperation(jobclass, numberOfOperations);

        //hyperexponential without using operations
        else if (SystemConfiguration.HYPEREXPO_ENABLED)
            return getCloudletHyperExpServiceTimeWithoutOperations(jobclass);

        //exponential using operations
        else if(SystemConfiguration.OPERATIONS_ENABLED)
            return getCloudletExpServiceTimePerOperation(jobclass, numberOfOperations);

        //exponential without using operations
        else
            return getCloudletServiceTimeWithoutOperations(jobclass);
    }

    @SuppressWarnings("Duplicates")
    private double getCloudletHyperExpServiceTimePerOperation(int jobClass, double numberOfOperations){
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

    @SuppressWarnings("Duplicates")
    private double getCloudletHyperExpServiceTimeWithoutOperations(int jobClass){
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
        return InitGenerator.getInstance().exponential(serviceRate, 1);
    }

    private double getCloudletExpServiceTimePerOperation(int jobClass, double numberOfOperations){
        if(jobClass == 1)
            return (1/SystemConfiguration.CLOUDLET_M1) * numberOfOperations;
        else
            return (1/SystemConfiguration.CLOUDLET_M2) * numberOfOperations;
    }

    private double getCloudletServiceTimeWithoutOperations(int job_class){
        double service_rate;
        int stream;
        if(job_class == 1) {
            service_rate = SystemConfiguration.CLOUDLET_M1;
            stream = 2;
        }
        else {
            service_rate = SystemConfiguration.CLOUDLET_M2;
            stream = 3;
        }
        return InitGenerator.getInstance().exponential(service_rate, stream);
    }


    // OTHER


    public double getJobOperations(){
        return InitGenerator.getInstance().exponential(1, 6);
    }












}
