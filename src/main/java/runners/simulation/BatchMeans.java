package runners.simulation;

import runners.Statistics.JobStatistics;
import runners.Statistics.Statistics;
import system.SystemConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

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
        confidenceInterval = ConfidenceInterval.getInstance();
        iterations = SystemConfiguration.ITERATIONS;
        num_batch = SystemConfiguration.NUM_BATCH;
        computeBatchSize();
        init();
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
        double[]res = confidenceInterval.computeConfidenceInterval(BMGlobalPopulation,num_batch);
        System.out.println("global population: [" + res[0] + " , " + res[1] + " )");

        BMCloudletPopulation[0] = computeMean(BMCloudletPopulation[0]);
        BMCloudletPopulation[1] = computeMean(BMCloudletPopulation[1]);
        res = confidenceInterval.computeConfidenceInterval(BMCloudletPopulation,num_batch);
        System.out.println("cloudlet population: [" + res[0] + " , " + res[1] + " )");

        BMCloudPopulation[0] = computeMean(BMCloudPopulation[0]);
        BMCloudPopulation[1] = computeMean(BMCloudPopulation[1]);
        res = confidenceInterval.computeConfidenceInterval(BMCloudPopulation,num_batch);
        System.out.println("cloud population: [" + res[0] + " , " + res[1] + " )");

        BMGlobalPopulationClass1[0] = computeMean(BMGlobalPopulationClass1[0]);
        BMGlobalPopulationClass1[1] = computeMean(BMGlobalPopulationClass1[1]);
        res = confidenceInterval.computeConfidenceInterval(BMGlobalPopulationClass1,num_batch);
        System.out.println("glob pop class 1: [" + res[0] + " , " + res[1] + " )");

        BMCloudletPopulationClass1[0] = computeMean(BMCloudletPopulationClass1[0]);
        BMCloudletPopulationClass1[1] = computeMean(BMCloudletPopulationClass1[1]);
        res = confidenceInterval.computeConfidenceInterval(BMCloudletPopulationClass1,num_batch);
        System.out.println("cloudlet pop class 1: [" + res[0] + " , " + res[1] + " )");

        BMCloudPopulationClass1[0] = computeMean(BMCloudPopulationClass1[0]);
        BMCloudPopulationClass1[1] = computeMean(BMCloudPopulationClass1[1]);
        res = confidenceInterval.computeConfidenceInterval(BMCloudPopulationClass1,num_batch);
        System.out.println("cloud pop class 1: [" + res[0] + " , " + res[1] + " )");

        BMGlobalPopulationClass2[0] = computeMean(BMGlobalPopulationClass2[0]);
        BMGlobalPopulationClass2[1] = computeMean(BMGlobalPopulationClass2[1]);
        res = confidenceInterval.computeConfidenceInterval(BMGlobalPopulationClass2,num_batch);
        System.out.println("glob pop class 2: [" + res[0] + " , " + res[1] + " )");

        BMCloudletPopulationClass2[0] = computeMean(BMCloudletPopulationClass2[0]);
        BMCloudletPopulationClass2[1] = computeMean(BMCloudletPopulationClass2[1]);
        res = confidenceInterval.computeConfidenceInterval(BMCloudletPopulationClass2,num_batch);
        System.out.println("cloudlet pop class 2: [" + res[0] + " , " + res[1] + " )");

        BMCloudPopulationClass2[0] = computeMean(BMCloudPopulationClass2[0]);
        BMCloudPopulationClass2[1] = computeMean(BMCloudPopulationClass2[1]);
        res = confidenceInterval.computeConfidenceInterval(BMCloudletPopulationClass2,num_batch);
        System.out.println("cloud pop class 2: [" + res[0] + " , " + res[1] + " )");
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


    public void init(){
        BMGlobalPopulation = new double[2];
        BMCloudletPopulation = new double[2];
        BMCloudPopulation = new double[2];
        BMGlobalPopulationClass1 = new double[2];
        BMCloudletPopulationClass1= new double[2];
        BMCloudPopulationClass1= new double[2];
        BMGlobalPopulationClass2= new double[2];
        BMCloudletPopulationClass2= new double[2];
        BMCloudPopulationClass2= new double[2];
    }


    @Override
    public String toString() {
        return "BatchMeans{" +
                "BMGlobalPopulation=" + Arrays.toString(BMGlobalPopulation) +
                ", BMCloudletPopulation=" + Arrays.toString(BMCloudletPopulation) +
                ", BMCloudPopulation=" + Arrays.toString(BMCloudPopulation) +
                ", BMGlobalPopulationClass1=" + Arrays.toString(BMGlobalPopulationClass1) +
                ", BMCloudletPopulationClass1=" + Arrays.toString(BMCloudletPopulationClass1) +
                ", BMCloudPopulationClass1=" + Arrays.toString(BMCloudPopulationClass1) +
                ", BMGlobalPopulationClass2=" + Arrays.toString(BMGlobalPopulationClass2) +
                ", BMCloudletPopulationClass2=" + Arrays.toString(BMCloudletPopulationClass2) +
                ", BMCloudPopulationClass2=" + Arrays.toString(BMCloudPopulationClass2) +
                '}';
    }

}



