package cloudlet;

import cloud.Cloud;
import event.Event;
import runners.simulation.StatisticsGenerator;

public class CloudletController {

    private static CloudletController instance = new CloudletController();
    private int numberOfServers;
    private StatisticsGenerator statistics;
    private int iterations;


    private CloudletController(){
        numberOfServers = Cloudlet.getInstance().getNumberOfServers();
        statistics = StatisticsGenerator.getInstance();
        iterations = 1;
    }

    public static CloudletController getInstance(){
        return instance;
    }

    public void dispatchArrivals(Event e){
        statistics.increaseAllPackets();
        double arrivalTime = e.getJob().getArrivalTime();
        int[] numberOfJobsInCloudlet = Cloudlet.getInstance().numberOfJobsInCloudlet(arrivalTime);
        //statistics.calculateSampleMean(numberOfJobsInCloudlet[1] + numberOfJobsInCloudlet[2],iterations);
        int totalJobsInCloudlet = numberOfJobsInCloudlet[0] + numberOfJobsInCloudlet[1];
        iterations++;
        if(totalJobsInCloudlet >= numberOfServers){
            statistics.increasePacketLoss();
            Cloud.getInstance().processArrival(e);
            statistics.updatePopulationMeans(2, e.getJob().getJobClass(), totalJobsInCloudlet, 0, numberOfJobsInCloudlet[0], 0 , numberOfJobsInCloudlet[1], 0);
        }else{
            Cloudlet.getInstance().processArrival(e);
            statistics.updatePopulationMeans(1, e.getJob().getJobClass(), totalJobsInCloudlet, 0, numberOfJobsInCloudlet[0], 0 , numberOfJobsInCloudlet[1], 0);
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
        statistics = StatisticsGenerator.getInstance();
        iterations = 1;
    }
}
