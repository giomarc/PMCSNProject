package runners.simulation;

import cloudlet.Cloudlet;
import variablesGenerator.Arrivals;

public class StatisticsGenerator {

    private double packetloss;
    private double allpackets;
    private static StatisticsGenerator instance = null;
    private double globalTime;
    private double packet1;
    private double packet2;


    private StatisticsGenerator(){
        this.allpackets = 0.0;
        this.packetloss = 0.0;
        this.globalTime = 0.0;
        this.packet1 = 0.0;
        this.packet2 = 0.0;
    }

    public static StatisticsGenerator getInstance(){
        if(instance == null){
           instance = new StatisticsGenerator();
        }
        return instance;
    }

    public double calculatePLoss(){
        System.out.println(packet1/allpackets);
        System.out.println(packet2/allpackets);
        return getPacketloss()/getAllpackets();
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
        return Arrivals.getInstance().getTotalRate()*(1-calculatePLoss());
    }

    public double getSecondThroughput(double globalTime){
        return (allpackets - packetloss)/globalTime;
    }

    public double getGlobalTime() {
        return globalTime;
    }

    public void setGlobalTime(double globalTime) {
        this.globalTime = globalTime;
    }

    public void increasePacket1(){
        this.packet1++;
    }

    public void increasePacket2(){
        this.packet2++;
    }
}
