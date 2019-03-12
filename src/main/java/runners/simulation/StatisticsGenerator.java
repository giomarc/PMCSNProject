package runners.simulation;

import cloudlet.Cloudlet;
import variablesGenerator.Arrivals;

public class StatisticsGenerator {

    double packetloss;
    double allpackets;
    private static StatisticsGenerator instance = null;

    private StatisticsGenerator(){
        this.allpackets = 0.0;
        this.packetloss = 0.0;
    }

    public static StatisticsGenerator getInstance(){
        if(instance == null){
           instance = new StatisticsGenerator();
        }
        return instance;
    }

    public double calculatePLoss(){
        double ploss = getPacketloss()/getAllpackets();
        return ploss;
    }

    public void increasePacketLoss(){
            this.packetloss ++;
    }

    public void increaseAllPackets(){
        this.allpackets ++;
    }

    public double getAllpackets(){
        return this.allpackets;
    }

    public double getPacketloss(){
        return this.packetloss;
    }

    public double getCloudletThroughput(){
        double X = Arrivals.getInstance().getTotalRate()*(1-calculatePLoss());
        return X;
    }

    public double getSecondThroughput(Cloudlet cloudlet){

        double X = (allpackets - packetloss)/ cloudlet.getSimulationTime();
        return X;
    }

}
