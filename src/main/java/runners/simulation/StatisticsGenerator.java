package runners.simulation;

import event.Event;
import variablesGenerator.Arrivals;

public class StatisticsGenerator {

    private double packetloss;
    private double allpackets;
    private static StatisticsGenerator instance = null;
    private double globalTime;

    private double completedCloudlet;
    private double completedCloud;

    //POPULATION
    private double completedCloudletClass1;
    private double completedCloudletClass2;
    private double completedCloudClass1;
    private double completedCloudClass2;
    private long arrivedCloudletClass1;
    private long arrivedCloudletClass2;
    private long arrivedCloudClass1;
    private long arrivedCloudClass2;

    //RESPONSE TIME
    private double meanResponseTime;
    private double meanResponseTimeCloudlet;
    private double meanResponseTimeCloud;
    private double meanResponseTimeClass1;
    private double meanResponseTimeClass2;
    private double meanResponseTimeClass1Cloudlet;
    private double meanResponseTimeClass2Cloudlet;
    private double meanResponseTimeClass1Cloud;
    private double meanResponseTimeClass2Cloud;

    //POPULATION
    private double meanGlobalPopulation;
    private double meanCloudletPopulation;
    private double meanCloudPopulation;
    private double meanGlobalPopulationClass1;
    private double meanCloudletPopulationClass1;
    private double meanCloudPopulationClass1;
    private double meanGlobalPopulationClass2;
    private double meanCloudletPopulationClass2;
    private double meanCloudPopulationClass2;


    private double sampleMean;
    private double variance;
    private int iterations;


    public double getMeanResponseTime() {
        return meanResponseTime;
    }

    public double getMeanResponseTimeCloudlet() {
        return meanResponseTimeCloudlet;
    }

    public double getMeanResponseTimeCloud() {
        return meanResponseTimeCloud;
    }

    public double getMeanResponseTimeClass1() {
        return meanResponseTimeClass1;
    }

    public double getMeanResponseTimeClass2() {
        return meanResponseTimeClass2;
    }

    public double getMeanResponseTimeClass1Cloudlet() {
        return meanResponseTimeClass1Cloudlet;
    }

    public double getMeanResponseTimeClass2Cloudlet() {
        return meanResponseTimeClass2Cloudlet;
    }

    public double getMeanResponseTimeClass1Cloud() {
        return meanResponseTimeClass1Cloud;
    }

    public double getMeanResponseTimeClass2Cloud() {
        return meanResponseTimeClass2Cloud;
    }

    private StatisticsGenerator(){
        this.allpackets                     = 0.0;
        this.packetloss                     = 0.0;
        this.globalTime                     = 0.0;
        this.completedCloudletClass1        = 0.0;
        this.completedCloudClass1           = 0.0;
        this.completedCloudletClass2        = 0.0;
        this.completedCloudClass2           = 0.0;
        this.arrivedCloudletClass1          = 0;
        this.arrivedCloudletClass2          = 0;
        this.arrivedCloudClass1             = 0;
        this.arrivedCloudClass2             = 0;
        this.completedCloud                 = 0.0;
        this.completedCloudlet              = 0.0;
        this.meanResponseTime               = 0.0;
        this.meanResponseTimeCloudlet       = 0.0;
        this.meanResponseTimeCloud          = 0.0;
        this.meanResponseTimeClass1         = 0.0;
        this.meanResponseTimeClass2         = 0.0;
        this.meanResponseTimeClass1Cloudlet = 0.0;
        this.meanResponseTimeClass2Cloudlet = 0.0;
        this.meanResponseTimeClass1Cloud    = 0.0;
        this.meanResponseTimeClass2Cloud    = 0.0;
        this.sampleMean                     = 0.0;
        this.variance                       = 0.0;
        this.iterations                     = 0;
        this.meanGlobalPopulation           = 0;
        this.meanCloudletPopulation         = 0;
        this.meanCloudPopulation            = 0;
        this.meanGlobalPopulationClass1     = 0;
        this.meanCloudletPopulationClass1   = 0;
        this.meanCloudPopulationClass1      = 0;
        this.meanGlobalPopulationClass2     = 0;
        this.meanCloudletPopulationClass2   = 0;
        this.meanCloudPopulationClass2      = 0;
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
        double i = (double) n;
        calculateVariance(diff, i);
        sampleMean = sampleMean + (diff / i);
    }


