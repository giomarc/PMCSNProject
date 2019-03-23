package runners.Statistics;

import variablesGenerator.Arrivals;

public class JobStatistics {

    private static JobStatistics instance = null;
    private static Statistics statistics;

    private double packetloss;
    private double allpackets;
    private double globalTime;
    private long globalIteration;

    //POPULATION COUNTERS
    private double completedCloudletClass1;
    private double completedCloudletClass2;
    private double completedCloudClass1;
    private double completedCloudClass2;
    private long arrivedCloudletClass1;
    private long arrivedCloudletClass2;
    private long arrivedCloudClass1;
    private long arrivedCloudClass2;
    private double completedCloudlet;
    private double completedCloud;


    //POPULATION MEANS
    private double meanGlobalPopulation;
    private double meanCloudletPopulation;
    private double meanCloudPopulation;
    private double meanGlobalPopulationClass1;
    private double meanCloudletPopulationClass1;
    private double meanCloudPopulationClass1;
    private double meanGlobalPopulationClass2;
    private double meanCloudletPopulationClass2;
    private double meanCloudPopulationClass2;

    public JobStatistics(){
        this.allpackets                     = 0.0;
        this.packetloss                     = 0.0;
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
        this.meanGlobalPopulation           = 0;
        this.meanCloudletPopulation         = 0;
        this.meanCloudPopulation            = 0;
        this.meanGlobalPopulationClass1     = 0;
        this.meanCloudletPopulationClass1   = 0;
        this.meanCloudPopulationClass1      = 0;
        this.meanGlobalPopulationClass2     = 0;
        this.meanCloudletPopulationClass2   = 0;
        this.meanCloudPopulationClass2      = 0;
        statistics = Statistics.getInstance();
    }

    public static JobStatistics getInstance()
    {
        if(instance == null)
            instance = new JobStatistics();
        return instance;
    }

    /**
     * Methods to get statistics
     */

    public void updatePopulationMeans(int type, int jobClass,
                                      int cloudletPopulation, int cloudPopulation,
                                      int cloudletPopulationClass1, int cloudPopulationClass1,
                                      int cloudletPopulationClass2, int cloudPopulationClass2){

        // aggiornamento della popolazione media senza considerare la classe
        updateGlobalPopulation(cloudletPopulation, cloudPopulation);
        if(type == 1){
            long iterationValueCloudlet = (int) (arrivedCloudletClass1 + arrivedCloudletClass2);
            this.meanCloudletPopulation = statistics.welfordMean(this.meanCloudletPopulation, cloudletPopulation, iterationValueCloudlet);
        }
        else if(type == 2){
            long iterationValueCloud = (int) (arrivedCloudClass1 + arrivedCloudClass2);
            this.meanCloudPopulation = statistics.welfordMean(this.meanCloudPopulation, cloudPopulation, iterationValueCloud);
        }

        //aggiornamento della popolazione media per la classe 1
        if(jobClass == 1) {
            if (type == 1) {
                long iterationCloudletClass1 = (long) arrivedCloudletClass1;
                this.meanCloudletPopulationClass1 = statistics.welfordMean(this.meanCloudletPopulationClass1, cloudletPopulationClass1, iterationCloudletClass1);
            } else if (type == 2) {
                long iterationCloudClass1 = (long) arrivedCloudClass1;
                this.meanCloudPopulationClass1 = statistics.welfordMean(this.meanCloudPopulationClass1, cloudPopulationClass1, iterationCloudClass1);
            }
        }

        //aggiornamento della popolazione media per la classe 2
        else if(jobClass == 2){
            if(type == 1){
                long iterationCloudletClass2 = (long) arrivedCloudletClass2;
                this.meanCloudletPopulationClass2 = statistics.welfordMean(this.meanCloudletPopulationClass2, cloudletPopulationClass2, iterationCloudletClass2);
            }
            else if(type == 2){
                long iterationCloudClass2 = (long) arrivedCloudClass2;
                this.meanCloudPopulationClass2 = statistics.welfordMean(this.meanCloudPopulationClass2, cloudPopulationClass2, iterationCloudClass2);
            }
        }
    }

    public void updateGlobalIteration(){
        globalIteration = arrivedCloudClass1 + arrivedCloudClass2 + arrivedCloudletClass1 + arrivedCloudletClass2;
    }


    public void updateGlobalPopulation(int cloudletPopulation, int cloudPopulation){
        updateGlobalIteration();
        this.meanGlobalPopulation = statistics.welfordMean(this.meanGlobalPopulation,
                cloudletPopulation + cloudPopulation, globalIteration);
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

    public void increaseCompletedCloudlet(){
        this.completedCloudlet++;
    }

    public void increaseCompletedCloud(){
        completedCloud++;
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

    public void setGlobalTime(double globalTime) {
        this.globalTime = globalTime;
    }

    public void increaseCompletesCloudletClass(){
        this.completedCloudletClass1++;
    }

    public void increasePacket2Cloudlet(){
        this.completedCloudletClass2++;
    }

    public void increasePacket1Cloud(){
        this.completedCloudClass1++;
    }

    public void increaseCompletedCloudletClass1(){completedCloudletClass1 ++;}

    public void increaseCompletedCloudletClass2(){completedCloudletClass2 ++;}

    public void increasePacket2Cloud(){
        this.completedCloudClass2++;
    }



    /**
     * Getters and Setters
     */
    public double getPacket1() {
        return completedCloudletClass1 + completedCloudClass1;
    }

    public double getPacket2() {
        return completedCloudletClass2 + completedCloudClass2;
    }

    public double getAllpackets(){
        return this.allpackets;
    }

    public double getPacketloss(){
        return this.packetloss;
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


    public double getGlobalTime() {
        return globalTime;
    }

    public double getCompletedCloudlet() {
        return completedCloudlet;
    }

    public double getCompletedCloud() {
        return completedCloud;
    }


    public double getCompletedCloudletClass1() {
        return completedCloudletClass1;
    }

    public double getCompletedCloudletClass2() {
        return completedCloudletClass2;
    }


    public void resetStatistics(){
        this.allpackets                     = 0.0;
        this.packetloss                     = 0.0;
        this.globalTime                     = 0.0;
        this.completedCloudletClass1        = 0.0;
        this.completedCloudClass1           = 0.0;
        this.completedCloudletClass2        = 0.0;
        this.completedCloudClass2           = 0.0;
        this.completedCloud                 = 0.0;
        this.completedCloudlet              = 0.0;
    }


    public void increaseCompletedCloudClass1() {
        completedCloudClass1++;
    }

    public void increaseCompletedCloudClass2() {
        completedCloudClass2++;
    }

    public double getCompletedCloudClass1() {
        return completedCloudClass1;
    }

    public double getCompletedCloudClass2() {
        return completedCloudClass2;
    }
}
