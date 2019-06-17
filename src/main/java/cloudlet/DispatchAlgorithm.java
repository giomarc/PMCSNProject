package cloudlet;

import cloud.Cloud;
import event.Event;
import statistics.JobStatistics;
import system.SystemConfiguration;

@SuppressWarnings("Duplicates")
public class DispatchAlgorithm {

    private int algorithmType;
    private int numberOfServers;
    private static DispatchAlgorithm instance;

    private DispatchAlgorithm(){
        algorithmType = SystemConfiguration.ALGORITHM;
        numberOfServers = SystemConfiguration.N;
    }

    public static DispatchAlgorithm getInstance(){
        if(instance == null)
            instance = new DispatchAlgorithm();
        return instance;
    }

    /**
     * Choose the correct algorithm depending on what is written in the configuration file
     * @param e: arrival event
     */
    void getAlgorithm(Event e){
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
     * default algorithm. If Cloudlet is full then send the packet to the Cloud
     * @param e: arrival event
     */
    private void defaultAlgorithm(Event e){
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
     * threshold algorithm: if n1 + n2 > soglia and class == 2 || n1 + n2 > server then send to the cloud
     * @param e: arrival event
     * @param soglia
     */
    private void thresholdAlgorithm(Event e, int soglia){
        int jobClass = e.getJob().getJobClass();
        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().getJobsInCloudlet();
        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];

        if((totalJobsInCloudlet >= numberOfServers) || (totalJobsInCloudlet > soglia && jobClass == 2)){
            if(totalJobsInCloudlet > soglia && jobClass == 2)
            Cloud.getInstance().processArrival(e);
        }else{
            Cloudlet.getInstance().processArrival(e);
        }
    }

    /**
     * First-Class algorithm: if totalJobsInCloudlet >= numberOfServers || jobClass == 2 then send tto the cloud
     * @param e: arrival event
     */
    private void firstClassinCloudletAlgorithm(Event e){
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
     * Size based algorithm: if (class 1 & Size_1 > E[S_1]) or (class 2 & Size_2 > E[S_2]) and if the cloudlet is full,
     * then send it on the cloud
     * @param e: arrival event
     */
    private void sizeBasedBAlgorithm(Event e){
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

