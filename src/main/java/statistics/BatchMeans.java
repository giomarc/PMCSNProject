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
    private ArrayList<Double> BMGlobalPopulation_2;
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


    double getBatchSize(){
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
            confidenceInterval.computeConfidenceInterval(values);
            return value /num_batch;
        }
        else
            return values.get(0);
    }



    /**
     * Getter
     */
    public ArrayList<Double> getBMGlobalPopulation(int classId){
        ArrayList<Double> mean = new ArrayList<>();
        switch (classId){
            case 0: mean = BMGlobalPopulation;
            break;
            case 1: mean =  BMGlobalPopulation_1;
            break;
            case 2: mean = BMGlobalPopulation_2;
            break;
        }
        return mean;
    }

    public ArrayList<Double> getBMCloudletPopulation(int classId) {
        ArrayList<Double> mean = new ArrayList<>();
        switch (classId){
            case 0: mean = BMCloudletPopulation;
                break;
            case 1: mean =  BMCloudletPopulation_1;
                break;
            case 2: mean = BMCloudletPopulation_2;
                break;
        }
        return mean;
    }

    public ArrayList<Double> getBMCloudPopulation(int classId) {
        ArrayList<Double> mean = new ArrayList<>();
        switch (classId){
            case 0: mean = BMCloudPopulation;
                break;
            case 1: mean =  BMCloudPopulation_1;
                break;
            case 2: mean = BMCloudPopulation_2;
                break;
        }
        return mean;
    }


    public ArrayList<Double> getBMVarianceGlobalPopulation(int classId) {
        ArrayList<Double> var = new ArrayList<>();
        switch (classId){
            case 0: var = BMVarGlobalPopulation;
                break;
            case 1: var =  BMVarGlobalPopulation_1;
                break;
            case 2: var =  BMVarGlobalPopulation_2;
                break;
        }
        return var;
    }

    public ArrayList<Double> getBMVarianceCloudletPopulation(int classId) {
        ArrayList<Double> var = new ArrayList<>();
        switch (classId){
            case 0: var = BMVarCloudletPopulation;
                break;
            case 1: var =  BMVarCloudletPopulation_1;
                break;
            case 2: var =  BMVarCloudletPopulation_2;
                break;
        }
        return var;
    }

    public ArrayList<Double> getBMVarianceCloudPopulation(int classId) {
        ArrayList<Double> var = new ArrayList<>();
        switch (classId){
            case 0: var = BMVarCloudPopulation;
                break;
            case 1: var =  BMVarCloudPopulation_1;
                break;
            case 2: var =  BMVarCloudPopulation_2;
                break;
        }
        return var;
    }


    /**
     * GETTER FOR STATISTICS
     */
    public double getMeanBMGlobalPopulation(int classId) {
        double res = 0.0;
        switch (classId){
            case 0: res = getBMMean(BMGlobalPopulation); break;
            case 1: res = getBMMean(BMGlobalPopulation_1); break;
            case 2: res = getBMMean(BMGlobalPopulation_2); break;
        }
        return res;
    }

    public double getMeanBMCloudletPopulation(int classId) {
        double res = 0.0;
        switch (classId){
            case 0: res = getBMMean(BMCloudletPopulation); break;
            case 1: res = getBMMean(BMCloudletPopulation_1); break;
            case 2: res = getBMMean(BMCloudletPopulation_2); break;
        }
        return res;
    }

    public double getMeanBMCloudPopulation(int classId) {
        double res = 0.0;
        switch (classId){
            case 0: res = getBMMean(BMCloudPopulation); break;
            case 1: res = getBMMean(BMCloudPopulation_1); break;
            case 2: res = getBMMean(BMCloudPopulation_2); break;
        }
        return res;
    }



    public double getMeanBMVarianceGlobalPopulation(int classId) {
        double res = 0.0;
        switch (classId){
            case 0: res = getBMMean(BMVarGlobalPopulation); break;
            case 1: res = getBMMean(BMVarGlobalPopulation_1); break;
            case 2: res = getBMMean(BMVarGlobalPopulation_2); break;
        }
        return res;
    }

    public double getMeanBMVarianceCloudletPopulation(int classId) {
        double res = 0.0;
        switch (classId){
            case 0: res = getBMMean(BMVarCloudletPopulation); break;
            case 1: res = getBMMean(BMVarCloudletPopulation_1); break;
            case 2: res = getBMMean(BMVarCloudletPopulation_2); break;
        }
        return res;
    }

    public double getMeanBMVarianceCloudPopulation(int classId) {
        double res = 0.0;
        switch (classId){
            case 0: res = getBMMean(BMVarCloudPopulation); break;
            case 1: res = getBMMean(BMVarCloudPopulation_1); break;
            case 2: res = getBMMean(BMVarCloudPopulation_2); break;
        }
        return res;
    }


    /**
     * SETTER
     */
    void setBMGlobalPopulation(double currentValue) {
         BMGlobalPopulation.add(currentValue);
    }

    void setBMCloudletPopulation(double currentValue) {
        BMCloudletPopulation.add(currentValue);
    }

    void setBMCloudPopulation(double currentValue) {
        BMCloudPopulation.add(currentValue);
    }

    void setBMGlobalPopulationClass1(double currentValue) {
        BMGlobalPopulation_1.add(currentValue);
    }

    void setBMCloudletPopulationClass1(double currentValue) {
        BMCloudletPopulation_1.add(currentValue);
    }

    void setBMCloudPopulationClass1(double currentValue) {
        BMCloudPopulation_1.add(currentValue);
    }

    void setBMGlobalPopulation_2(double currentValue) {
        BMGlobalPopulation_2.add(currentValue);
    }

    void setBMCloudletPopulationClass2(double currentValue) {
        BMCloudletPopulation_2.add(currentValue);
    }

    void setBMCloudPopulationClass2(double currentValue) {
        BMCloudPopulation_2.add(currentValue);
    }

    void setBMVarianceGlobalPopulation(double currentValue) {
        BMVarGlobalPopulation.add(currentValue);
    }

    void setBMVarianceCloudletPopulation(double currentValue) {
        BMVarCloudletPopulation.add(currentValue);
    }

    void setBMVarianceCloudPopulation(double currentValue) {
        BMVarCloudPopulation.add(currentValue);
    }

    void setBMVarianceGlobalPopulationClass1(double currentValue) {
        BMVarGlobalPopulation_1.add(currentValue);
    }

    void setBMVarianceCloudletPopulationClass1(double currentValue) {
        BMVarCloudletPopulation_1.add(currentValue);
    }

    void setBMVarianceCloudPopulationClass1(double currentValue) {
        BMVarCloudPopulation_1.add(currentValue);
    }

    void setBMVarianceGlobalPopulationClass2(double currentValue) {
        BMVarGlobalPopulation_2.add(currentValue);
    }

    void setBMVarianceCloudletPopulationClass2(double currentValue) {
        BMVarCloudletPopulation_2.add(currentValue);
    }

    void setBMVarianceCloudPopulationClass2(double currentValue) {
        BMVarCloudPopulation_2.add(currentValue);
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
        BMGlobalPopulation_2 = new ArrayList<>();
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
