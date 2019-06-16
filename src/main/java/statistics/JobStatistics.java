package statistics;

import event.CompletionHandler;
import results.CSVlogger;
import system.SystemConfiguration;

@SuppressWarnings("Duplicates")
public class JobStatistics{


    private static JobStatistics instance = null;
    private static Statistics statistics;
    private static BatchMeans batchMeans;

    //POPULATION MEANS
    private double meanGlobalPopulation;
    private double meanCloudletPopulation;
    private double meanCloudPopulation;
    private double meanGlobalPopulation_1;
    private double meanCloudletPopulation_1;
    private double meanCloudPopulation_1;
    private double meanGlobalPopulation_2;
    private double meanCloudletPopulation_2;
    private double meanCloudPopulation_2;


    private long completedCloudlet_1;
    private long completedCloudlet_2;
    private long completedCloud_1;
    private long completedCloud_2;


    //ITERATIONS
    private long globalIteration;
    private long actualIteration;
    private double globalTime;
    private double actualTime;
    private double batchSize;
    private int actualBatch;

    private JobStatistics(){
        resetStatistics();
        statistics = Statistics.getInstance();
        batchMeans = BatchMeans.getInstance();
        batchSize = batchMeans.getBatchSize();
    }

    public static JobStatistics getInstance(){
        if(instance == null)
            instance = new JobStatistics();
        return instance;
    }


    public void updatePopulationMeans(int[] cloudletPopulation, int[] cloudPopulation){


        updateGlobalIterations();
        updateGlobal(cloudletPopulation,cloudPopulation);
        updateClass1(cloudletPopulation[0],cloudPopulation[0]);
        updateClass2(cloudletPopulation[1],cloudPopulation[1]);

        CSVlogger.getInstance().writePopulationMeanInOneSimulation(this);
        if(SystemConfiguration.BATCH &&
                ((actualIteration >= batchSize))){ // devo considerare sia gli arrivi che i completamenti
            computeBatch();
            actualBatch++;
            setBatchSize();
        }
    }


    public void setBatchSize(){
        if( ((SystemConfiguration.ITERATIONS % SystemConfiguration.NUM_BATCH) != 0) && actualBatch == (SystemConfiguration.NUM_BATCH-1)){
            batchSize = (SystemConfiguration.ITERATIONS - (SystemConfiguration.NUM_BATCH*batchMeans.getBatchSize()))+1;
        }
    }

    /**
     * Updates means and variance of class 1 and class 2 jobs
     * @param cloudletPopulation actual number of jobs in cloudlet
     * @param cloudPopulation actual number of jobs in cloud
     */
    private void updateGlobal(int[] cloudletPopulation, int[] cloudPopulation){

        int totcloudletPopulation = cloudletPopulation[0] + cloudletPopulation[1];
        int totcloudPopulation = cloudPopulation[0] + cloudPopulation[1];
        int globalPop = totcloudletPopulation + totcloudPopulation;

        meanGlobalPopulation = statistics.computeWelfordMean(meanGlobalPopulation,globalPop,actualIteration);
        meanCloudletPopulation = statistics.computeWelfordMean(meanCloudletPopulation,totcloudletPopulation,actualIteration);
        meanCloudPopulation = statistics.computeWelfordMean(meanCloudPopulation,totcloudPopulation,actualIteration);
    }

    /**
     * Updates means and variance of class 1 jobs
     * @param cloudlet1 actual number of class 1 jobs in cloudlet
     * @param cloud1 actual number of class 1 jobs in cloud
     *               cloudlet  = 1
     *               cloud  = 2
     */
    private void updateClass1(int cloudlet1, int cloud1){
        int totalClass1 = cloudlet1 + cloud1;
        meanGlobalPopulation_1 = statistics.computeWelfordMean(meanGlobalPopulation_1, totalClass1, actualIteration);
        meanCloudletPopulation_1 = statistics.computeMean(meanCloudletPopulation_1, cloudlet1, actualIteration);
        meanCloudPopulation_1 = statistics.computeWelfordMean(meanCloudPopulation_1, cloud1, actualIteration);
    }

