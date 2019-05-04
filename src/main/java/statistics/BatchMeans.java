package statistics;

import cloud.Cloud;
import cloudlet.Cloudlet;
import system.SystemConfiguration;

import java.util.ArrayList;

@SuppressWarnings("Duplicates")
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

    //THROUGHPUT
    private ArrayList<Double> avgSystemT;
    private ArrayList<Double> avgCloudletT;
    private ArrayList<Double> avgCloudT;
    private ArrayList<Double> varSystemT;
    private ArrayList<Double> varCloudletT;
    private ArrayList<Double> varCloudT;



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



    public double getBMMean(ArrayList<Double> values, String meanOrVar) {
            double value = 0;
            if(values.size()!=num_batch){
                System.out.println("cicli: " + values.size() +  ", num_batch: " + num_batch);
                System.exit(-1);
            }
            for (double d : values) {
                value += d;
            }
            if(meanOrVar.contains("m"))
                confidenceInterval.computeConfidenceInterval(values);
            return value /num_batch;
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
            case 0: res = getBMMean(BMGlobalPopulation,"m"); break;
            case 1: res = getBMMean(BMGlobalPopulation_1,"m"); break;
            case 2: res = getBMMean(BMGlobalPopulation_2,"m"); break;
        }
        return res;
    }

    public double getMeanBMCloudletPopulation(int classId) {
        double res = 0.0;
        switch (classId){
            case 0: res = getBMMean(BMCloudletPopulation,"m"); break;
            case 1: res = getBMMean(BMCloudletPopulation_1,"m"); break;
            case 2: res = getBMMean(BMCloudletPopulation_2,"m"); break;
        }
        return res;
    }

    public double getMeanBMCloudPopulation(int classId) {
        double res = 0.0;
        switch (classId){
            case 0: res = getBMMean(BMCloudPopulation,"m"); break;
            case 1: res = getBMMean(BMCloudPopulation_1,"m"); break;
            case 2: res = getBMMean(BMCloudPopulation_2,"m"); break;
        }
        return res;
    }



    public double getMeanBMVarianceGlobalPopulation(int classId) {
        double res = 0.0;
        switch (classId){
            case 0: res = getBMMean(BMVarGlobalPopulation,"v"); break;
            case 1: res = getBMMean(BMVarGlobalPopulation_1,"v"); break;
            case 2: res = getBMMean(BMVarGlobalPopulation_2,"v"); break;
        }
        return res;
    }

    public double getMeanBMVarianceCloudletPopulation(int classId) {
        double res = 0.0;
        switch (classId){
            case 0: res = getBMMean(BMVarCloudletPopulation,"v"); break;
            case 1: res = getBMMean(BMVarCloudletPopulation_1,"v"); break;
            case 2: res = getBMMean(BMVarCloudletPopulation_2,"v"); break;
        }
        return res;
    }

    public double getMeanBMVarianceCloudPopulation(int classId) {
        double res = 0.0;
        switch (classId){
            case 0: res = getBMMean(BMVarCloudPopulation,"v"); break;
            case 1: res = getBMMean(BMVarCloudPopulation_1,"v"); break;
            case 2: res = getBMMean(BMVarCloudPopulation_2,"v"); break;
        }
        return res;
    }

    public ArrayList<Double> getAvgThroughputArray(int index){
        ArrayList<Double> var = new ArrayList<>();
        switch (index){
            case 0: var = avgSystemT; break;
            case 1: var = avgCloudletT; break;
            case 2: var = avgCloudT; break;
        }
        return var;
    }

    public void updateBMAvgTroughputArray(double currentValue, int index)
    {
        switch (index){
            case 0: avgSystemT.add(currentValue); break;
            case 1: avgCloudletT.add(currentValue); break;
            case 2: avgCloudT.add(currentValue);break;
        }
    }

    public double getBMAvgThroughput(int index){
        double res = 0.0;
        switch (index){
            case 0: res = getBMMean(avgSystemT,"m"); break;
            case 1: res = getBMMean(avgCloudletT,"m"); break;
            case 2: res = getBMMean(avgCloudT,"m"); break;
        }
        return res;
    }

    public ArrayList<Double> getVarThroughputArray(int index){
        ArrayList<Double> var = new ArrayList<>();
        switch (index){
            case 0: var = varSystemT; break;
            case 1: var = varCloudletT; break;
            case 2: var = varCloudT; break;
        }
        return var;
    }

    public void updateBMVarThroughputArray(double currentValue, int index)
    {
        switch (index){
            case 0: varSystemT.add(currentValue); break;
            case 1: varCloudletT.add(currentValue); break;
            case 2: varCloudT.add(currentValue);break;
        }
    }

    public double getBMVarThroughput(int index){
        double res = 0.0;
        switch (index){
            case 0: res = getBMMean(varSystemT,"v"); break;
            case 1: res = getBMMean(varCloudletT,"v"); break;
            case 2: res = getBMMean(varCloudT,"v"); break;
        }
        return res;
    }




    /**
     * SETTER
     */
    void updateBMGlobalPopulation(double currentValue) {
         BMGlobalPopulation.add(currentValue);
    }

    void updateBMCloudletPopulation(double currentValue) {
        BMCloudletPopulation.add(currentValue);
    }

    void updateBMCloudPopulation(double currentValue) {
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

        avgSystemT = new ArrayList<>();
        avgCloudletT = new ArrayList<>();
        avgCloudT =  new ArrayList<>();
        varSystemT = new ArrayList<>();
        varCloudletT = new ArrayList<>();
        varCloudT =  new ArrayList<>();
    }
}
