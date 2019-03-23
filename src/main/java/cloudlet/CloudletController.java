package cloudlet;

import cloud.Cloud;
import event.Event;
import runners.Statistics.JobStatistics;

public class CloudletController {

    private static CloudletController instance = new CloudletController();
    private int numberOfServers;
    private JobStatistics jobStatistics;
    private int iterations;


    private CloudletController(){
        numberOfServers = Cloudlet.getInstance().getNumberOfServers();
        jobStatistics = JobStatistics.getInstance();
        iterations = 1;
    }

    public static CloudletController getInstance(){
        return instance;
    }

    public void dispatchArrivals(Event e){
        jobStatistics.increaseAllPackets();
        double arrivalTime = e.getJob().getArrivalTime();
        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().numberOfJobsInCloudlet(arrivalTime);
        //jobStatistics.calculateSampleMean(numberOfJobsInCloudlet[1] + numberOfJobsInCloudlet[2],iterations);
        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];
        iterations++;
        if(totalJobsInCloudlet >= numberOfServers){
            jobStatistics.increasePacketLoss();
            Cloud.getInstance().processArrival(e);
            jobStatistics.updatePopulationMeans(2, e.getJob().getJobClass(), totalJobsInCloudlet, 0, numberOfJobsInCloudlet[0], 0 , numberOfJobsInCloudlet[1], 0);
        }else{
            Cloudlet.getInstance().processArrival(e);
            jobStatistics.updatePopulationMeans(1, e.getJob().getJobClass(), totalJobsInCloudlet, 0, numberOfJobsInCloudlet[0], 0 , numberOfJobsInCloudlet[1], 0);
        }


    }

    public double endSimulation(){
        double cloudletEndTime = Cloudlet.getInstance().endSimulation();
        double cloudEndTime = Cloud.getInstance().endSimulation();
        return Math.max(cloudletEndTime, cloudEndTime);
    }


    public int getIterations(){
        return iterations;
    }

    public void increaseIterations(){
        iterations++;
    }

    public void reset(){
        numberOfServers = Cloudlet.getInstance().getNumberOfServers();
        jobStatistics = JobStatistics.getInstance();
        iterations = 1;
    }
}