    /**
     * Updates means and variance of class 2 jobs
     * @param cloudlet2 actual number of class 2 jobs in cloudlet
     * @param cloud2 actual number of class 2 jobs in cloud
     */
    private void updateClass2(int cloudlet2, int cloud2){
        int totalClass2 = cloudlet2 + cloud2;
        meanGlobalPopulation_2 = statistics.computeWelfordMean(meanGlobalPopulation_2, totalClass2, actualIteration);
        meanCloudletPopulation_2 = statistics.computeWelfordMean(meanCloudletPopulation_2, cloudlet2, actualIteration);
        meanCloudPopulation_2 = statistics.computeWelfordMean(meanCloudPopulation_2, cloud2, actualIteration);
    }

    private void updateGlobalIterations(){
        actualIteration++;
        globalIteration++;
    }

    public long getActuallIteration(){
        return actualIteration;
    }

    private void computeBatch(){

        batchMeans.updateBMCloudletPopulation               (this.meanCloudletPopulation);
        batchMeans.setBMCloudletPopulationClass1            (this.meanCloudletPopulation_1);
        batchMeans.setBMCloudletPopulationClass2            (this.meanCloudletPopulation_2);
        batchMeans.updateBMCloudPopulation                  (this.meanCloudPopulation);
        batchMeans.setBMCloudPopulationClass1               (this.meanCloudPopulation_1);
        batchMeans.setBMCloudPopulationClass2               (this.meanCloudPopulation_2);
        batchMeans.updateBMGlobalPopulation                 (this.meanGlobalPopulation);
        batchMeans.setBMGlobalPopulationClass1              (this.meanGlobalPopulation_1);
        batchMeans.setBMGlobalPopulation_2                  (this.meanGlobalPopulation_2);


        //OLD Throughput
        batchMeans.updateBMAvgTroughputArray(getSystemThroughput(),0);
        batchMeans.updateBMAvgTroughputArray(getSystemClass1Throughput(),1);
        batchMeans.updateBMAvgTroughputArray(getSystemClass2Throughput(),2);

        batchMeans.updateBMAvgCletTroughputArray(getCloudletThroughput(),0);
        batchMeans.updateBMAvgCletTroughputArray(getCloudletClass1Throughput(),1);
        batchMeans.updateBMAvgCletTroughputArray(getCloudletClass2Throughput(),2);

        batchMeans.updateBMAvgCloudTroughputArray(getCloudThroughput(),0);
        batchMeans.updateBMAvgCloudTroughputArray(getCloudClass1Throughput(),1);
        batchMeans.updateBMAvgCloudTroughputArray(getCloudClass2Throughput(),2);


        //New throughput
        double systemT = CompletionHandler.getInstance().getThroughputStatistics(0,"sys");
        double systemT1 = CompletionHandler.getInstance().getThroughputStatistics(1,"sys");
        double systemT2 = CompletionHandler.getInstance().getThroughputStatistics(2,"sys");
        double cletT = CompletionHandler.getInstance().getThroughputStatistics(0,"clet");
        double cletT1 = CompletionHandler.getInstance().getThroughputStatistics(1,"clet");
        double cletT2 = CompletionHandler.getInstance().getThroughputStatistics(2,"clet");
        double cloudT = CompletionHandler.getInstance().getThroughputStatistics(0,"cloud");
        double cloudT1 = CompletionHandler.getInstance().getThroughputStatistics(1,"cloud");
        double cloudT2 = CompletionHandler.getInstance().getThroughputStatistics(2,"cloud");

        batchMeans.updateNewThroughputBMArray(systemT,"sys",0);
        batchMeans.updateNewThroughputBMArray(systemT1,"sys",1);
        batchMeans.updateNewThroughputBMArray(systemT2,"sys",2);
        batchMeans.updateNewThroughputBMArray(cletT,"clet",0);
        batchMeans.updateNewThroughputBMArray(cletT1,"clet",1);
        batchMeans.updateNewThroughputBMArray(cletT2,"clet",2);
        batchMeans.updateNewThroughputBMArray(cloudT,"cloud",0);
        batchMeans.updateNewThroughputBMArray(cloudT1,"cloud",1);
        batchMeans.updateNewThroughputBMArray(cloudT2,"cloud",2);

        resetMeans();
        CompletionHandler.getInstance().resetThroughputStatistics();
    }




    /**
     * THROUGHPUT
     */
    public double getSystemThroughput(){
        return (getCloudThroughput() + getCloudletThroughput());
    }

    public double getSystemClass1Throughput(){
        return (getCloudletClass1Throughput() + getCloudClass1Throughput());
    }

    public double getSystemClass2Throughput(){
        return (getCloudletClass2Throughput() + getCloudClass2Throughput());
    }

