package runners.simulation;

import event.Event;
import variablesGenerator.Arrivals;

public class StatisticsGenerator {

    private double packetloss;
    private double allpackets;
    private static StatisticsGenerator instance = null;
    private double globalTime;
    private double packet1;
    private double packet2;
    private double completedCloudlet;
    private double completedCloud;

    //DA COMPLETARE CON WELFORD
    private double meanServiceTime;
    private double meanServiceTimeClass1;
    private double meanServiceTimeClass2;
    private double meanServiceTimeClass1Cloudlet;
    private double meanServiceTimeClass2Cloudlet;
    private double meanServiceTimeClass1Cloud;
    private double meanServiceTimeClass2Cloud;



    private StatisticsGenerator(){
        this.allpackets                     = 0.0;
        this.packetloss                     = 0.0;
        this.globalTime                     = 0.0;
        this.packet1                        = 0.0;
        this.packet2                        = 0.0;
        this.completedCloud                 = 0.0;
        this.completedCloudlet              = 0.0;
        this.meanServiceTime                = 0.0;
        this.meanServiceTimeClass1          = 0.0;
        this.meanServiceTimeClass2          = 0.0;
        this.meanServiceTimeClass1Cloudlet  = 0.0;
        this.meanServiceTimeClass2Cloudlet  = 0.0;
        this.meanServiceTimeClass1Cloud     = 0.0;
        this.meanServiceTimeClass2Cloud     = 0.0;

    }

    public static StatisticsGenerator getInstance(){
        if(instance == null){
           instance = new StatisticsGenerator();
        }
        return instance;
    }

    public double calculatePLoss(){
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

    public double getAnalyticCloudletThroughput(){
        return Arrivals.getInstance().getTotalRate()*(1-calculatePLoss());
    }

    public double getEmpiricCloudletThroughput(){
        return (completedCloudlet)/globalTime;
    }

    public double getAnalyticCloudThroughput(){
        return Arrivals.getInstance().getTotalRate()*(calculatePLoss());
    }

    public double getEmpiricCloudThroughput(){
        return (completedCloud)/globalTime;
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

    public void receiveCompletion(Event e){
        if(e.getType() == 1)
            completedCloudlet++;
        else if(e.getType() == 2)
            completedCloud++;
        else
            System.exit(-1);
        int jobClass = e.getJob().getJobClass();
        double serviceTime = e.getJob().getServiceTime();
        if(jobClass == 1)
            increasePacket1();
        else if(jobClass == 2)
            increasePacket2();
        else
            System.exit(-1);

    }

    public double getPacket1() {
        return packet1;
    }

    public double getPacket2() {
        return packet2;
    }

    public double getMeanServiceTime() {
        return meanServiceTime;
    }

    public double getMeanServiceTimeClass1() {
        return meanServiceTimeClass1;
    }

    public double getMeanServiceTimeClass2() {
        return meanServiceTimeClass2;
    }

    public double getMeanServiceTimeClass1Cloudlet() {
        return meanServiceTimeClass1Cloudlet;
    }

    public double getMeanServiceTimeClass2Cloudlet() {
        return meanServiceTimeClass2Cloudlet;
    }

    public double getMeanServiceTimeClass1Cloud() {
        return meanServiceTimeClass1Cloud;
    }

    public double getMeanServiceTimeClass2Cloud() {
        return meanServiceTimeClass2Cloud;
    }
}
