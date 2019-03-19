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
        double numberOfJobsInCloudlet = Cloudlet.getInstance().numberOfJobsInCloudlet(arrivalTime);
        if(numberOfJobsInCloudlet >= numberOfServers){
            statistics.increasePacketLoss();
            Cloud.getInstance().processArrival(e);
        }
        else{
            Cloudlet.getInstance().processArrival(e);
        }
        statistics.calculateSampleMean(numberOfJobsInCloudlet,iterations);
        iterations++;
    }

    public double endSimulation(){
        double cloudletEndTime = Cloudlet.getInstance().endSimulation();
        double cloudEndTime = Cloud.getInstance().endSimulation();
        return Math.max(cloudletEndTime, cloudEndTime);
    }


    public int getIterations(){
        return iterations;
    }
}