    /**
     * calculateSampleMean VERSION 2
     */
    private double welfordMean(double valueToUpdate, double currentValueToInsert, long n)
    {
        double diff = (currentValueToInsert -  valueToUpdate);
        double i = (double) n;
        if(i!=0)
            valueToUpdate = valueToUpdate + (diff / i);
        return valueToUpdate;
    }

    /**
     *
     * @param diff: difference between sample mean ad actual value
     * @param n: number of iteration
     */
    private void calculateVariance(double diff, double n){
        variance = variance + ((diff*diff)*((n-1)/n));
        iterations = (int) n;
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

    public void increasePacket1Cloudlet(){
        this.completedCloudletClass1++;
    }

    public void increasePacket2Cloudlet(){
        this.completedCloudletClass2++;
    }

    public void increasePacket1Cloud(){
        this.completedCloudClass1++;
    }

    public void increasePacket2Cloud(){
        this.completedCloudClass2++;
    }


    public void receiveCompletion(Event e){
        int jobClass = e.getJob().getJobClass();
        double serviceTime = e.getJob().getServiceTime();
        if(e.getType() == 1) {//cloudlet
            completedCloudlet++;
            meanResponseTimeCloudlet = welfordMean(meanResponseTimeCloudlet, serviceTime, (int) completedCloudlet);
            if(jobClass == 1){
                increasePacket1Cloudlet();
                meanResponseTimeClass1Cloudlet = welfordMean(meanResponseTimeClass1Cloudlet, serviceTime, (int) completedCloudletClass1);
            }
            else if(jobClass == 2){
                increasePacket2Cloudlet();
                meanResponseTimeClass2Cloudlet = welfordMean(meanResponseTimeClass2Cloudlet, serviceTime, (int) completedCloudletClass2);
            }
        }
        else if(e.getType() == 2) {     //cloud
            completedCloud++;
            meanResponseTimeCloud = welfordMean(meanResponseTimeCloud, serviceTime, (int) completedCloud);
            if(jobClass == 1){
                increasePacket1Cloud();
                meanResponseTimeClass1Cloud = welfordMean(meanResponseTimeClass1Cloud, serviceTime, (int) completedCloudClass1);
            }
            else if(jobClass == 2){
                increasePacket2Cloud();
                meanResponseTimeClass2Cloud = welfordMean(meanResponseTimeClass2Cloud, serviceTime, (int) completedCloudClass2);
            }
        }
        else
            System.exit(-1);
        if(jobClass == 1)
            meanResponseTimeClass1 = welfordMean(meanResponseTimeClass1, serviceTime, (int) (completedCloudletClass1 + completedCloudClass1));
        else if(jobClass == 2)
            meanResponseTimeClass2 = welfordMean(meanResponseTimeClass2, serviceTime, (int) (completedCloudletClass2 + completedCloudClass2));

        meanResponseTime = welfordMean(meanResponseTime, serviceTime, (int) (completedCloudletClass1 + completedCloudletClass2 + completedCloudClass1 + completedCloudClass2));
    }

    public void updatePopulationMeans(int type,
                                      int jobClass,
                                      int cloudletPopulation,
                                      int cloudPopulation,
                                      int cloudletPopulationClass1,
                                      int cloudPopulationClass1,
                                      int cloudletPopulationClass2,
                                      int cloudPopulationClass2){

        // aggiornamento della popolazione media senza considerare la classe
        long globalIteration = (long) (arrivedCloudClass1 + completedCloudClass2 + completedCloudletClass1 + completedCloudletClass2);
        this.meanGlobalPopulation = welfordMean(this.meanGlobalPopulation, cloudletPopulation + cloudPopulation, globalIteration);
        if(type == 1){
            long iterationValueCloudlet = (int) (completedCloudletClass1 + completedCloudletClass2);
            this.meanCloudletPopulation = welfordMean(this.meanCloudletPopulation, cloudletPopulation, iterationValueCloudlet);
        }
        else if(type == 2){
            long iterationValueCloud = (int) (completedCloudClass1 + completedCloudClass2);
            this.meanCloudPopulation = welfordMean(this.meanCloudPopulation, cloudPopulation, iterationValueCloud);
        }

        //aggiornamento della popolazione media per la classe 1
        if(jobClass == 1) {
            if (type == 1) {
                long iterationCloudletClass1 = (long) completedCloudletClass1;
                this.meanCloudletPopulationClass1 = welfordMean(this.meanCloudletPopulationClass1, cloudletPopulationClass1, iterationCloudletClass1);
            } else if (type == 2) {
                long iterationCloudClass1 = (long) completedCloudClass1;
                this.meanCloudPopulationClass1 = welfordMean(this.meanCloudPopulationClass1, cloudPopulationClass1, iterationCloudClass1);
            }
        }

        //aggiornamento della popolazione media per la classe 2
        else if(jobClass == 2){
            if(type == 1){
                long iterationCloudletClass2 = (long) completedCloudletClass2;
                this.meanCloudletPopulationClass2 = welfordMean(this.meanCloudletPopulationClass2, cloudletPopulationClass2, iterationCloudletClass2);
            }
            else if(type == 2){
                long iterationCloudClass2 = (long) completedCloudClass2;
                this.meanCloudPopulationClass2 = welfordMean(this.meanCloudPopulationClass2, cloudPopulationClass2, iterationCloudClass2);
            }
        }
    }

    public double getPacket1() {
        return completedCloudletClass1 + completedCloudClass1;
    }

    public double getPacket2() {
        return completedCloudletClass2 + completedCloudClass2;
    }

    public double getMeanGlobalPopulation() {
        return meanGlobalPopulation;
    }

    public double getMeanCloudletPopulation() {
        return meanCloudletPopulation;
    }

    public double getMeanCloudPopulation() {
        return meanCloudPopulation;
    }

    public double getMeanGlobalPopulationClass1() {
        return meanGlobalPopulationClass1;
    }

    public double getMeanCloudletPopulationClass1() {
        return meanCloudletPopulationClass1;
    }

    public double getMeanCloudPopulationClass1() {
        return meanCloudPopulationClass1;
    }

    public double getMeanGlobalPopulationClass2() {
        return meanGlobalPopulationClass2;
    }

    public double getMeanCloudletPopulationClass2() {
        return meanCloudletPopulationClass2;
    }

    public double getMeanCloudPopulationClass2() {
        return meanCloudPopulationClass2;
    }

    public void printSampleMean(){
        System.out.println("Sample mean: " + sampleMean);
    }

    public void printVariance(){
        System.out.println("Variance: " + variance/iterations);
    }


    public void resetStatistics(){
        this.allpackets                     = 0.0;
        this.packetloss                     = 0.0;
        this.globalTime                     = 0.0;
        this.completedCloudletClass1 = 0.0;
        this.completedCloudClass1 = 0.0;
        this.completedCloudletClass2 = 0.0;
        this.completedCloudClass2 = 0.0;
        this.completedCloud                 = 0.0;
        this.completedCloudlet              = 0.0;
        this.meanResponseTime                = 0.0;
        this.meanResponseTimeCloudlet        = 0.0;
        this.meanResponseTimeCloud           = 0.0;
        this.meanResponseTimeClass1          = 0.0;
        this.meanResponseTimeClass2          = 0.0;
        this.meanResponseTimeClass1Cloudlet  = 0.0;
        this.meanResponseTimeClass2Cloudlet  = 0.0;
        this.meanResponseTimeClass1Cloud     = 0.0;
        this.meanResponseTimeClass2Cloud     = 0.0;
        this.sampleMean = 0.0;
        this.variance = 0.0;
    }
}
