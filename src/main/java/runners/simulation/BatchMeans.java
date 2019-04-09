package runners.simulation;

import runners.Statistics.JobStatistics;
import runners.Statistics.Statistics;
import system.SystemConfiguration;

import java.util.ArrayList;

public class BatchMeans {

    private ConfidenceInterval confidenceInterval;
    double counter_batch;
    long iterations;
    int batch_size;
    int num_batch;
    private static BatchMeans instance = null;



    //POPULATION BATCH
    // [0] = mean, [1] = variance
    private double[] BMGlobalPopulation;
    private double[] BMCloudletPopulation;
    private double[] BMCloudPopulation;
    private double[] BMGlobalPopulationClass1;
    private double[] BMCloudletPopulationClass1;
    private double[] BMCloudPopulationClass1;
    private double[] BMGlobalPopulationClass2;
    private double[] BMCloudletPopulationClass2;
    private double[] BMCloudPopulationClass2;


    //TIME STATISTICS BATCH

    private BatchMeans(){
        confidenceInterval.getInstance();
        iterations = SystemConfiguration.ITERATIONS;
        num_batch = SystemConfiguration.NUM_BATCH;
        computeBatchSize();
        counter_batch = 0;
    }

    public static BatchMeans getInstance(){
        if(instance == null)
            instance = new BatchMeans();
        return instance;
    }


    public void computeBatchMeans(){
        BMGlobalPopulation[0] = computeMean(BMGlobalPopulation[0]);
        BMGlobalPopulation[1] = computeMean(BMGlobalPopulation[1]);
        BMGlobalPopulation = confidenceInterval.computeConfidenceInterval(BMGlobalPopulation,batch_size);
        System.out.println("glob pop: [" + BMGlobalPopulation[0] + " , " + BMGlobalPopulation[1] + " )");

        BMCloudletPopulation[0] = computeMean(BMCloudletPopulation[0]);
        BMCloudletPopulation[1] = computeMean(BMCloudletPopulation[1]);
        BMCloudletPopulation = confidenceInterval.computeConfidenceInterval(BMCloudletPopulation,batch_size);
        System.out.println("glob pop: [" + BMCloudletPopulation[0] + " , " +  BMCloudletPopulation[1] + " )");

        BMCloudPopulationClass1[0] = computeMean(BMCloudPopulation[0]);
        BMCloudPopulationClass1[1] = computeMean(BMCloudPopulation[1]);
        BMCloudPopulationClass1 = confidenceInterval.computeConfidenceInterval(BMCloudPopulationClass1,batch_size);
        System.out.println("glob pop: [" + BMCloudPopulationClass1[0] + " , " + BMCloudPopulationClass1[1] + " )");

        BMGlobalPopulationClass1[0] = computeMean(BMGlobalPopulationClass1[0]);
        BMGlobalPopulationClass1[1] = computeMean(BMGlobalPopulationClass1[1]);
        BMGlobalPopulationClass1 = confidenceInterval.computeConfidenceInterval(BMGlobalPopulationClass1,batch_size);
        System.out.println("glob pop: [" + BMGlobalPopulationClass1[0] + " , " + BMGlobalPopulationClass1[1] + " )");

        BMCloudletPopulationClass1[0] = computeMean(BMCloudletPopulationClass2[0]);
        BMCloudletPopulationClass1[1] = computeMean(BMCloudletPopulationClass2[1]);
        BMCloudletPopulationClass1 = confidenceInterval.computeConfidenceInterval(BMCloudletPopulationClass1,batch_size);
        System.out.println("glob pop: [" + BMCloudletPopulationClass1[0] + " , " + BMCloudletPopulationClass1[1] + " )");

        BMCloudPopulationClass1[0] = computeMean(BMCloudPopulationClass1[0]);
        BMCloudPopulationClass1[1] = computeMean(BMCloudPopulationClass1[1]);
        BMCloudPopulationClass1 = confidenceInterval.computeConfidenceInterval(BMCloudPopulationClass1,batch_size);
        System.out.println("glob pop: [" + BMCloudPopulationClass1[0] + " , " + BMCloudPopulationClass1[1] + " )");

        BMGlobalPopulationClass2[0] = computeMean(BMGlobalPopulationClass2[0]);
        BMGlobalPopulationClass2[1] = computeMean(BMGlobalPopulationClass2[1]);
        BMGlobalPopulationClass2 = confidenceInterval.computeConfidenceInterval(BMGlobalPopulationClass2,batch_size);
        System.out.println("glob pop: [" + BMGlobalPopulationClass2[0] + " , " + BMGlobalPopulationClass2[1] + " )");

        BMCloudletPopulationClass2[0] = computeMean(BMCloudletPopulationClass2[0]);
        BMCloudletPopulationClass2[1] = computeMean(BMCloudletPopulationClass2[1]);
        BMCloudletPopulationClass2 = confidenceInterval.computeConfidenceInterval(BMCloudletPopulationClass2,batch_size);
        System.out.println("glob pop: [" + BMCloudletPopulationClass2[0] + " , " + BMCloudletPopulationClass2[1] + " )");

        BMCloudPopulationClass2[0] = computeMean(BMCloudPopulationClass2[0]);
        BMCloudPopulationClass2[1] = computeMean(BMCloudPopulationClass2[1]);
        BMCloudPopulationClass2 = confidenceInterval.computeConfidenceInterval(BMCloudletPopulationClass2,batch_size);
        System.out.println("glob pop: [" +  BMCloudPopulationClass2[0] + " , " +  BMCloudPopulationClass2[1] + " )");
    }



