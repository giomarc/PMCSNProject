package statistics;

import results.CSVlogger;
import system.SystemConfiguration;

@SuppressWarnings("Duplicates")
public class JobStatistics{


    private static JobStatistics instance = null;
    private static ConfidenceInterval statistics;
    private static BatchMeans batchMeans;
    private static TimeStatistics ts;

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

    //THROUGHPUT MEANS
    private double meanSystemThroughput;
    private double meanSystemThroughput1;
    private double meanSystemThroughput2;
    private double meanCletThroughput;
    private double meanCletThroughput1;
    private double meanCletThroughput2;
    private double meanCloudThroughput;
    private double meanCloudThroughput1;
    private double meanCloudThroughput2;


    //ITERATIONS
    private long globalIteration;
    private long actualIteration;
    private double globalTime;
    private double actualTime;
    private double batchSize;
    private int actualBatch;

    private long completedCloudlet_1;
    private long completedCloudlet_2;
    private long completedCloud_1;
    private long completedCloud_2;



    private JobStatistics(){
        resetStatistics();
        statistics = ConfidenceInterval.getInstance();
        batchMeans = BatchMeans.getInstance();
        batchSize = batchMeans.getBatchSize();
        ts = TimeStatistics.getInstance();
    }

    public static JobStatistics getInstance(){
        if(instance == null)
            instance = new JobStatistics();
        return instance;
    }


    /**
     * Updates population means statistics
     * @param cloudletPopulation actual number of jobs into cloudlet
     * @param cloudPopulation actual number of jobs into cloud
     */
    public void updatePopulationMeans(int[] cloudletPopulation, int[] cloudPopulation){


        updateGlobalIterations();
        updateGlobal(cloudletPopulation,cloudPopulation);
        updateClass1(cloudletPopulation[0],cloudPopulation[0]);
        updateClass2(cloudletPopulation[1],cloudPopulation[1]);

        CSVlogger.getInstance().writePopulationMeanInOneSimulation(this);
        if(SystemConfiguration.BATCH && ((actualIteration >= batchSize))){

            computeBatch();
            setBatchSize();
            actualBatch++;
        }
    }


    /**
     * Utility function
     */
    public void setBatchSize(){
        if( ((SystemConfiguration.ITERATIONS % SystemConfiguration.NUM_BATCH) != 0) && actualBatch == (SystemConfiguration.NUM_BATCH-1)){
            batchSize = (SystemConfiguration.ITERATIONS - (SystemConfiguration.NUM_BATCH*batchMeans.getBatchSize()))+1;
        }
    }

    /**
     * Update system, cloudlet and cloud global population means
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
     * Update means of class 1 jobs for system, cloudlet and cloud
     * @param cloudlet1 actual number of class 1 jobs in cloudlet
     * @param cloud1 actual number of class 1 jobs in cloud
     */
    private void updateClass1(int cloudlet1, int cloud1){
        int totalClass1 = cloudlet1 + cloud1;
        meanGlobalPopulation_1 = statistics.computeWelfordMean(meanGlobalPopulation_1, totalClass1, actualIteration);
        meanCloudletPopulation_1 = statistics.computeWelfordMean(meanCloudletPopulation_1, cloudlet1, actualIteration);
        meanCloudPopulation_1 = statistics.computeWelfordMean(meanCloudPopulation_1, cloud1, actualIteration);
    }


    /**
     * Updates means of class 2 jobs for system, cloudlet and cloud
     * @param cloudlet2 actual number of class 2 jobs in cloudlet
     * @param cloud2 actual number of class 2 jobs in cloud
     */
    private void updateClass2(int cloudlet2, int cloud2){
        int totalClass2 = cloudlet2 + cloud2;
        meanGlobalPopulation_2 = statistics.computeWelfordMean(meanGlobalPopulation_2, totalClass2, actualIteration);
        meanCloudletPopulation_2 = statistics.computeWelfordMean(meanCloudletPopulation_2, cloudlet2, actualIteration);
        meanCloudPopulation_2 = statistics.computeWelfordMean(meanCloudPopulation_2, cloud2, actualIteration);
    }