    public double getCloudletThroughput(){
        return (completedCloudlet_1 + completedCloudlet_2)/globalTime;
    }

    public double getCloudletClass1Throughput(){
        return completedCloudlet_1/globalTime;
    }

    public double getCloudletClass2Throughput(){
        return completedCloudlet_2/globalTime;
    }

    public double getCloudThroughput(){
        return (completedCloud_1 + completedCloud_2)/globalTime;
    }

    public double getCloudClass1Throughput(){
        return completedCloud_1 /globalTime;
    }

    public double getCloudClass2Throughput(){
        return completedCloud_2 /globalTime;
    }


    /**
     * PLOSS
     */
    public double calculatePLoss(){
        double allPackets = completedCloudlet_1 +
                completedCloudlet_2 +
                completedCloud_1 +
                completedCloud_2;
        double packetLoss = completedCloud_1 + completedCloud_2;
        return packetLoss/allPackets;
    }


    /**
     * Getter
     */
    //GLOBAL TIME
    public void setGlobalTime(double globalTime) {
        this.globalTime = globalTime;
    }

    public double getGlobalTime() {
        return globalTime;
    }

    public void setActualTime(double actualTime) {
        this.actualTime = actualTime;
    }

    public double getActualTime() {
        return actualTime;
    }


    //COMPLETED JOBS
    public long getCompletedCloudlet(int jobclass){
        if(jobclass == 0)
            return completedCloudlet_1 + completedCloudlet_2;
        else if(jobclass == 1)
            return completedCloudlet_1;
        else
            return completedCloudlet_2;
    }

    public long getCompletedCloud(int jobclass){
        if(jobclass == 0)
            return completedCloud_1 + completedCloud_2;
        else if(jobclass == 1)
            return completedCloud_1;
        else
            return completedCloud_2;
    }

    public void updateCompletedCloudlet(int jobclass){
        switch(jobclass){
            case 1: completedCloudlet_1++;
                break;
            case 2: completedCloudlet_2++;
                break;
        }
    }

    public void updateCompletedCloud(int jobclass){
        switch(jobclass){
            case 1: completedCloud_1++;
                break;
            case 2: completedCloud_2++;
                break;
        }
    }


    //POPULATION
    public double getMeanCloudletPopulation(int jobclass) {
        double mean = 0.0;
        switch (jobclass){
            case 0: mean = meanCloudletPopulation;
                break;
            case 1: mean = meanCloudletPopulation_1;
                break;
            case 2: mean = meanCloudletPopulation_2;
        }
        return  mean;
    }

    public double getMeanCloudPopulation(int jobclass){
        double mean = 0.0;
        switch (jobclass){
            case 0: mean = meanCloudPopulation;
                break;
            case 1: mean = meanCloudPopulation_1;
                break;
            case 2: mean = meanCloudPopulation_2;
        }
        return  mean;
    }

    public double getMeanGlobalPopulation(int jobclass){
        double mean = 0.0;
        switch (jobclass){
            case 0: mean = meanGlobalPopulation;
                break;
            case 1: mean = meanGlobalPopulation_1;
                break;
            case 2: mean = meanGlobalPopulation_2;
        }
        return  mean;
    }


    public long getPackets(int jobclass){
        if(jobclass == 0)
            return  completedCloudlet_1 + completedCloudlet_2 + completedCloud_1 + completedCloud_2;
        else if(jobclass == 1)
            return completedCloudlet_1 + completedCloud_1;
        else
            return completedCloudlet_2 + completedCloud_2;
    }


    /**
     * RESET METHODS
     */
    public void resetStatistics(){
        this.completedCloudlet_1        = 0;
        this.completedCloud_1           = 0;
        this.completedCloudlet_2        = 0;
        this.completedCloud_2           = 0;
        this.globalTime                 = 0;
        this.actualTime                 = 0;
        this.globalIteration            = 0;
        this.actualBatch                = 0;
        resetMeans();
    }

    private void resetMeans(){
        this.meanGlobalPopulation       = 0;
        this.meanCloudletPopulation     = 0;
        this.meanCloudPopulation        = 0;
        this.meanGlobalPopulation_1     = 0;
        this.meanCloudletPopulation_1   = 0;
        this.meanCloudPopulation_1      = 0;
        this.meanGlobalPopulation_2     = 0;
        this.meanCloudletPopulation_2   = 0;
        this.meanCloudPopulation_2      = 0;

        this.actualTime                 = 0;
        this.actualIteration            = 0;
    }






}
