package runners.Statistics;

import runners.simulation.BatchMeans;
import runners.simulation.ConfidenceInterval;
import system.CSVlogger;
import system.SystemConfiguration;
import variablesGenerator.Arrivals;

public class JobStatistics {

    private static JobStatistics instance = null;
    private static Statistics statistics;
    private static ConfidenceInterval confidenceInterval;
    private static BatchMeans batchMeans;
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

    private double varGlobalPopulation;
    private double varCloudletPopulation;
    private double varCloudPopulation;
    private double varGlobalPopulationClass1;
    private double varCloudletPopulationClass1;
    private double varCloudPopulationClass1;
    private double varGlobalPopulationClass2;
    private double varCloudletPopulationClass2;
    private double varCloudPopulationClass2;

    private long globalIteration;


    private JobStatistics(){
        resetStatistics();
        statistics = Statistics.getInstance();
        batchMeans = BatchMeans.getInstance();
        confidenceInterval = ConfidenceInterval.getInstance();
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

//        updateGlobalPopulation(cloudletPopulation, cloudPopulation);
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
            this.meanGlobalPopulationClass1 = confidenceInterval.computeVariance(this.meanGlobalPopulationClass1, cloudletPopulationClass1 + cloudPopulationClass1,varGlobalPopulationClass1, iterationClass1)
;
            if (type == 1) {
                long iterationCloudletClass1 = arrivedCloudletClass1;
                this.meanCloudletPopulationClass1 = statistics.welfordMean(this.meanCloudletPopulationClass1, cloudletPopulationClass1, iterationCloudletClass1);
                this.varCloudletPopulationClass1 = confidenceInterval.computeVariance(this.meanCloudletPopulationClass1, cloudletPopulationClass1, varCloudletPopulationClass1,iterationCloudletClass1);
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

        CSVlogger.getInstance().writeMeansInOneSimulation(this);
    }






    @SuppressWarnings("Duplicates")
    public void updatePopulationMeansTest(int cloudletOrCloud, int jobClass,
                                          int[] cloudletPopulation,
                                          int[] cloudPopulation){


        updateGlobalIterations(cloudletOrCloud, jobClass);
        // aggiornamento della popolazione media senza considerare la classe
        int totcloudletPopulation = cloudletPopulation[0] + cloudletPopulation[1];
        int totcloudPopulation = cloudPopulation[0] + cloudPopulation[1];

        this.meanGlobalPopulation = statistics.welfordMean(this.meanGlobalPopulation, totcloudletPopulation + totcloudPopulation, globalIteration);
        this.varGlobalPopulation = confidenceInterval.computeVariance(this.meanGlobalPopulation, totcloudletPopulation + totcloudPopulation, varGlobalPopulation,globalIteration);

        this.meanCloudletPopulation = statistics.welfordMean(this.meanCloudletPopulation, totcloudletPopulation, globalIteration);
        this.varCloudletPopulation = confidenceInterval.computeVariance(this.meanCloudletPopulation,totcloudletPopulation,varCloudletPopulation,globalIteration);

        this.meanCloudPopulation = statistics.welfordMean(this.meanCloudPopulation, totcloudPopulation, globalIteration);
        this.varCloudPopulation = confidenceInterval.computeVariance(this.meanCloudPopulation,totcloudPopulation,varCloudPopulation,globalIteration);


        //aggiornamento della popolazione media per la classe 1
        if(jobClass == 1) {
            long iterationClass1 = arrivedCloudletClass1 + arrivedCloudClass1;
            this.meanGlobalPopulationClass1 = statistics.welfordMean(this.meanGlobalPopulationClass1, cloudletPopulation[0] + cloudPopulation[0], iterationClass1);
            this.varGlobalPopulationClass1 = confidenceInterval.computeVariance(meanGlobalPopulationClass1, cloudletPopulation[0] + cloudPopulation[0], varGlobalPopulationClass1,iterationClass1);

            long iterationCloudletClass1 = arrivedCloudletClass1;
            this.meanCloudletPopulationClass1 = statistics.welfordMean(this.meanCloudletPopulationClass1, cloudletPopulation[0], iterationCloudletClass1);
            this.varCloudletPopulationClass1 = confidenceInterval.computeVariance(meanCloudletPopulationClass1,cloudletPopulation[0], varCloudletPopulationClass1,iterationCloudletClass1);

            long iterationCloudClass1 = arrivedCloudClass1;
            this.meanCloudPopulationClass1 = statistics.welfordMean(this.meanCloudPopulationClass1, cloudPopulation[0], iterationCloudClass1);
            this.varCloudPopulationClass1 = confidenceInterval.computeVariance(meanCloudPopulationClass1,cloudPopulation[0],varCloudPopulationClass1,iterationClass1);

        }

        //aggiornamento della popolazione media per la classe 2
        else if(jobClass == 2){
            long iterationClass2 = arrivedCloudletClass2 + arrivedCloudClass2;
            this.meanGlobalPopulationClass2 = statistics.welfordMean(this.meanGlobalPopulationClass2, cloudletPopulation[1] + cloudPopulation[1], iterationClass2);
            this.varGlobalPopulationClass2 = confidenceInterval.computeVariance(meanGlobalPopulationClass2, cloudletPopulation[1] + cloudPopulation[1], varGlobalPopulationClass2,iterationClass2);

            long iterationCloudletClass2 = arrivedCloudletClass2;
            this.meanCloudletPopulationClass2 = statistics.welfordMean(this.meanCloudletPopulationClass2, cloudletPopulation[1], iterationCloudletClass2);
            this.varCloudletPopulationClass2 = confidenceInterval.computeVariance(meanCloudletPopulationClass2,cloudletPopulation[1], varCloudletPopulationClass2,iterationCloudletClass2);

            long iterationCloudClass2 = arrivedCloudClass2;
            this.meanCloudPopulationClass2 = statistics.welfordMean(this.meanCloudPopulationClass2, cloudPopulation[1], iterationCloudClass2);
            this.varCloudPopulationClass2 = confidenceInterval.computeVariance(meanCloudPopulationClass2,cloudPopulation[1],varCloudPopulationClass2,iterationClass2);

        }
        computeBatch();
        CSVlogger.getInstance().writeMeansInOneSimulation(this);
    }


    public void computeBatch(){

        long actualit = globalIteration;
        if(SystemConfiguration.BATCH && ((actualit%batchMeans.getBatchSize() == 0) || globalIteration == SystemConfiguration.ITERATIONS)){

            batchMeans.updateBMGlobalPopulation(meanGlobalPopulation,0);
            batchMeans.updateBMGlobalPopulation(varGlobalPopulation,1);
            batchMeans.updateBMCloudletPopulation(meanCloudletPopulation,0);
            batchMeans.updateBMCloudletPopulation(varCloudletPopulation,1);
            batchMeans.updateBMCloudPopulation(meanCloudPopulation,0);
            batchMeans.updateBMCloudPopulation(varCloudPopulation,1);

            batchMeans.updateBMGlobalPopulationClass1(meanGlobalPopulationClass1,0);
            batchMeans.updateBMGlobalPopulationClass1(varGlobalPopulationClass1,1);
            batchMeans.updateBMCloudletPopulationClass1(meanCloudletPopulationClass1,0);
            batchMeans.updateBMCloudletPopulationClass1(varCloudletPopulationClass1,1);
            batchMeans.updateBMCloudPopulationClass1(meanCloudPopulationClass1,0);
            batchMeans.updateBMCloudPopulationClass1(varCloudPopulationClass1,1);

            batchMeans.updateBMGlobalPopulationClass2(meanGlobalPopulationClass2,0);
            batchMeans.updateBMGlobalPopulationClass2(varGlobalPopulationClass2,1);
            batchMeans.updateBMCloudletPopulationClass2(meanCloudletPopulationClass2,0);
            batchMeans.updateBMCloudletPopulationClass2(varCloudletPopulationClass2,1);
            batchMeans.updateBMCloudPopulationClass2(varCloudPopulationClass2,0);
            batchMeans.updateBMCloudPopulationClass2(varCloudPopulationClass2,1);

            if(globalIteration != SystemConfiguration.ITERATIONS){
                resetStatistics();
                globalIteration = actualit;
            }
        }
    }

    public void updateGlobalMean(int cloudletOrCloud, int jobClass, int[] cloudletPopulation, int[] cloudPopulation){
        updateGlobalIterations(cloudletOrCloud, jobClass);

        int currentCloudletPopulation = cloudletPopulation[0] + cloudletPopulation[1];
        int currentCloudPopulation = cloudPopulation[0] + cloudPopulation[1];

        this.meanGlobalPopulation = statistics.welfordMean(this.meanGlobalPopulation,
                currentCloudletPopulation + currentCloudPopulation, globalIteration);


        if(jobClass == 1){
            long iterationClass1 = arrivedCloudletClass1 + arrivedCloudClass1;
            this.meanGlobalPopulationClass1 = statistics.welfordMean(this.meanGlobalPopulationClass1, cloudletPopulation[0] + cloudPopulation[0], iterationClass1);
        }
        else{
            long iterationClass2 = arrivedCloudletClass2 + arrivedCloudClass2;
            this.meanGlobalPopulationClass2 = statistics.welfordMean(this.meanGlobalPopulationClass2, cloudletPopulation[1] + cloudPopulation[1], iterationClass2);
        }
    }

    public void updatecloudMean(){

    }

    public void updateCloudletMean(){

    }



    private void updateGlobalIterations(int cloudletOrCloud, int jobClass){
        if(cloudletOrCloud == 1 && jobClass == 1)
            arrivedCloudletClass1++;
        if(cloudletOrCloud == 1 && jobClass == 2)
            arrivedCloudletClass2++;
        if(cloudletOrCloud == 2 && jobClass == 1)
            arrivedCloudClass1++;
        if(cloudletOrCloud == 2 && jobClass == 2)
            arrivedCloudClass2++;

        globalIteration++;
    }

//    private void updateGlobalPopulation(int cloudletPopulation, int cloudPopulation){
//        updateGlobalIteration();
//        this.meanGlobalPopulation = statistics.welfordMean(this.meanGlobalPopulation,
//                cloudletPopulation + cloudPopulation, globalIteration);
//    }


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


    //GLOBAL CLOUDLET THROUGHPUT
    public double getAnalyticCloudletThroughput() {
        return Arrivals.getInstance().getTotalRate() * (1 - calculatePLoss());
    }

    public double getEmpiricCloudletThroughput(){
        return (completedCloudletClass1 + completedCloudletClass2)/globalTime;
    }

    //PER-CLASS CLOUDLET THROUGHPUT
    public double getAnalyticCloudletClass1Throughput() {
        return SystemConfiguration.ARRIVAL_RATE_1 * (1 - calculatePLoss());
    }

    public double getEmpiricCloudletClass1Throughput(){
        return completedCloudletClass1/globalTime;
    }

    public double getAnalyticCloudletClass2Throughput() {
        return SystemConfiguration.ARRIVAL_RATE_2 * (1 - calculatePLoss());
    }

    public double getEmpiricCloudletClass2Throughput(){
        return completedCloudletClass2/globalTime;
    }


    //GLOBAL CLOUD THROUGHPUT
    public double getAnalyticCloudThroughput(){
        return Arrivals.getInstance().getTotalRate()*(calculatePLoss());
    }

    public double getEmpiricCloudThroughput(){
        return (completedCloudClass1 + completedCloudClass2)/globalTime;
    }

    //PER-CLASS CLOUD THROUGHPUT
    public double getAnalyticCloudClass1Throughput() {
        return SystemConfiguration.ARRIVAL_RATE_1 * (calculatePLoss());
    }

    public double getEmpiricCloudClass1Throughput(){
        return completedCloudClass1 /globalTime;
    }

    public double getAnalyticCloudClass2Throughput() {
        return SystemConfiguration.ARRIVAL_RATE_2 * (calculatePLoss());
    }

    public double getEmpiricCloudClass2Throughput(){
        return completedCloudClass2 /globalTime;
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
