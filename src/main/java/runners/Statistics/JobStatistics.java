package runners.Statistics;

import system.CSVlogger;
import variablesGenerator.Arrivals;

public class JobStatistics {

    private static JobStatistics instance = null;
    private static Statistics statistics;
    private double globalTime;

    //POPULATION COUNTERS
    private long completedCloudletClass1;
    private long completedCloudletClass2;
    private long completedCloudClass1;
    private long completedCloudClass2;
    private long arrivedCloudletClass1;
    private long arrivedCloudletClass2;
    private long arrivedCloudClass1;
    private long arrivedCloudClass2;


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

    private long globalIteration;
    private long cloudletIteration;
    private long cloudIteration;
    private long cloudletClass1Iteration;
    private long cloudletClass2Iteration;
    private long cloudClass1Iteration;
    private long cloudClas2Iteration;


    private JobStatistics(){
        this.completedCloudletClass1        = 0;
        this.completedCloudClass1           = 0;
        this.completedCloudletClass2        = 0;
        this.completedCloudClass2           = 0;
        this.arrivedCloudletClass1          = 1;
        this.arrivedCloudletClass2          = 1;
        this.arrivedCloudClass1             = 1;
        this.arrivedCloudClass2             = 1;
        this.meanGlobalPopulation           = 0;
        this.meanCloudletPopulation         = 0;
        this.meanCloudPopulation            = 0;
        this.meanGlobalPopulationClass1     = 0;
        this.meanCloudletPopulationClass1   = 0;
        this.meanCloudPopulationClass1      = 0;
        this.meanGlobalPopulationClass2     = 0;
        this.meanCloudletPopulationClass2   = 0;
        this.meanCloudPopulationClass2      = 0;
        this.globalIteration                = 0;
        this.globalTime                     = 0;
        statistics = Statistics.getInstance();
    }

    public static JobStatistics getInstance()
    {
        if(instance == null)
            instance = new JobStatistics();
        return instance;
    }


    /**
     * UPDATE POPULATION STATISTICS
     */
    @SuppressWarnings("Duplicates")
    public void updatePopulationMeans(int type, int jobClass,
                                      int cloudletPopulationClass1, int cloudPopulationClass1,
                                      int cloudletPopulationClass2, int cloudPopulationClass2){

        // aggiornamento della popolazione media senza considerare la classe
        int cloudletPopulation = cloudletPopulationClass1 + cloudletPopulationClass2;
        int cloudPopulation = cloudPopulationClass1 + cloudPopulationClass2;
//        System.out.println("[ " + cloudletPopulationClass1 + ", " + cloudletPopulationClass2 + " | " + cloudPopulationClass1 + ", " + cloudPopulationClass2 + " | " + (cloudPopulationClass1+ cloudPopulationClass2) +" ]");

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
            long iterationClass1 = arrivedCloudletClass1 + arrivedCloudClass1;
            this.meanGlobalPopulationClass1 = statistics.welfordMean(this.meanGlobalPopulationClass1, cloudletPopulationClass1 + cloudPopulationClass1, iterationClass1);
            if (type == 1) {
                long iterationCloudletClass1 = arrivedCloudletClass1;
                this.meanCloudletPopulationClass1 = statistics.welfordMean(this.meanCloudletPopulationClass1, cloudletPopulationClass1, iterationCloudletClass1);
            } else if (type == 2) {
                long iterationCloudClass1 = arrivedCloudClass1;
                this.meanCloudPopulationClass1 = statistics.welfordMean(this.meanCloudPopulationClass1, cloudPopulationClass1, iterationCloudClass1);
            }
        }

        //aggiornamento della popolazione media per la classe 2
        else if(jobClass == 2){
            long iterationClass2 = arrivedCloudletClass2 + arrivedCloudClass2;
            this.meanGlobalPopulationClass2 = statistics.welfordMean(this.meanGlobalPopulationClass2, cloudletPopulationClass2 + cloudPopulationClass2, iterationClass2);
            if(type == 1){
                long iterationCloudletClass2 = arrivedCloudletClass2;
                this.meanCloudletPopulationClass2 = statistics.welfordMean(this.meanCloudletPopulationClass2, cloudletPopulationClass2, iterationCloudletClass2);
            }
            else if(type == 2){
                long iterationCloudClass2 = arrivedCloudClass2;
                this.meanCloudPopulationClass2 = statistics.welfordMean(this.meanCloudPopulationClass2, cloudPopulationClass2, iterationCloudClass2);
            }
        }

