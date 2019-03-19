package runners.simulation;

import event.Event;
import variablesGenerator.Arrivals;

public class StatisticsGenerator {

    private double packetloss;
    private double allpackets;
    private static StatisticsGenerator instance = null;
    private double globalTime;
    private double packet1Cloudlet;
    private double packet2Cloudlet;
    private double packet1Cloud;
    private double packet2Cloud;
    private double completedCloudlet;
    private double completedCloud;

    //DA COMPLETARE CON WELFORD
    private double meanServiceTime;
    private double meanServiceTimeCloudlet;
    private double meanServiceTimeCloud;
    private double meanServiceTimeClass1;
    private double meanServiceTimeClass2;
    private double meanServiceTimeClass1Cloudlet;
    private double meanServiceTimeClass2Cloudlet;
    private double meanServiceTimeClass1Cloud;
    private double meanServiceTimeClass2Cloud;

    private double sampleMean;
    private double variance;
    private int iterations;



    private StatisticsGenerator(){
        this.allpackets                     = 0.0;
        this.packetloss                     = 0.0;
        this.globalTime                     = 0.0;
        this.packet1Cloudlet                = 0.0;
        this.packet1Cloud                   = 0.0;
        this.packet2Cloudlet                = 0.0;
        this.packet2Cloud                   = 0.0;
        this.completedCloud                 = 0.0;
        this.completedCloudlet              = 0.0;
        this.meanServiceTime                = 0.0;
        this.meanServiceTimeCloudlet        = 0.0;
        this.meanServiceTimeCloud           = 0.0;
        this.meanServiceTimeClass1          = 0.0;
        this.meanServiceTimeClass2          = 0.0;
        this.meanServiceTimeClass1Cloudlet  = 0.0;
        this.meanServiceTimeClass2Cloudlet  = 0.0;
        this.meanServiceTimeClass1Cloud     = 0.0;
        this.meanServiceTimeClass2Cloud     = 0.0;
        this.sampleMean = 0.0;
        this.variance = 0.0;

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

    /**
     * sample mean basing on Welford's algorithm
     */
    public void calculateSampleMean(double actualValue, int n)
    {
        double diff = (actualValue - sampleMean);
        double i = Double.valueOf(n);
        calculateVariance(diff, i);
        sampleMean = sampleMean + (diff / i);
    }

    /**
     * calculateSampleMean VERSION 2
     */
    private double welfordMean(double valueToUpdate, double currentValueToInsert, int n)
    {
        double diff = (currentValueToInsert -  valueToUpdate);
        valueToUpdate = valueToUpdate + (diff / n);
        return valueToUpdate;
    }

    /**
     *
     * @param diff: difference between sample mean ad actual value
     * @param n: number of iteration
     */
    public void calculateVariance(double diff, double n){
        variance = variance + ((diff*diff)*((n-1)/n));
        iterations = (int) n;
    }
    /**
     * Getter and Setter
     *
     */

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

    public void increasePacket1Cloudlet(){
        this.packet1Cloudlet++;
    }

    public void increasePacket2Cloudlet(){
        this.packet2Cloudlet++;
    }
    public void increasePacket1Cloud(){
        this.packet1Cloud++;
    }

    public void increasePacket2Cloud(){
        this.packet2Cloud++;
    }


    public void receiveCompletion(Event e){
        int jobClass = e.getJob().getJobClass();
        double serviceTime = e.getJob().getServiceTime();
        if(e.getType() == 1) {          //cloudlet
            completedCloudlet++;
            meanServiceTimeCloudlet = welfordMean(meanServiceTimeCloudlet, serviceTime, (int) completedCloudlet);
            if(jobClass == 1){
                increasePacket1Cloudlet();
                meanServiceTimeClass1Cloudlet = welfordMean(meanServiceTimeClass1Cloudlet, serviceTime, (int) packet1Cloudlet);
            }
            else if(jobClass == 2){
                increasePacket2Cloudlet();
                meanServiceTimeClass2Cloudlet = welfordMean(meanServiceTimeClass2Cloudlet, serviceTime, (int) packet2Cloudlet);
            }
        }
        else if(e.getType() == 2) {     //cloud
            completedCloud++;
            meanServiceTimeCloud = welfordMean(meanServiceTimeCloud, serviceTime, (int) completedCloud);
            if(jobClass == 1){
                increasePacket1Cloud();
                meanServiceTimeClass1Cloud = welfordMean(meanServiceTimeClass1Cloud, serviceTime, (int) packet1Cloud);
            }
            else if(jobClass == 2){
                increasePacket2Cloud();
                meanServiceTimeClass2Cloud = welfordMean(meanServiceTimeClass2Cloud, serviceTime, (int) packet2Cloud);
            }
        }
        else
            System.exit(-1);
        if(jobClass == 1)
            meanServiceTimeClass1 = welfordMean(meanServiceTimeClass1, serviceTime, (int) (packet1Cloudlet + packet1Cloud));
        else if(jobClass == 2)
            meanServiceTimeClass2 = welfordMean(meanServiceTimeClass2, serviceTime, (int) (packet2Cloudlet + packet2Cloud));

        meanServiceTime = welfordMean(meanServiceTime, serviceTime, (int) (packet1Cloudlet + packet2Cloudlet + packet1Cloudlet + packet2Cloud));
    }

    public double getPacket1() {
        return packet1Cloudlet + packet1Cloud;
    }

    public double getPacket2() {
        return packet2Cloudlet + packet2Cloud;
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

    public void printSampleMean(){
        System.out.println("Sample mean: " + sampleMean);
    }

    public void printVariance(){
        System.out.println("Variance: " + variance/iterations);
    }

    public double getMeanServiceTimeCloudlet() {
        return meanServiceTimeCloudlet;
    }

    public double getMeanServiceTimeCloud() {
        return meanServiceTimeCloud;
    }
}
