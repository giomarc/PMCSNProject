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

    //POPULATION BATCH MEANS ARRAY
    private ArrayList<Double> BMGlobalPopulation;
    private ArrayList<Double> BMCloudletPopulation;
    private ArrayList<Double> BMCloudPopulation;
    private ArrayList<Double> BMGlobalPopulation_1;
    private ArrayList<Double> BMCloudletPopulation_1;
    private ArrayList<Double> BMCloudPopulation_1;
    private ArrayList<Double> BMGlobalPopulation_2;
    private ArrayList<Double> BMCloudletPopulation_2;
    private ArrayList<Double> BMCloudPopulation_2;

    //THROUGHPUT BATCH MEANS ARRAY
    private ArrayList<Double> BMSysThroughput;
    private ArrayList<Double> BMSysThroughput1;
    private ArrayList<Double> BMSysThroughput2;
    private ArrayList<Double> BMCletThroughput;
    private ArrayList<Double> BMCletThroughput1;
    private ArrayList<Double> BMCletThroughput2;
    private ArrayList<Double> BMCloudThroughput;
    private ArrayList<Double> BMCloudThroughput1;
    private ArrayList<Double> BMCloudThroughput2;

    //RESPONSE TIME BATCH ARRAY
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

    /*
     * computes batch size
     */
    double getBatchSize(){
        batch_size = (double) (iterations / num_batch);
        return batch_size;
    }


    /**
     * Computes Confidence Interval from batches
     */
    public double[] getBMMean(ArrayList<Double> values) {

        if(values.size()!=num_batch){
            System.out.println("cicli: " + values.size() +  ", num_batch: " + num_batch + "  ");
            System.exit(-1);
        }
        return confidenceInterval.computeConfidenceInterval(values);
    }

    /*
     * Return array containing batch sample means of population
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
     * Return Confidence Interval from Batch Arrays
     * @oaram classId: 0: both class 1 and 2, 1: class 1, 2: class 2
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
     * Insert actual throughput mean into Batch array
     * @param value actual mean to insert
     * @param s sys = system, clet = cloudlet, cloud = cloud
     * @param index 0: both class 1 and 2, 1: class 1, 2: class 2
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


    /**
     * Return throughput confidence interval
     * @param s system to compute (sys = system, clet = cloudlet, cloud = cloud)
     * @param index (0: both class 1 and 2, 1: class 1, 2 class 2)
     * @return
     */
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


    /**
     * Return thoughput array with batches
     */
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


    /**
     * Insert new mean value into Batch Array
     */
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

    /**
     * Compute confidence interval for Response time
     */
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

        this.BMGlobalPopulation = new ArrayList<>();
        this.BMCloudletPopulation = new ArrayList<>();
        this.BMCloudPopulation = new ArrayList<>();
        this.BMGlobalPopulation_1 = new ArrayList<>();
        this.BMCloudletPopulation_1 = new ArrayList<>();
        this.BMCloudPopulation_1 = new ArrayList<>();
        this.BMGlobalPopulation_2 = new ArrayList<>();
        this.BMCloudletPopulation_2 = new ArrayList<>();
        this.BMCloudPopulation_2 = new ArrayList<>();

        this.BMSysThroughput = new ArrayList<>();
        this.BMSysThroughput1 = new ArrayList<>();
        this.BMSysThroughput2= new ArrayList<>();
        this.BMCletThroughput = new ArrayList<>();
        this.BMCletThroughput1 = new ArrayList<>();
        this.BMCletThroughput2 = new ArrayList<>();
        this.BMCloudThroughput = new ArrayList<>();
        this.BMCloudThroughput1 = new ArrayList<>();
        this.BMCloudThroughput2 = new ArrayList<>();

        this.BMSysTime = new ArrayList<>();
        this.BMSysTime1 = new ArrayList<>();
        this.BMSysTime2 = new ArrayList<>();
        this.BMCletTime = new ArrayList<>();
        this.BMCletTime1 = new ArrayList<>();
        this.BMCletTime2 = new ArrayList<>();
        this.BMCloudTime = new ArrayList<>();
        this.BMCloudTime1 = new ArrayList<>();
        this.BMCloudTime2 = new ArrayList<>();
    }
}