    public void updateThroughputStatistics(){

        double sysT = getSystemThroughput();
        double sysT1 = getSystemClass1Throughput();
        double sysT2 = getSystemClass2Throughput();
        double cletT = getCloudletThroughput();
        double cletT1 = getCloudletClass1Throughput();
        double cletT2 = getCloudletClass2Throughput();
        double cloudT = getCloudThroughput();
        double cloudT1 = getCloudClass1Throughput();
        double cloudT2 = getCloudClass2Throughput();
        long iterations = getActuallIteration();

        meanSystemThroughput = statistics.computeWelfordMean(meanSystemThroughput,sysT, iterations);
        meanSystemThroughput1 = statistics.computeWelfordMean(meanSystemThroughput1,sysT1, iterations);
        meanSystemThroughput2 = statistics.computeWelfordMean(meanSystemThroughput2,sysT2, iterations);

        meanCletThroughput = statistics.computeWelfordMean(meanCletThroughput,cletT, iterations);
        meanCletThroughput1 = statistics.computeWelfordMean(meanCletThroughput1,cletT1, iterations);
        meanCletThroughput2 = statistics.computeWelfordMean(meanCletThroughput2,cletT2, iterations);

        meanCloudThroughput = statistics.computeWelfordMean(meanCloudThroughput,cloudT, iterations);
        meanCloudThroughput1 = statistics.computeWelfordMean(meanCloudThroughput1,cloudT1, iterations);
        meanCloudThroughput2 = statistics.computeWelfordMean(meanCloudThroughput2,cloudT2, iterations);


    }



    /**
     * Updates Batch Means Array for population with current mean values, throughput and response time
     */
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

        computeThroughputBatch();
        ts.computeResponseTimeBatch();
        resetMeans();
        ts.resetStatistics();
    }


    /**
     * Updates Batch Means throughput arrays with current mean values
     */
    public void computeThroughputBatch(){
        batchMeans.updateThroughputBMArray(meanSystemThroughput,"sys",0);
        batchMeans.updateThroughputBMArray(meanSystemThroughput1,"sys",1);
        batchMeans.updateThroughputBMArray(meanSystemThroughput2,"sys",2);
        batchMeans.updateThroughputBMArray(meanCletThroughput,"clet",0);
        batchMeans.updateThroughputBMArray(meanCletThroughput1,"clet",1);
        batchMeans.updateThroughputBMArray(meanCletThroughput2,"clet",2);
        batchMeans.updateThroughputBMArray(meanCloudThroughput,"cloud",0);
        batchMeans.updateThroughputBMArray(meanCloudThroughput1,"cloud",1);
        batchMeans.updateThroughputBMArray(meanCloudThroughput2,"cloud",2);

    }


    /**
     * Increase value of actual and global iterations
     */
    private void updateGlobalIterations(){
        actualIteration++;
        globalIteration++;
    }


    /**
     * Return actual iteration
     */
    public long getActuallIteration(){
        return actualIteration;
    }


    /*
     * Computes throughput in terms of (completed tasks)/time
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
        return (completedCloudlet_1 + completedCloudlet_2)/actualTime;
    }

    public double getCloudletClass1Throughput(){
        return completedCloudlet_1/actualTime;
    }

    public double getCloudletClass2Throughput(){
        return completedCloudlet_2/actualTime;
    }

    public double getCloudThroughput(){
        return (completedCloud_1 + completedCloud_2)/actualTime;
    }

    public double getCloudClass1Throughput(){
        return completedCloud_1 /actualTime;
    }

    public double getCloudClass2Throughput(){
        return completedCloud_2 /actualTime;
    }


    /**
     * Computes Cloudlet Ploss
     */
    public double calculatePLoss(){
        double allPackets = getGlobalCompleted(0);
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


    /*
     * Compute completed job into system, cloudlet and cloud
     * @param jobclass 0: both class 1 and 2, 1: class 1, 2: class 2
     */
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


    public long getGlobalCompleted(int jobclass){
        if(jobclass == 0)
            return  completedCloudlet_1 + completedCloudlet_2 + completedCloud_1 + completedCloud_2;
        else if(jobclass == 1)
            return completedCloudlet_1 + completedCloud_1;
        else
            return completedCloudlet_2 + completedCloud_2;
    }


    /*
     * Return actual population means
     */
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



    /*
     * RESET statistics
     */
    public void resetStatistics(){
        batchMeans = BatchMeans.getInstance();
        batchSize = batchMeans.getBatchSize();

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

        this.meanSystemThroughput       = 0;
        this.meanSystemThroughput1      = 0;
        this.meanSystemThroughput2      = 0;
        this.meanCletThroughput         = 0;
        this.meanCletThroughput1        = 0;
        this.meanCletThroughput2        = 0;
        this.meanCloudThroughput        = 0;
        this.meanCloudThroughput1       = 0;
        this.meanCloudThroughput2       = 0;

        this.actualTime                 = 0;
        this.actualIteration            = 0;

        this.completedCloudlet_1        = 0;
        this.completedCloud_1           = 0;
        this.completedCloudlet_2        = 0;
        this.completedCloud_2           = 0;
    }






}
