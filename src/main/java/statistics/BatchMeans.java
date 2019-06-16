package statistics;

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

    //THROUGHPUT
    private ArrayList<Double> avgSystemT;
    private ArrayList<Double> avgCloudletT;
    private ArrayList<Double> avgCloudT;
    private ArrayList<Double> avgSystemT1;
    private ArrayList<Double> avgCloudletT1;
    private ArrayList<Double> avgCloudT1;
    private ArrayList<Double> avgSystemT2;
    private ArrayList<Double> avgCloudletT2;
    private ArrayList<Double> avgCloudT2;

    private ArrayList<Double> BMSysThroughput;
    private ArrayList<Double> BMSysThroughput1;
    private ArrayList<Double> BMSysThroughput2;
    private ArrayList<Double> BMCletThroughput;
    private ArrayList<Double> BMCletThroughput1;
    private ArrayList<Double> BMCletThroughput2;
    private ArrayList<Double> BMCloudThroughput;
    private ArrayList<Double> BMCloudThroughput1;
    private ArrayList<Double> BMCloudThroughput2;



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



    public double[] getBMMean(ArrayList<Double> values) {

            if(values.size()!=num_batch){
                System.out.println("cicli: " + values.size() +  ", num_batch: " + num_batch);
                System.exit(-1);
            }
            return confidenceInterval.computeConfidenceInterval(values);
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




    /**
     * GETTER FOR STATISTICS
     */
    public double[] getMeanBMGlobalPopulation(int classId) {
        double[] res = new double[2];
        switch (classId){
            case 0: res = getBMMean(BMGlobalPopulation); break;
            case 1: res = getBMMean(BMGlobalPopulation_1); break;
            case 2: res = getBMMean(BMGlobalPopulation_2); break;
        }
        return res;
    }

    public double[] getMeanBMCloudletPopulation(int classId) {
        double[] res = new double[2];
        switch (classId){
            case 0: res = getBMMean(BMCloudletPopulation); break;
            case 1: res = getBMMean(BMCloudletPopulation_1); break;
            case 2: res = getBMMean(BMCloudletPopulation_2); break;
        }
        return res;
    }

    public double[] getMeanBMCloudPopulation(int classId) {
        double[] res = new double[2];
        switch (classId){
            case 0: res = getBMMean(BMCloudPopulation); break;
            case 1: res = getBMMean(BMCloudPopulation_1); break;
            case 2: res = getBMMean(BMCloudPopulation_2); break;
        }
        return res;
    }


    public ArrayList<Double> getAvgThroughputArray(int index, String s){
        ArrayList<Double> var = new ArrayList<>();
        switch (s){
            case "sys":
                switch(index){
                    case 0: var = avgSystemT;break;
                    case 1: var = avgSystemT1; break;
                    case 2: var = avgSystemT2; break;
                }break;
            case "clet":
                switch(index){
                    case 0: var = avgCloudletT;break;
                    case 1: var = avgCloudletT1; break;
                    case 2: var = avgCloudletT2; break;
                }break;
            case "cloud":
                switch(index){
                    case 0: var =avgCloudT;break;
                    case 1: var = avgCloudT1; break;
                    case 2: var = avgCloudT2; break;
                }break;
        }
        return var;
    }




    public void updateBMAvgTroughputArray(double currentValue, int index)
    {
        switch (index){
            case 0: avgSystemT.add(currentValue); break;
            case 1: avgSystemT1.add(currentValue); break;
            case 2: avgSystemT2.add(currentValue);break;
        }
    }

    public void updateBMAvgCletTroughputArray(double currentValue, int index)
    {
        switch (index){
            case 0: avgCloudletT.add(currentValue); break;
            case 1: avgCloudletT1.add(currentValue); break;
            case 2: avgCloudletT2.add(currentValue);break;
        }
    }

    public void updateBMAvgCloudTroughputArray(double currentValue, int index)
    {
        switch (index){
            case 0: avgCloudT.add(currentValue); break;
            case 1: avgCloudT1.add(currentValue); break;
            case 2: avgCloudT2.add(currentValue);break;
        }
    }


    /**
     * NEWWWWWWWW
     */

    public void updateNewThroughputBMArray(double value, String s, int index){
        switch(s){
            case "sys":
                switch(index){
                    case 0: BMSysThroughput.add(value);break;
                    case 1: BMSysThroughput1.add(value); break;
                    case 2: BMSysThroughput2.add(value); break;
                }break;
            case "clet":
                switch(index){
                    case 0: BMCletThroughput.add(value);break;
                    case 1: BMCletThroughput1.add(value); break;
                    case 2: BMCletThroughput2.add(value); break;
                }break;
            case "cloud":
                switch(index){
                    case 0: BMCloudThroughput.add(value);break;
                    case 1: BMCloudThroughput1.add(value); break;
                    case 2: BMCloudThroughput2.add(value); break;
                }break;
        }
    }

    public double[] getNewBMThroughput(String s, int index){
        double[] res = new double[2];
        switch(s){
            case "sys":
                switch(index){
                    case 0: res = getBMMean(BMSysThroughput);break;
                    case 1: res = getBMMean(BMSysThroughput1); break;
                    case 2: res = getBMMean(BMSysThroughput2); break;
                }break;
            case "clet":
                switch(index){
                    case 0: res = getBMMean(BMCletThroughput);break;
                    case 1: res = getBMMean(BMCletThroughput1);break;
                    case 2: res = getBMMean(BMCletThroughput2);break;
                }break;
            case "cloud":
                switch(index){
                    case 0: res = getBMMean(BMCloudThroughput);break;
                    case 1: res = getBMMean(BMCloudThroughput1);break;
                    case 2: res = getBMMean(BMCloudThroughput2);break;
                }break;
        }
        return res;
    }

    public double[] getBMAvgSystemThroughput(int index){
        double[] res = new double[2];
        switch (index){
            case 0: res = getBMMean(avgSystemT);
                break;
            case 1: res = getBMMean(avgSystemT1);
                break;
            case 2: res = getBMMean(avgSystemT2);
                break;
        }
        return res;
    }

    public double[] getBMAvgCletThroughput(int index){
        double[] res = new double[2];
        switch (index){
            case 0: res = getBMMean(avgCloudletT);
                break;
            case 1: res = getBMMean(avgCloudletT1);
                break;
            case 2: res = getBMMean(avgCloudletT2);
                break;
        }
        return res;
    }


    public double[] getBMAvgCloudThroughput(int index){
        double[] res = new double[2];
        switch (index){
            case 0: res = getBMMean(avgCloudT);
                break;
            case 1: res = getBMMean(avgCloudT1);
                break;
            case 2: res = getBMMean(avgCloudT2);
                break;
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

        avgSystemT = new ArrayList<>();
        avgSystemT1 = new ArrayList<>();
        avgSystemT2 = new ArrayList<>();
        avgCloudletT = new ArrayList<>();
        avgCloudletT1 = new ArrayList<>();
        avgCloudletT2 = new ArrayList<>();
        avgCloudT =  new ArrayList<>();
        avgCloudT1 =  new ArrayList<>();
        avgCloudT2 =  new ArrayList<>();

        //NEW
        BMSysThroughput = new ArrayList<>();
        BMSysThroughput1 = new ArrayList<>();
        BMSysThroughput2= new ArrayList<>();
        BMCletThroughput = new ArrayList<>();
        BMCletThroughput1 = new ArrayList<>();
        BMCletThroughput2 = new ArrayList<>();
        BMCloudThroughput = new ArrayList<>();
        BMCloudThroughput1 = new ArrayList<>();
        BMCloudThroughput2 = new ArrayList<>();



    }
}
