package cloudlet;

import cloud.Cloud;
import event.Event;
import runners.Statistics.JobStatistics;
import system.SystemConfiguration;
import variablesGenerator.Services;

public class CloudletController {

    private static CloudletController instance = new CloudletController();
    private int numberOfServers;
    private JobStatistics jobStatistics;


    private CloudletController(){
        numberOfServers = Cloudlet.getInstance().getNumberOfServers();
        jobStatistics = JobStatistics.getInstance();
    }

    public static CloudletController getInstance(){
        return instance;
    }


    public void dispatchArrivals(Event e){
        int algorithm = SystemConfiguration.ALGORITHM;
        switch (algorithm){
            case 1: dispatchArrivalsStandard(e);
                    break;
            case 2: dispatchArrivalsSBAlgorithm(e);
                    break;
            case 3: dispatchArrivalsFirstClassinCloudletAlgorithm(e);
                    break;
            case 4: dispatchArrivalsThresholdAlgorithm(e, SystemConfiguration.THRESHOLD);
                    break;
        }
    }

    @SuppressWarnings("Duplicates")
    public void dispatchArrivalsStandard(Event e){
        int jobClass = e.getJob().getJobClass();
        double arrivalTime = e.getJob().getArrivalTime();
        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().numberOfJobsInCloudlet(arrivalTime);
        int[] numberOfJobsInCloud = Cloud.getInstance().numberOfJobsInCloudlet(arrivalTime);

        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];
        if(totalJobsInCloudlet >= numberOfServers){
            if(jobClass == 1)
                jobStatistics.increaseArrivedCloudClass1();
            else
                jobStatistics.increaseArrivedCloudClass2();
            Cloud.getInstance().processArrival(e);
            jobStatistics.updatePopulationMeans(2, e.getJob().getJobClass(), numberOfJobsInCloudlet[0], numberOfJobsInCloud[0] , numberOfJobsInCloudlet[1], numberOfJobsInCloud[1]);
        }else{
            if(jobClass == 1)
                jobStatistics.increaseArrivedCloudletClass1();
            else
                jobStatistics.increaseArrivedCloudletClass2();
            Cloudlet.getInstance().processArrival(e);
        }
        jobStatistics.updatePopulationMeans(1, e.getJob().getJobClass(), numberOfJobsInCloudlet[0], numberOfJobsInCloud[0] , numberOfJobsInCloudlet[1], numberOfJobsInCloud[1]);
    }


    /**
     * job classe 1 nel coudlet
     * @param e
     */
    @SuppressWarnings("Duplicates")
    public void dispatchArrivalsFirstClassinCloudletAlgorithm(Event e){
        int jobClass = e.getJob().getJobClass();
        double arrivalTime = e.getJob().getArrivalTime();
        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().numberOfJobsInCloudlet(arrivalTime);
        int[] numberOfJobsInCloud = Cloud.getInstance().numberOfJobsInCloudlet(arrivalTime);
        double operations = e.getJob().getOperations();

        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];
        if(totalJobsInCloudlet >= numberOfServers || jobClass == 2){
            if(jobClass == 1)
                jobStatistics.increaseArrivedCloudClass1();
            else
                jobStatistics.increaseArrivedCloudClass2();
            Cloud.getInstance().processArrival(e);
            jobStatistics.updatePopulationMeans(2, e.getJob().getJobClass(), numberOfJobsInCloudlet[0], numberOfJobsInCloud[0] , numberOfJobsInCloudlet[1], numberOfJobsInCloud[1]);
        }else{
            if(jobClass == 1)
                jobStatistics.increaseArrivedCloudletClass1();
            else
                jobStatistics.increaseArrivedCloudletClass2();
            Cloudlet.getInstance().processArrival(e);
        }
        jobStatistics.updatePopulationMeans(1, e.getJob().getJobClass(), numberOfJobsInCloudlet[0], numberOfJobsInCloud[0] , numberOfJobsInCloudlet[1], numberOfJobsInCloud[1]);
    }


    /**
     *
     */
    @SuppressWarnings("Duplicates")
    public void dispatchArrivalsSBAlgorithm(Event e){
        int jobClass = e.getJob().getJobClass();
        double arrivalTime = e.getJob().getArrivalTime();
        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().numberOfJobsInCloudlet(arrivalTime);
        int[] numberOfJobsInCloud = Cloud.getInstance().numberOfJobsInCloudlet(arrivalTime);
        double operations = e.getJob().getOperations();

        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];
        if(totalJobsInCloudlet >= numberOfServers || (jobClass == 2 && operations > 0.6) || (jobClass == 1 && operations > 1)){
            if(jobClass == 1)
                jobStatistics.increaseArrivedCloudClass1();
            else
                jobStatistics.increaseArrivedCloudClass2();
            Cloud.getInstance().processArrival(e);
            jobStatistics.updatePopulationMeans(2, e.getJob().getJobClass(), numberOfJobsInCloudlet[0], numberOfJobsInCloud[0] , numberOfJobsInCloudlet[1], numberOfJobsInCloud[1]);
        }else{
            if(jobClass == 1)
                jobStatistics.increaseArrivedCloudletClass1();
            else
                jobStatistics.increaseArrivedCloudletClass2();
            Cloudlet.getInstance().processArrival(e);
        }
        jobStatistics.updatePopulationMeans(1, e.getJob().getJobClass(), numberOfJobsInCloudlet[0], numberOfJobsInCloud[0] , numberOfJobsInCloudlet[1], numberOfJobsInCloud[1]);
    }


    @SuppressWarnings("Duplicates")
    public void dispatchArrivalsThresholdAlgorithm(Event e, int soglia){
        int jobClass = e.getJob().getJobClass();
        double arrivalTime = e.getJob().getArrivalTime();
        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().numberOfJobsInCloudlet(arrivalTime);
        int[] numberOfJobsInCloud = Cloud.getInstance().numberOfJobsInCloudlet(arrivalTime);
        double operations = e.getJob().getOperations();

        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];

        /**
         * in cloud se n1 + n2 > soglia e classe == 2 || n1 + n2 > server
         *
         */


        if((totalJobsInCloudlet >= numberOfServers) || (totalJobsInCloudlet > soglia && jobClass == 2)){
            if(jobClass == 1)
                jobStatistics.increaseArrivedCloudClass1();
            else
                jobStatistics.increaseArrivedCloudClass2();
            Cloud.getInstance().processArrival(e);
            jobStatistics.updatePopulationMeans(2, e.getJob().getJobClass(), numberOfJobsInCloudlet[0], numberOfJobsInCloud[0] , numberOfJobsInCloudlet[1], numberOfJobsInCloud[1]);
        }else{
            if(jobClass == 1)
                jobStatistics.increaseArrivedCloudletClass1();
            else
                jobStatistics.increaseArrivedCloudletClass2();
            Cloudlet.getInstance().processArrival(e);
        }
        jobStatistics.updatePopulationMeans(1, e.getJob().getJobClass(), numberOfJobsInCloudlet[0], numberOfJobsInCloud[0] , numberOfJobsInCloudlet[1], numberOfJobsInCloud[1]);
    }

    public double endSimulation(){
        double cloudletEndTime = Cloudlet.getInstance().endSimulation();
        double cloudEndTime = Cloud.getInstance().endSimulation();
        return Math.max(cloudletEndTime, cloudEndTime);
    }

    public void reset(){
        numberOfServers = Cloudlet.getInstance().getNumberOfServers();
        jobStatistics = JobStatistics.getInstance();
    }
}
