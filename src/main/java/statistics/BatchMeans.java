package statistics;

import system.SystemConfiguration;

import java.util.ArrayList;

public class BatchMeans {

    private ConfidenceInterval confidenceInterval;
    private long iterations;
    private double batch_size;
    private int num_batch;
    private static BatchMeans instance = null;

    //BATCH MEANS
    private ArrayList<Double> BMGlobalPopulation;
    private ArrayList<Double> BMCloudletPopulation;
    private ArrayList<Double> BMCloudPopulation;
    private ArrayList<Double> BMGlobalPopulation_1;
    private ArrayList<Double> BMCloudletPopulation_1;
    private ArrayList<Double> BMCloudPopulation_1;
    private ArrayList<Double> BMGlobalPopulationClass2;
    private ArrayList<Double> BMCloudletPopulation_2;
    private ArrayList<Double> BMCloudPopulation_2;

    //BATCH VARIANCE
    private ArrayList<Double> BMVarGlobalPopulation;
    private ArrayList<Double> BMVarCloudletPopulation;
    private ArrayList<Double> BMVarCloudPopulation;
    private ArrayList<Double> BMVarGlobalPopulation_1;
    private ArrayList<Double> BMVarCloudletPopulation_1;
    private ArrayList<Double> BMVarCloudPopulation_1;
    private ArrayList<Double> BMVarGlobalPopulation_2;
    private ArrayList<Double> BMVarCloudletPopulation_2;
    private ArrayList<Double> BMVarCloudPopulation_2;



    private BatchMeans(){
        confidenceInterval = ConfidenceInterval.getInstance();
        iterations = SystemConfiguration.ITERATIONS;
        num_batch = SystemConfiguration.NUM_BATCH;
        init();
    }

    public static BatchMeans getInstance(){
        if(instance == null)
            instance = new BatchMeans();
        return instance;
    }


    public double getBatchSize(){
        batch_size = (double) (iterations / num_batch);
        return batch_size;
    }



    /**
     * Methods to insert welford means into arrays at the end of the batch
     */
    public void updateBMG(ArrayList<Double> listToUpdate,double valueToInsert) {
        listToUpdate.add(valueToInsert);
    }



    public double getBMMean(ArrayList<Double> values) {
        if(SystemConfiguration.BATCH) {
            double value = 0;

            if(values.size()!=num_batch){
                System.out.println("cicli: " + values.size() +  ", num_batch: " + num_batch);
                System.exit(-1);
            }
            for (double d : values) {
                value += d;
            }
            confidenceInterval.computeConfidenceInterval(BMGlobalPopulation);
            return value /num_batch;
        }
        else
            return JobStatistics.getInstance().getMeanGlobalPopulation(0);
    }


    public double getBMVariance(ArrayList<Double> values) {
        if(SystemConfiguration.BATCH) {
            double value = 0;

            if(values.size()!=num_batch){
                System.out.println("cicli: " + values.size() +  ", num_batch: " + num_batch);
                System.exit(-1);
            }
            for (double d : values) {
                value += d;
            }
            confidenceInterval.computeConfidenceInterval(BMGlobalPopulation);
            return value /num_batch;
        }
        else
            return JobStatistics.getInstance().getVarGlobalPopulation(0);
    }


    /**
     * Getter
     */

    public ArrayList<Double> getBMGlobalPopulation() {
        return BMGlobalPopulation;
    }

    public ArrayList<Double> getBMCloudletPopulation() {
        return BMCloudletPopulation;
    }

    public ArrayList<Double> getBMCloudPopulation() {
        return BMCloudPopulation;
    }

    public ArrayList<Double> getBMGlobalPopulationClass1() {
        return BMGlobalPopulation_1;
    }

    public ArrayList<Double> getBMCloudletPopulationClass1() {
        return BMCloudletPopulation_1;
    }

    public ArrayList<Double> getBMCloudPopulationClass1() {
        return BMCloudPopulation_1;
    }

    public ArrayList<Double> getBMGlobalPopulationClass2() {
        return BMGlobalPopulationClass2;
    }

    public ArrayList<Double> getBMCloudletPopulationClass2() {
        return BMCloudletPopulation_2;
    }

    public ArrayList<Double> getBMCloudPopulationClass2() {
        return BMCloudPopulation_2;
    }

    public ArrayList<Double> getBMVarianceGlobalPopulation() {
        return BMVarGlobalPopulation;
    }

    public ArrayList<Double> getBMVarianceCloudletPopulation() {
        return BMVarCloudletPopulation;
    }

    public ArrayList<Double> getBMVarianceCloudPopulation() {
        return BMVarCloudPopulation;
    }

    public ArrayList<Double> getBMVarianceGlobalPopulationClass1() {
        return BMVarGlobalPopulation_1;
    }

    public ArrayList<Double> getBMVarianceCloudletPopulationClass1() {
        return BMVarCloudletPopulation_1;
    }

    public ArrayList<Double> getBMVarianceCloudPopulationClass1() {
        return BMVarCloudPopulation_1;
    }

    public ArrayList<Double> getBMVarianceGlobalPopulationClass2() {
        return BMVarGlobalPopulation_2;
    }

    public ArrayList<Double> getBMVarianceCloudletPopulationClass2() {
        return BMVarCloudletPopulation_2;
    }

    public ArrayList<Double> getBMVarianceCloudPopulationClass2() {
        return BMVarCloudPopulation_2;
    }

    public void reset(){
        init();
    }


    private void init(){

        BMGlobalPopulation = new ArrayList<>();
        BMCloudletPopulation = new ArrayList<>();
        BMCloudPopulation = new ArrayList<>();
        BMGlobalPopulation_1 = new ArrayList<>();
        BMCloudletPopulation_1 = new ArrayList<>();
        BMCloudPopulation_1 = new ArrayList<>();
        BMGlobalPopulationClass2 = new ArrayList<>();
        BMCloudletPopulation_2 = new ArrayList<>();
        BMCloudPopulation_2 = new ArrayList<>();

        BMVarGlobalPopulation = new ArrayList<>();
        BMVarCloudletPopulation = new ArrayList<>();
        BMVarCloudPopulation = new ArrayList<>();
        BMVarGlobalPopulation_1 = new ArrayList<>();
        BMVarCloudletPopulation_1 = new ArrayList<>();
        BMVarCloudPopulation_1 = new ArrayList<>();
        BMVarGlobalPopulation_2 = new ArrayList<>();
        BMVarCloudletPopulation_2 = new ArrayList<>();
        BMVarCloudPopulation_2 = new ArrayList<>();
    }
}
