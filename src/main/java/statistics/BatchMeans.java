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

    //POPULATION BATCH MEANS
    private ArrayList<Double> BMGlobalPopulation;
    private ArrayList<Double> BMCloudletPopulation;
    private ArrayList<Double> BMCloudPopulation;
    private ArrayList<Double> BMGlobalPopulation_1;
    private ArrayList<Double> BMCloudletPopulation_1;
    private ArrayList<Double> BMCloudPopulation_1;
    private ArrayList<Double> BMGlobalPopulation_2;
    private ArrayList<Double> BMCloudletPopulation_2;
    private ArrayList<Double> BMCloudPopulation_2;

    //THROUGHPUT BATCH MEANS
    private ArrayList<Double> BMSysThroughput;
    private ArrayList<Double> BMSysThroughput1;
    private ArrayList<Double> BMSysThroughput2;
    private ArrayList<Double> BMCletThroughput;
    private ArrayList<Double> BMCletThroughput1;
    private ArrayList<Double> BMCletThroughput2;
    private ArrayList<Double> BMCloudThroughput;
    private ArrayList<Double> BMCloudThroughput1;
    private ArrayList<Double> BMCloudThroughput2;

    //RESPONSE TIME BATCH
    private ArrayList<Double> BMSysTime;
    private ArrayList<Double> BMSysTime1;
    private ArrayList<Double> BMSysTime2;
    private ArrayList<Double> BMCletTime;
    private ArrayList<Double> BMCletTime1;
    private ArrayList<Double> BMCletTime2;
    private ArrayList<Double> BMCloudTime;
    private ArrayList<Double> BMCloudTime1;
    private ArrayList<Double> BMCloudTime2;





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


    /**
     * NEWWWWWWWW
     */

    public void updateThroughputBMArray(double value, String s, int index){
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

    public double[] getBMThroughput(String s, int index){
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


    public ArrayList<Double> getBMThroughputArray(int index, String s){
        ArrayList res =  new ArrayList();
        switch(s){
            case "sys":
                switch(index){
                    case 0: res = BMSysThroughput;break;
                    case 1: res = BMSysThroughput1; break;
                    case 2: res = BMSysThroughput2; break;
                }break;
            case "clet":
                switch(index){
                    case 0: res = BMCletThroughput;break;
                    case 1: res = BMCletThroughput1 ;break;
                    case 2: res = BMCletThroughput2; break;
                }break;
            case "cloud":
                switch(index){
                    case 0: res = BMCloudThroughput;break;
                    case 1: res = BMCloudThroughput1;break;
                    case 2: res = BMCloudThroughput2;break;
                }break;
        }
        return res;
    }

    public void updateTimeBMArray(double value, String s, int index){
        switch(s){
            case "sys":
                switch(index){
                    case 0: BMSysTime.add(value);break;
                    case 1: BMSysTime1.add(value); break;
                    case 2: BMSysTime2.add(value); break;
                }break;
            case "clet":
                switch(index){
                    case 0: BMCletTime.add(value);break;
                    case 1: BMCletTime1.add(value); break;
                    case 2: BMCletTime2.add(value); break;
                }break;
            case "cloud":
                switch(index){
                    case 0: BMCloudTime.add(value);break;
                    case 1: BMCloudTime1.add(value); break;
                    case 2: BMCloudTime2.add(value); break;
                }break;
        }
    }

    public double[] getBMTime(String s, int index){
        double[] res = new double[2];
        switch(s){
            case "sys":
                switch(index){
                    case 0: res = getBMMean(BMSysTime);break;
                    case 1: res = getBMMean(BMSysTime1); break;
                    case 2: res = getBMMean(BMSysTime2); break;
                }break;
            case "clet":
                switch(index){
                    case 0: res = getBMMean(BMCletTime);break;
                    case 1: res = getBMMean(BMCletTime1);break;
                    case 2: res = getBMMean(BMCletTime2);break;
                }break;
            case "cloud":
                switch(index){
                    case 0: res = getBMMean(BMCloudTime);break;
                    case 1: res = getBMMean(BMCloudTime1);break;
                    case 2: res = getBMMean(BMCloudTime2);break;
                }break;
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

        BMSysThroughput = new ArrayList<>();
        BMSysThroughput1 = new ArrayList<>();
        BMSysThroughput2= new ArrayList<>();
        BMCletThroughput = new ArrayList<>();
        BMCletThroughput1 = new ArrayList<>();
        BMCletThroughput2 = new ArrayList<>();
        BMCloudThroughput = new ArrayList<>();
        BMCloudThroughput1 = new ArrayList<>();
        BMCloudThroughput2 = new ArrayList<>();

        BMSysTime = new ArrayList<>();
        BMSysTime1 = new ArrayList<>();
        BMSysTime2 = new ArrayList<>();
        BMCletTime = new ArrayList<>();
        BMCletTime1 = new ArrayList<>();
        BMCletTime2 = new ArrayList<>();
        BMCloudTime = new ArrayList<>();
        BMCloudTime1 = new ArrayList<>();
        BMCloudTime2 = new ArrayList<>();



    }
}
