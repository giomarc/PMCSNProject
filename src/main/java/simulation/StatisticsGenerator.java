package simulation;

import cloud.Cloud;
import cloudlet.Cloudlet_NEW;
import variablesGenerator.Arrivals;

public class StatisticsGenerator {

    double packetloss;
    double allpackets;


    public StatisticsGenerator(){
        this.allpackets = 0.0;
        this.packetloss = 0.0;
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

    public double getSecondThroughput(Cloudlet_NEW cloudlet_new){

        double X = (allpackets - packetloss)/ cloudlet_new.getSimulationTime();
        return X;
    }

}
