package cloudlet;

import cloud.Cloud;
import event.Event;
import statistics.JobStatistics;
import system.SystemConfiguration;

@SuppressWarnings("Duplicates")
public class DispatchAlgorithm {

    private int algorithmType;
    private int numberOfServers;
    private static JobStatistics jobStatistics;
    private static DispatchAlgorithm instance;


    private DispatchAlgorithm(){
        algorithmType = SystemConfiguration.ALGORITHM;
        numberOfServers = SystemConfiguration.N;
        jobStatistics = JobStatistics.getInstance();
    }

    public static DispatchAlgorithm getInstance(){
        if(instance == null)
            instance = new DispatchAlgorithm();
        return instance;
    }

    public void getAlgorithm(Event e){
        switch (algorithmType){
            case 1: defaultAlgorithm(e);
                break;
            case 2: sizeBasedBAlgorithm(e);
                break;
            case 3: firstClassinCloudletAlgorithm(e);
                break;
            case 4: thresholdAlgorithm(e, SystemConfiguration.THRESHOLD);
                break;
        }
    }


    /**
     * Basic algoritm
     */
    public void defaultAlgorithm(Event e){
        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().getJobsInCloudlet();
        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];

        if(totalJobsInCloudlet == numberOfServers){
            Cloud.getInstance().processArrival(e);
        }
        else{
            Cloudlet.getInstance().processArrival(e);
        }
    }



    /**
     * Threshold based algorithm
     */
    public void thresholdAlgorithm(Event e, int soglia){
        int jobClass = e.getJob().getJobClass();
        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().getJobsInCloudlet();
        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];


        /**
         * in cloud se n1 + n2 > soglia e classe == 2 || n1 + n2 > server
         */
        if((totalJobsInCloudlet >= numberOfServers) || (totalJobsInCloudlet > soglia && jobClass == 2)){
            if(totalJobsInCloudlet > soglia && jobClass == 2)
            Cloud.getInstance().processArrival(e);
        }else{
            Cloudlet.getInstance().processArrival(e);
        }
    }



    /**
     * Algorithm: only class1 jobs go into cloudlet
     */
    public void firstClassinCloudletAlgorithm(Event e){
        int jobClass = e.getJob().getJobClass();
        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().getJobsInCloudlet();
        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];

        if(totalJobsInCloudlet >= numberOfServers || jobClass == 2){
            Cloud.getInstance().processArrival(e);
        }else{
            Cloudlet.getInstance().processArrival(e);
        }
    }



    /**
     * Size-based algorithm
     */
    public void sizeBasedBAlgorithm(Event e){
        int jobClass = e.getJob().getJobClass();
        double operations = e.getJob().getOperations();
        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().getJobsInCloudlet();
        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];

        if(totalJobsInCloudlet >= numberOfServers || (jobClass == 2 && operations > 1.0) || (jobClass == 1 && operations > 1)){
            Cloud.getInstance().processArrival(e);
        }else{
            Cloudlet.getInstance().processArrival(e);
        }
    }

}

