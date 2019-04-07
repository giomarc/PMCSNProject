package cloudlet;

import cloud.Cloud;
import event.Event;
import runners.Statistics.JobStatistics;
import system.SystemConfiguration;

import java.util.ArrayList;

public class CloudletController {

    private static CloudletController instance = new CloudletController();
    private int numberOfServers;
    private JobStatistics jobStatistics;

    private ArrayList<Integer> delete = new ArrayList<>();


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
        int cloudletOrCloud;

        int jobClass = e.getJob().getJobClass();
        double arrivalTime = e.getJob().getArrivalTime();

        Cloudlet.getInstance().timeHasPassed(arrivalTime);
        Cloud.getInstance().timeHasPassed(arrivalTime);

        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().getJobsInCloudlet();
        int[] numberOfJobsInCloud = Cloud.getInstance().returnJobsInCloud();
        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];


        if(totalJobsInCloudlet >= numberOfServers){
            cloudletOrCloud = 2;
            Cloud.getInstance().processArrival(e);
        }else{
            cloudletOrCloud = 1;
            Cloudlet.getInstance().processArrival(e);
        }
        jobStatistics.updatePopulationMeansTest(cloudletOrCloud, jobClass, numberOfJobsInCloudlet, numberOfJobsInCloud);
    }


    /**
     * job classe 1 nel coudlet
     * @param e
     */
    @SuppressWarnings("Duplicates")
    public void dispatchArrivalsFirstClassinCloudletAlgorithm(Event e){
        int cloudletOrCloud;

        int jobClass = e.getJob().getJobClass();
        double arrivalTime = e.getJob().getArrivalTime();
        Cloudlet.getInstance().timeHasPassed(arrivalTime);
        Cloud.getInstance().timeHasPassed(arrivalTime);

        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().getJobsInCloudlet();
        int[] numberOfJobsInCloud = Cloud.getInstance().returnJobsInCloud();
        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];
        if(totalJobsInCloudlet >= numberOfServers || jobClass == 2){
            cloudletOrCloud = 2;
            Cloud.getInstance().processArrival(e);
        }else{
            cloudletOrCloud = 1;
            Cloudlet.getInstance().processArrival(e);
        }
        jobStatistics.updatePopulationMeansTest(cloudletOrCloud, jobClass, numberOfJobsInCloudlet, numberOfJobsInCloud);
    }


    /**
     *
     */
    @SuppressWarnings("Duplicates")
    public void dispatchArrivalsSBAlgorithm(Event e){
        int cloudletOrCloud;
        int jobClass = e.getJob().getJobClass();
        double arrivalTime = e.getJob().getArrivalTime();
        Cloudlet.getInstance().timeHasPassed(arrivalTime);
        Cloud.getInstance().timeHasPassed(arrivalTime);
        double operations = e.getJob().getOperations();

        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().getJobsInCloudlet();
        int[] numberOfJobsInCloud = Cloud.getInstance().returnJobsInCloud();
        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];
        if(totalJobsInCloudlet >= numberOfServers || (jobClass == 2 && operations > 0.6) || (jobClass == 1 && operations > 1)){
            cloudletOrCloud = 2;
            Cloud.getInstance().processArrival(e);
        }else{
            cloudletOrCloud = 1;
            Cloudlet.getInstance().processArrival(e);
        }
        jobStatistics.updatePopulationMeansTest(cloudletOrCloud, jobClass, numberOfJobsInCloudlet, numberOfJobsInCloud);
    }


    @SuppressWarnings("Duplicates")
    public void dispatchArrivalsThresholdAlgorithm(Event e, int soglia){
        int cloudletOrCloud;
        int jobClass = e.getJob().getJobClass();
        double arrivalTime = e.getJob().getArrivalTime();
        Cloudlet.getInstance().timeHasPassed(arrivalTime);
        Cloud.getInstance().timeHasPassed(arrivalTime);
        double operations = e.getJob().getOperations();

        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().getJobsInCloudlet();
        int[] numberOfJobsInCloud = Cloud.getInstance().returnJobsInCloud();
        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];

        /**
         * in cloud se n1 + n2 > soglia e classe == 2 || n1 + n2 > server
         *
         */
        if((totalJobsInCloudlet >= numberOfServers) || (totalJobsInCloudlet > soglia && jobClass == 2)){
            cloudletOrCloud = 2;
            Cloud.getInstance().processArrival(e);
        }else{
            cloudletOrCloud = 1;
            Cloudlet.getInstance().processArrival(e);
        }
        jobStatistics.updatePopulationMeansTest(cloudletOrCloud, jobClass, numberOfJobsInCloudlet, numberOfJobsInCloud);
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