    public double computeMean(double val)
    {
        double avg = val/num_batch;
        return avg;
    }


    public void computeBatchSize(){
        batch_size = (int) (iterations / num_batch);
    }

    public int getBatchSize(){
        return batch_size;
    }


    /**
     * Methods to insert welford means into arrays at the end of the batch
     */
    public void updateBMGlobalPopulation(double valueToInsert, int index) {
        double v = BMGlobalPopulation[index] + valueToInsert;
        BMGlobalPopulation[index] = v;
    }

    public void updateBMCloudletPopulation(double valueToInsert, int index) {
        double v = BMCloudletPopulation[index] + valueToInsert;
        BMCloudletPopulation[index] = v;
    }


    public void updateBMCloudPopulation ( double valueToInsert, int index){
        double v = BMCloudPopulation[index] + valueToInsert;
        BMCloudPopulation[index] = v;
    }

    public void updateBMGlobalPopulationClass1 ( double valueToInsert, int index){
        double v = BMGlobalPopulationClass1[index] + valueToInsert;
        BMGlobalPopulationClass1[index] = v;
    }

    public void updateBMCloudletPopulationClass1 ( double valueToInsert, int index){
        double v =  BMCloudletPopulationClass1[index] + valueToInsert;
        BMCloudletPopulationClass1[index] = v;
    }

    public void updateBMCloudPopulationClass1 ( double valueToInsert, int index){
        double v = BMCloudPopulationClass1[index] + valueToInsert;
        BMCloudPopulationClass1[index] = v;
    }

    public void updateBMGlobalPopulationClass2 ( double valueToInsert, int index){
        double v = BMGlobalPopulationClass2[index] + valueToInsert;
        BMGlobalPopulationClass2[index] = v;
    }

    public void updateBMCloudletPopulationClass2 ( double valueToInsert, int index){
        double v = BMCloudletPopulationClass2[index] + valueToInsert;
        BMCloudletPopulationClass2[index] = v;
    }

    public void updateBMCloudPopulationClass2 (double valueToInsert, int index){
        double v = BMCloudPopulationClass2[index] + valueToInsert;
        System.out.println(v);
        BMCloudPopulationClass2[index] = v;
    }

    public double[] getBMGlobalPopulation() {
        return BMGlobalPopulation;
    }

    public double[] getBMCloudletPopulation() {
        return BMCloudletPopulation;
    }

    public double[] getBMCloudPopulation() {
        return BMCloudPopulation;
    }

    public double[] getBMGlobalPopulationClass1() {
        return BMGlobalPopulationClass1;
    }

    public double[] getBMCloudletPopulationClass1() {
        return BMCloudletPopulationClass1;
    }

    public double[] getBMCloudPopulationClass1() {
        return BMCloudPopulationClass1;
    }

    public double[] getBMGlobalPopulationClass2() {
        return BMGlobalPopulationClass2;
    }

    public double[] getBMCloudletPopulationClass2() {
        return BMCloudletPopulationClass2;
    }

    public double[] getBMCloudPopulationClass2() {
        return BMCloudPopulationClass2;
    }






}