        CSVlogger.getInstance().writeMeanPopulation(this);
    }






    @SuppressWarnings("Duplicates")
    public void updatePopulationMeansTest(int jobClass,
                                          int cloudletPopulationClass1, int cloudPopulationClass1,
                                          int cloudletPopulationClass2, int cloudPopulationClass2){

        // aggiornamento della popolazione media senza considerare la classe
        int cloudletPopulation = cloudletPopulationClass1 + cloudletPopulationClass2;
        int cloudPopulation = cloudPopulationClass1 + cloudPopulationClass2;
//        System.out.println("[ " + cloudletPopulationClass1 + ", " + cloudletPopulationClass2 + " | " + cloudPopulationClass1 + ", " + cloudPopulationClass2 + " | " + (cloudPopulationClass1+ cloudPopulationClass2) +" ]");

        updateGlobalPopulation(cloudletPopulation, cloudPopulation);
        this.meanCloudletPopulation = statistics.welfordMean(this.meanCloudletPopulation, cloudletPopulation, globalIteration);

        this.meanCloudPopulation = statistics.welfordMean(this.meanCloudPopulation, cloudPopulation, globalIteration);

        //aggiornamento della popolazione media per la classe 1
        if(jobClass == 1) {
            long iterationClass1 = arrivedCloudletClass1 + arrivedCloudClass1;
            this.meanGlobalPopulationClass1 = statistics.welfordMean(this.meanGlobalPopulationClass1, cloudletPopulationClass1 + cloudPopulationClass1, iterationClass1);

            long iterationCloudletClass1 = arrivedCloudletClass1;
            this.meanCloudletPopulationClass1 = statistics.welfordMean(this.meanCloudletPopulationClass1, cloudletPopulationClass1, iterationCloudletClass1);

            long iterationCloudClass1 = arrivedCloudClass1;
            this.meanCloudPopulationClass1 = statistics.welfordMean(this.meanCloudPopulationClass1, cloudPopulationClass1, iterationCloudClass1);

        }

        //aggiornamento della popolazione media per la classe 2
        else if(jobClass == 2){
            long iterationClass2 = arrivedCloudletClass2 + arrivedCloudClass2;
            this.meanGlobalPopulationClass2 = statistics.welfordMean(this.meanGlobalPopulationClass2, cloudletPopulationClass2 + cloudPopulationClass2, iterationClass2);

            long iterationCloudletClass2 = arrivedCloudletClass2;
            this.meanCloudletPopulationClass2 = statistics.welfordMean(this.meanCloudletPopulationClass2, cloudletPopulationClass2, iterationCloudletClass2);

            long iterationCloudClass2 = arrivedCloudClass2;
            this.meanCloudPopulationClass2 = statistics.welfordMean(this.meanCloudPopulationClass2, cloudPopulationClass2, iterationCloudClass2);

        }

        CSVlogger.getInstance().writeMeanPopulation(this);
    }







    private void updateGlobalIteration(){
        globalIteration++;
    }

    private void updateGlobalPopulation(int cloudletPopulation, int cloudPopulation){
        updateGlobalIteration();
        this.meanGlobalPopulation = statistics.welfordMean(this.meanGlobalPopulation,
                cloudletPopulation + cloudPopulation, globalIteration);
    }


    //PLOSS
    public double calculatePLoss(){
        double allPackets = completedCloudletClass1 +
                            completedCloudletClass2 +
                            completedCloudClass1 +
                            completedCloudClass2;
        double packetLoss = completedCloudClass1 +
                            completedCloudClass2;
        return packetLoss/allPackets;
    }


    //GLOBAL TIME
    public void setGlobalTime(double globalTime) {
        this.globalTime = globalTime;
    }

    public double getGlobalTime() {
        return globalTime;
    }


    //INCREASE ARRIVED JOBS
    public void increaseArrivedCloudClass1() {
        arrivedCloudClass1++;
    }

    public void increaseArrivedCloudClass2() {
        arrivedCloudClass2++;
    }

    public void increaseArrivedCloudletClass1(){
        arrivedCloudletClass1++;
    }

    public void increaseArrivedCloudletClass2(){
        arrivedCloudletClass2++;
    }


    //INCREASE COMPLETED JOBS
    void increaseCompletedCloudClass1() {
        completedCloudClass1++;
    }

    void increaseCompletedCloudClass2() {
        completedCloudClass2++;
    }

    void increaseCompletedCloudletClass1(){
        completedCloudletClass1 ++;
    }

    void increaseCompletedCloudletClass2(){
        completedCloudletClass2 ++;
    }


    //GET NUMBER OF JOBS
    public double getCompletedCloudClass1() {
        return completedCloudClass1;
    }

    public double getCompletedCloudClass2() {
        return completedCloudClass2;
    }

    public double getCompletedCloudletClass1() {
        return completedCloudletClass1;
    }

    public double getCompletedCloudletClass2() {
        return completedCloudletClass2;
    }

    public double getCompletedCloudlet() {
        return completedCloudletClass1 + completedCloudletClass2;
    }

    public double getCompletedCloud() {
        return completedCloudClass1 + completedCloudClass2;
    }

    public long getAllpackets(){
        return  completedCloudletClass1 +
                completedCloudletClass2 +
                completedCloudClass1 +
                completedCloudClass2;
    }

    public double getPacket1() {
        return completedCloudletClass1 + completedCloudClass1;
    }

    public double getPacket2() {
        return completedCloudletClass2 + completedCloudClass2;
    }


    // MEAN POPULATION
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


    // THROUGHPUT
    public double getAnalyticCloudletThroughput(){
        return Arrivals.getInstance().getTotalRate()*(1-calculatePLoss());
    }

    public double getEmpiricCloudletThroughput(){
        return (completedCloudletClass1 + completedCloudletClass2)/globalTime;
    }

    public double getAnalyticCloudThroughput(){
        return Arrivals.getInstance().getTotalRate()*(calculatePLoss());
    }

    public double getEmpiricCloudThroughput(){
        return (completedCloudClass1 + completedCloudClass2)/globalTime;
    }

    //RESET STATISTICS
    public void resetStatistics(){
        this.completedCloudletClass1        = 0;
        this.completedCloudClass1           = 0;
        this.completedCloudletClass2        = 0;
        this.completedCloudClass2           = 0;
        this.arrivedCloudletClass1          = 0;
        this.arrivedCloudletClass2          = 0;
        this.arrivedCloudClass1             = 0;
        this.arrivedCloudClass2             = 0;
        this.meanGlobalPopulation           = 0;
        this.meanCloudletPopulation         = 0;
        this.meanCloudPopulation            = 0;
        this.meanGlobalPopulationClass1     = 0;
        this.meanCloudletPopulationClass1   = 0;
        this.meanCloudPopulationClass1      = 0;
        this.meanGlobalPopulationClass2     = 0;
        this.meanCloudletPopulationClass2   = 0;
        this.meanCloudPopulationClass2      = 0;
        this.globalIteration                = 0;
        this.globalTime                     = 0;
    }

}
