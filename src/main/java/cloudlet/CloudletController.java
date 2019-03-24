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
        int jobClass = e.getJob().getJobClass();
        double arrivalTime = e.getJob().getArrivalTime();
        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().numberOfJobsInCloudlet(arrivalTime);
        int[] numberOfJobsInCloud = Cloud.getInstance().numberOfJobsInCloudlet(arrivalTime);

        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];
        int totalJobsInCloud = numberOfJobsInCloud[0] + numberOfJobsInCloud[1];
        iterations++;
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
            jobStatistics.updatePopulationMeans(1, e.getJob().getJobClass(), numberOfJobsInCloudlet[0], numberOfJobsInCloud[0] , numberOfJobsInCloudlet[1], numberOfJobsInCloud[1]);
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
