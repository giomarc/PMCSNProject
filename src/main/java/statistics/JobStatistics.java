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

    //POPULATION VARIANCE
    private double varGlobalPopulation;
    private double varCloudletPopulation;
    private double varCloudPopulation;
    private double varGlobalPopulation_1;
    private double varCloudletPopulation_1;
    private double varCloudPopulation_1;
    private double varGlobalPopulation_2;
    private double varCloudletPopulation_2;
    private double varCloudPopulation_2;

    private long completedCloudlet_1;
    private long completedCloudlet_2;
    private long completedCloud_1;
    private long completedCloud_2;

    private double meanSystemThroughput;
    private double varSystemThroughput;
    private double meanCloudletThroughput;
    private double meanCloudThroughput;

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

        double[] MVGlobal = statistics.computeMeanAndVariance(varGlobalPopulation,meanGlobalPopulation,globalPop,actualIteration);
        meanGlobalPopulation = MVGlobal[0];
        varGlobalPopulation = MVGlobal[1];

        double[] MVCloudlet = statistics.computeMeanAndVariance(varCloudletPopulation,meanCloudletPopulation,totcloudletPopulation,actualIteration);
        meanCloudletPopulation = MVCloudlet[0];
        varCloudletPopulation = MVCloudlet[1];

        double[]MVCloud = statistics.computeMeanAndVariance(varCloudPopulation,meanCloudPopulation,totcloudPopulation,actualIteration);
        meanCloudPopulation = MVCloud[0];
        varCloudPopulation = MVCloud[1];

        /*long sysT = completedCloud_1 + completedCloud_2 + completedCloudlet_1 + completedCloudlet_2;
        double time = actualTime;
        if(globalIteration < 10)
            System.out.println("time: " + time + " - sysT: "  + sysT);
        double[] TH = statistics.computeMeanAndVariance(varSystemThroughput,meanSystemThroughput,sysT/time, actualIteration);
        meanSystemThroughput = TH[0];
        varSystemThroughput = TH[1];*/

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

        double[] GP1 = statistics.computeMeanAndVariance(varGlobalPopulation_1,meanGlobalPopulation_1, totalClass1, actualIteration);
        meanGlobalPopulation_1 = GP1[0];
        varGlobalPopulation_1 = GP1[1];

        double[] CD1 = statistics.computeMeanAndVariance(varCloudletPopulation_1, meanCloudletPopulation_1, cloudlet1, actualIteration);
        meanCloudletPopulation_1 = CD1[0];
        varCloudletPopulation_1 = CD1[1];

        double[] C1 = statistics.computeMeanAndVariance(varCloudPopulation_1, meanCloudPopulation_1, cloud1, actualIteration);
        meanCloudPopulation_1 = C1[0];
        varCloudPopulation_1 = C1[1];

    }

    /**
     * Updates means and variance of class 2 jobs
     * @param cloudlet2 actual number of class 2 jobs in cloudlet
     * @param cloud2 actual number of class 2 jobs in cloud
     */
    private void updateClass2(int cloudlet2, int cloud2){
        int totalClass2 = cloudlet2 + cloud2;

        double[] GP2 = statistics.computeMeanAndVariance(varGlobalPopulation_2,meanGlobalPopulation_2, totalClass2, actualIteration);
        meanGlobalPopulation_2 = GP2[0];
        varGlobalPopulation_2 = GP2[1];

        double[] CD2 = statistics.computeMeanAndVariance(varCloudletPopulation_2, meanCloudletPopulation_2, cloudlet2, actualIteration);
        meanCloudletPopulation_2 = CD2[0];
        varCloudletPopulation_2 = CD2[1];

        double[] C2 = statistics.computeMeanAndVariance(varCloudPopulation_2, meanCloudPopulation_2, cloud2, actualIteration);
        meanCloudPopulation_2 = C2[0];
        varCloudPopulation_2 = C2[1];

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

        batchMeans.setBMVarianceCloudletPopulation          (this.varCloudletPopulation / actualIteration);
        batchMeans.setBMVarianceCloudletPopulationClass1    (this.varCloudletPopulation_1 / actualIteration);
        batchMeans.setBMVarianceCloudletPopulationClass2    (this.varCloudletPopulation_2 / actualIteration);
        batchMeans.setBMVarianceCloudPopulation             (this.varCloudPopulation / actualIteration);
        batchMeans.setBMVarianceCloudPopulationClass1       (this.varCloudPopulation_1 /actualIteration);
        batchMeans.setBMVarianceCloudPopulationClass2       (this.varCloudPopulation_2 /actualIteration);
        batchMeans.setBMVarianceGlobalPopulation            (this.varGlobalPopulation / actualIteration);
        batchMeans.setBMVarianceGlobalPopulationClass1      (this.varGlobalPopulation_1 / actualIteration);
        batchMeans.setBMVarianceGlobalPopulationClass2      (this.varGlobalPopulation_2 / actualIteration);

        batchMeans.updateBMAvgTroughputArray(getSystemThroughput(),0);
        batchMeans.updateBMAvgTroughputArray(getSystemClass1Throughput(),1);
        batchMeans.updateBMAvgTroughputArray(getSystemClass2Throughput(),2);

        batchMeans.updateBMAvgCletTroughputArray(getCloudletThroughput(),0);
        batchMeans.updateBMAvgCletTroughputArray(getCloudletClass1Throughput(),1);
        batchMeans.updateBMAvgCletTroughputArray(getCloudletClass2Throughput(),2);

        batchMeans.updateBMAvgCloudTroughputArray(getCloudThroughput(),0);
        batchMeans.updateBMAvgCloudTroughputArray(getCloudClass1Throughput(),1);
        batchMeans.updateBMAvgCloudTroughputArray(getCloudClass2Throughput(),2);

        //batchMeans.updateNewThroughputBMArray(this.meanSystemThroughput);

        double systemT = CompletionHandler.getInstance().getThroughputStatistics();
        batchMeans.updateNewThroughputBMArray(systemT);

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

    /*public void updateMeanCloudletCompleted() {
        double[] CletC = statistics.computeMeanAndVariance(varCloudletThroughput,meanCloudletThroughput,(completedCloudlet_1 + completedCloudlet_2)/actualTime, actualIteration+1);
        meanCloudletThroughput = CletC[0];
        varCloudletThroughput = CletC[1];
    }

    public void updateMeanCloudCompleted() {
        double[] CloudC = statistics.computeMeanAndVariance(varCloudThroughput,meanCloudThroughput,(completedCloud_1 + completedCloud_2)/actualTime, actualIteration+1);
        meanCloudThroughput = CloudC[0];
        varCloudThroughput = CloudC[1];
    }

    public void updateMeanGlobalCompleted() {
        double[] GC = statistics.computeMeanAndVariance(varSystemThroughput,meanSystemThroughput,getPackets(0)/actualTime, actualIteration+1);
        meanSystemThroughput = GC[0];
        varSystemThroughput = GC[1];
    }*/

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



    //VARIANCE
    public double getVarGlobalPopulation(int jobclass) {
        double var = 0.0;
        switch (jobclass) {
            case 0:
                var = varGlobalPopulation;
                break;
            case 1:
                var = varGlobalPopulation_1;
                break;
            case 2:
                var = varGlobalPopulation_2;
                break;
        }
        return (var/globalIteration);
    }

    public double getVarCloudletPopulation(int jobclass) {
        double var = 0.0;
        switch (jobclass) {
            case 0:
                var = varCloudletPopulation;
                break;
            case 1:
                var = varCloudletPopulation_1;
                break;
            case 2:
                var = varCloudletPopulation_2;
                break;
        }
        return (var/globalIteration);
    }

    public double getVarCloudPopulation(int jobclass) {
        double var = 0.0;
        switch (jobclass) {
            case 0:
                var = varCloudPopulation;
                break;
            case 1:
                var = varCloudPopulation_1;
                break;
            case 2:
                var =  varCloudPopulation_2;
                break;
        }
        return (var/globalIteration);
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

        this.varGlobalPopulation        = 0;
        this.varCloudletPopulation      = 0;
        this.varCloudPopulation         = 0;
        this.varGlobalPopulation_1      = 0;
        this.varCloudletPopulation_1    = 0;
        this.varCloudPopulation_1       = 0;
        this.varGlobalPopulation_2      = 0;
        this.varCloudletPopulation_2    = 0;
        this.varCloudPopulation_2       = 0;

        this.meanSystemThroughput       = 0;
        this.meanCloudletThroughput     = 0;
        this.meanCloudThroughput        = 0;

        this.actualTime                 = 0;
        this.actualIteration            = 0;
    }






}
