package cloudlet;

import event.Event;
import runners.simulation.StatisticsGenerator;

public class CloudletController {

    private static CloudletController instance = new CloudletController();
    private double allPackets;
    private double packetLoss;
    private int numberOfServers;
    private StatisticsGenerator statistics;


    private CloudletController(){
        allPackets = 0;
        packetLoss = 0;
        numberOfServers = Cloudlet.getInstance().getNumberOfServers();
        statistics = StatisticsGenerator.getInstance();
    }

    public static CloudletController getInstance(){
        return instance;
    }

    public void dispatchArrivals(Event e){
        statistics.increaseAllPackets();
        double arrivalTime = e.getJob().getArrivalTime();

        if(Cloudlet.getInstance().numberOfJobsInCloudlet(arrivalTime) >= numberOfServers){
            statistics.increasePacketLoss();
            //Cloud.processArrival(e);
        }
        else{
            Cloudlet.getInstance().processArrival(e);
        }
    }
}
