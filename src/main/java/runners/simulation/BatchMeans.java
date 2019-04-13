package runners.simulation;

import runners.Statistics.JobStatistics;
import runners.Statistics.Statistics;
import system.SystemConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

public class BatchMeans {

    private ConfidenceInterval confidenceInterval;
    private double counter_batch;
    private long iterations;
    private double batch_size;
    private int num_batch;
    private static BatchMeans instance = null;



    //POPULATION BATCH
    //MEANS
    private ArrayList<Double> BMGlobalPopulation;
    private ArrayList<Double> BMCloudletPopulation;
    private ArrayList<Double> BMCloudPopulation;
    private ArrayList<Double> BMGlobalPopulationClass1;
    private ArrayList<Double> BMCloudletPopulationClass1;
    private ArrayList<Double> BMCloudPopulationClass1;
    private ArrayList<Double> BMGlobalPopulationClass2;
    private ArrayList<Double> BMCloudletPopulationClass2;
    private ArrayList<Double> BMCloudPopulationClass2;

    //VARIANCE
    private ArrayList<Double> BMVarianceGlobalPopulation;
    private ArrayList<Double> BMVarianceCloudletPopulation;
    private ArrayList<Double> BMVarianceCloudPopulation;
    private ArrayList<Double> BMVarianceGlobalPopulationClass1;
    private ArrayList<Double> BMVarianceCloudletPopulationClass1;
    private ArrayList<Double> BMVarianceCloudPopulationClass1;
    private ArrayList<Double> BMVarianceGlobalPopulationClass2;
    private ArrayList<Double> BMVarianceCloudletPopulationClass2;
    private ArrayList<Double> BMVarianceCloudPopulationClass2;


    //TIME STATISTICS BATCH

    private BatchMeans(){
        confidenceInterval = ConfidenceInterval.getInstance();
        iterations = SystemConfiguration.ITERATIONS;
        num_batch = SystemConfiguration.NUM_BATCH;
        computeBatchSize();
        init();
    }

    public static BatchMeans getInstance(){
        if(instance == null)
            instance = new BatchMeans();
        return instance;
    }


    public void computeBatchSize(){
        batch_size = (double) (iterations / num_batch);
    }

    public double getBatchSize(){
        return batch_size;
    }


    /**
     * Methods to insert welford means into arrays at the end of the batch
     */
    public void updateBMGlobalPopulation(double valueToInsert, double variance) {
        BMGlobalPopulation.add(valueToInsert);
        BMVarianceGlobalPopulation.add(variance);
    }

    public void updateBMCloudletPopulation(double valueToInsert, double variance) {
        BMCloudletPopulation.add(valueToInsert);
        BMVarianceCloudletPopulation.add(variance);
    }


    public void updateBMCloudPopulation(double valueToInsert, double variance){
        BMCloudPopulation.add(valueToInsert);
        BMVarianceCloudPopulation.add(variance);
    }

    public void updateBMGlobalPopulationClass1(double valueToInsert, double variance){
        BMGlobalPopulationClass1.add(valueToInsert);
        BMVarianceGlobalPopulationClass1.add(variance);
    }

    public void updateBMCloudletPopulationClass1 ( double valueToInsert, double variance){
        BMCloudletPopulationClass1.add(valueToInsert);
        BMVarianceCloudletPopulationClass1.add(variance);
    }

    public void updateBMCloudPopulationClass1 ( double valueToInsert, double variance){
        BMCloudPopulationClass1.add(valueToInsert);
        BMVarianceCloudPopulationClass1.add(variance);
    }

    public void updateBMGlobalPopulationClass2 ( double valueToInsert, double variance){
        BMGlobalPopulationClass2.add(valueToInsert);
        BMVarianceGlobalPopulationClass2.add(variance);
    }

    public void updateBMCloudletPopulationClass2 ( double valueToInsert, double variance){
        BMCloudletPopulationClass2.add(valueToInsert);
        BMVarianceCloudletPopulationClass2.add(variance);
    }

    public void updateBMCloudPopulationClass2 (double valueToInsert, double variance){
        BMCloudPopulationClass2.add(valueToInsert);
        BMVarianceCloudPopulationClass2.add(variance);
    }


    private void init(){
        counter_batch = 0;

        BMGlobalPopulation = new ArrayList<>();
        BMCloudletPopulation = new ArrayList<>();
        BMCloudPopulation = new ArrayList<>();
        BMGlobalPopulationClass1 = new ArrayList<>();
        BMCloudletPopulationClass1 = new ArrayList<>();
        BMCloudPopulationClass1 = new ArrayList<>();
        BMGlobalPopulationClass2 = new ArrayList<>();
        BMCloudletPopulationClass2 = new ArrayList<>();
        BMCloudPopulationClass2 = new ArrayList<>();

        BMVarianceGlobalPopulation = new ArrayList<>();
        BMVarianceCloudletPopulation = new ArrayList<>();
        BMVarianceCloudPopulation = new ArrayList<>();
        BMVarianceGlobalPopulationClass1 = new ArrayList<>();
        BMVarianceCloudletPopulationClass1 = new ArrayList<>();
        BMVarianceCloudPopulationClass1 = new ArrayList<>();
        BMVarianceGlobalPopulationClass2 = new ArrayList<>();
        BMVarianceCloudletPopulationClass2 = new ArrayList<>();
        BMVarianceCloudPopulationClass2 = new ArrayList<>();
    }


    //GET BATCH MEANS MEANS


    public double getMeanBMGlobalPopulation() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMGlobalPopulation) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch) {
                System.out.println("cicli: " + cycles + ", num_batch: " + num_batch);
                System.exit(-1);
            }

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getMeanGlobalPopulation();
    }

    public double getMeanBMCloudletPopulation() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMCloudletPopulation) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getMeanCloudletPopulation();
    }

    public double getMeanBMCloudPopulation() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMCloudPopulation) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getMeanCloudPopulation();
    }

    public double getMeanBMGlobalPopulationClass1() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMGlobalPopulationClass1) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getMeanGlobalPopulationClass1();
    }

    public double getMeanBMCloudletPopulationClass1() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMCloudletPopulationClass1) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getMeanCloudletPopulationClass1();
    }

    public double getMeanBMCloudPopulationClass1() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMCloudPopulationClass1) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getMeanCloudPopulationClass1();
    }

    public double getMeanBMGlobalPopulationClass2() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMGlobalPopulationClass2) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getMeanGlobalPopulationClass2();
    }

    public double getMeanBMCloudletPopulationClass2() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMCloudletPopulationClass2) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getMeanCloudletPopulationClass2();
    }

    public double getMeanBMCloudPopulationClass2() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMCloudPopulationClass2) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getMeanCloudPopulationClass2();
    }

    public double getMeanBMVarianceGlobalPopulation() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMVarianceGlobalPopulation) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getVarGlobalPopulation();
    }

    public double getMeanBMVarianceCloudletPopulation() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMVarianceCloudletPopulation) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getVarCloudletPopulation();
    }

    public double getMeanBMVarianceCloudPopulation() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMVarianceCloudPopulation) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getVarCloudPopulation();
    }

    public double getMeanBMVarianceGlobalPopulationClass1() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMVarianceGlobalPopulationClass1) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getVarGlobalPopulationClass1();
    }

    public double getMeanBMVarianceCloudletPopulationClass1() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMVarianceCloudletPopulationClass1) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getVarCloudletPopulationClass1();
    }

    public double getMeanBMVarianceCloudPopulationClass1() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMVarianceCloudPopulationClass1) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getVarCloudPopulationClass1();
    }

    public double getMeanBMVarianceGlobalPopulationClass2() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMVarianceGlobalPopulationClass2) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getVarGlobalPopulationClass2();
    }

    public double getMeanBMVarianceCloudletPopulationClass2() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMVarianceCloudletPopulationClass2) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getVarCloudletPopulationClass2();
    }

    public double getMeanBMVarianceCloudPopulationClass2() {
        if(SystemConfiguration.BATCH) {
            double value = 0;
            double cycles = 0;
            for (double d : BMVarianceCloudPopulationClass2) {
                value += d;
                cycles++;
            }

            if (cycles != num_batch)
                System.exit(-1);

            return value / cycles;
        }
        else
            return JobStatistics.getInstance().getVarCloudPopulationClass2();
    }


    // RETURN ARRAYS


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
        return BMGlobalPopulationClass1;
    }

    public ArrayList<Double> getBMCloudletPopulationClass1() {
        return BMCloudletPopulationClass1;
    }

    public ArrayList<Double> getBMCloudPopulationClass1() {
        return BMCloudPopulationClass1;
    }

    public ArrayList<Double> getBMGlobalPopulationClass2() {
        return BMGlobalPopulationClass2;
    }

    public ArrayList<Double> getBMCloudletPopulationClass2() {
        return BMCloudletPopulationClass2;
    }

    public ArrayList<Double> getBMCloudPopulationClass2() {
        return BMCloudPopulationClass2;
    }

    public ArrayList<Double> getBMVarianceGlobalPopulation() {
        return BMVarianceGlobalPopulation;
    }

    public ArrayList<Double> getBMVarianceCloudletPopulation() {
        return BMVarianceCloudletPopulation;
    }

    public ArrayList<Double> getBMVarianceCloudPopulation() {
        return BMVarianceCloudPopulation;
    }

    public ArrayList<Double> getBMVarianceGlobalPopulationClass1() {
        return BMVarianceGlobalPopulationClass1;
    }

    public ArrayList<Double> getBMVarianceCloudletPopulationClass1() {
        return BMVarianceCloudletPopulationClass1;
    }

    public ArrayList<Double> getBMVarianceCloudPopulationClass1() {
        return BMVarianceCloudPopulationClass1;
    }

    public ArrayList<Double> getBMVarianceGlobalPopulationClass2() {
        return BMVarianceGlobalPopulationClass2;
    }

    public ArrayList<Double> getBMVarianceCloudletPopulationClass2() {
        return BMVarianceCloudletPopulationClass2;
    }

    public ArrayList<Double> getBMVarianceCloudPopulationClass2() {
        return BMVarianceCloudPopulationClass2;
    }

    public void reset(){
        init();
    }























//    private ConfidenceInterval confidenceInterval;
//    double counter_batch;
//    long iterations;
//    int batch_size;
//    int num_batch;
//    private static BatchMeans instance = null;
//
//
//
//    //POPULATION BATCH
//    // [0] = mean, [1] = variance
//    private double[] BMGlobalPopulation;
//    private double[] BMCloudletPopulation;
//    private double[] BMCloudPopulation;
//    private double[] BMGlobalPopulationClass1;
//    private double[] BMCloudletPopulationClass1;
//    private double[] BMCloudPopulationClass1;
//    private double[] BMGlobalPopulationClass2;
//    private double[] BMCloudletPopulationClass2;
//    private double[] BMCloudPopulationClass2;
//
//
//    //TIME STATISTICS BATCH
//
//    private BatchMeans(){
//        confidenceInterval = ConfidenceInterval.getInstance();
//        iterations = SystemConfiguration.ITERATIONS;
//        num_batch = SystemConfiguration.NUM_BATCH;
//        computeBatchSize();
//        init();
//        counter_batch = 0;
//    }
//
//    public static BatchMeans getInstance(){
//        if(instance == null)
//            instance = new BatchMeans();
//        return instance;
//    }
//
//
//    public void computeBatchMeans(){
//        BMGlobalPopulation[0] = computeMean(BMGlobalPopulation[0]);
//        BMGlobalPopulation[1] = computeMean(BMGlobalPopulation[1]);
//        double[]res = confidenceInterval.computeConfidenceInterval(BMGlobalPopulation,num_batch);
//        System.out.println("global population: [" + res[0] + " , " + res[1] + " )");
//
//        BMCloudletPopulation[0] = computeMean(BMCloudletPopulation[0]);
//        BMCloudletPopulation[1] = computeMean(BMCloudletPopulation[1]);
//        res = confidenceInterval.computeConfidenceInterval(BMCloudletPopulation,num_batch);
//        System.out.println("cloudlet population: [" + res[0] + " , " + res[1] + " )");
//
//        BMCloudPopulation[0] = computeMean(BMCloudPopulation[0]);
//        BMCloudPopulation[1] = computeMean(BMCloudPopulation[1]);
//        res = confidenceInterval.computeConfidenceInterval(BMCloudPopulation,num_batch);
//        System.out.println("cloud population: [" + res[0] + " , " + res[1] + " )");
//
//        BMGlobalPopulationClass1[0] = computeMean(BMGlobalPopulationClass1[0]);
//        BMGlobalPopulationClass1[1] = computeMean(BMGlobalPopulationClass1[1]);
//        res = confidenceInterval.computeConfidenceInterval(BMGlobalPopulationClass1,num_batch);
//        System.out.println("glob pop class 1: [" + res[0] + " , " + res[1] + " )");
//
//        BMCloudletPopulationClass1[0] = computeMean(BMCloudletPopulationClass1[0]);
//        BMCloudletPopulationClass1[1] = computeMean(BMCloudletPopulationClass1[1]);
//        res = confidenceInterval.computeConfidenceInterval(BMCloudletPopulationClass1,num_batch);
//        System.out.println("cloudlet pop class 1: [" + res[0] + " , " + res[1] + " )");
//
//        BMCloudPopulationClass1[0] = computeMean(BMCloudPopulationClass1[0]);
//        BMCloudPopulationClass1[1] = computeMean(BMCloudPopulationClass1[1]);
//        res = confidenceInterval.computeConfidenceInterval(BMCloudPopulationClass1,num_batch);
//        System.out.println("cloud pop class 1: [" + res[0] + " , " + res[1] + " )");
//
//        BMGlobalPopulationClass2[0] = computeMean(BMGlobalPopulationClass2[0]);
//        BMGlobalPopulationClass2[1] = computeMean(BMGlobalPopulationClass2[1]);
//        res = confidenceInterval.computeConfidenceInterval(BMGlobalPopulationClass2,num_batch);
//        System.out.println("glob pop class 2: [" + res[0] + " , " + res[1] + " )");
//
//        BMCloudletPopulationClass2[0] = computeMean(BMCloudletPopulationClass2[0]);
//        BMCloudletPopulationClass2[1] = computeMean(BMCloudletPopulationClass2[1]);
//        res = confidenceInterval.computeConfidenceInterval(BMCloudletPopulationClass2,num_batch);
//        System.out.println("cloudlet pop class 2: [" + res[0] + " , " + res[1] + " )");
//
//        BMCloudPopulationClass2[0] = computeMean(BMCloudPopulationClass2[0]);
//        BMCloudPopulationClass2[1] = computeMean(BMCloudPopulationClass2[1]);
//        res = confidenceInterval.computeConfidenceInterval(BMCloudletPopulationClass2,num_batch);
//        System.out.println("cloud pop class 2: [" + res[0] + " , " + res[1] + " )");
//    }
//
//
//
//    public double computeMean(double val)
//    {
//        return val/num_batch;
//    }
//
//
//    public void computeBatchSize(){
//        batch_size = (int) (iterations / num_batch);
//    }
//
//    public int getBatchSize(){
//        return batch_size;
//    }
//
//
//    /**
//     * Methods to insert welford means into arrays at the end of the batch
//     */
//    public void updateBMGlobalPopulation(double valueToInsert, int index) {
//        double v = BMGlobalPopulation[index] + valueToInsert;
//        BMGlobalPopulation[index] = v;
//    }
//
//    public void updateBMCloudletPopulation(double valueToInsert, int index) {
//        double v = BMCloudletPopulation[index] + valueToInsert;
//        BMCloudletPopulation[index] = v;
//    }
//
//
//    public void updateBMCloudPopulation ( double valueToInsert, int index){
//        double v = BMCloudPopulation[index] + valueToInsert;
//        BMCloudPopulation[index] = v;
//    }
//
//    public void updateBMGlobalPopulationClass1 ( double valueToInsert, int index){
//        double v = BMGlobalPopulationClass1[index] + valueToInsert;
//        BMGlobalPopulationClass1[index] = v;
//    }
//
//    public void updateBMCloudletPopulationClass1 ( double valueToInsert, int index){
//        double v =  BMCloudletPopulationClass1[index] + valueToInsert;
//        BMCloudletPopulationClass1[index] = v;
//    }
//
//    public void updateBMCloudPopulationClass1 ( double valueToInsert, int index){
//        double v = BMCloudPopulationClass1[index] + valueToInsert;
//        BMCloudPopulationClass1[index] = v;
//    }
//
//    public void updateBMGlobalPopulationClass2 ( double valueToInsert, int index){
//        double v = BMGlobalPopulationClass2[index] + valueToInsert;
//        BMGlobalPopulationClass2[index] = v;
//    }
//
//    public void updateBMCloudletPopulationClass2 ( double valueToInsert, int index){
//        double v = BMCloudletPopulationClass2[index] + valueToInsert;
//        BMCloudletPopulationClass2[index] = v;
//    }
//
//    public void updateBMCloudPopulationClass2 (double valueToInsert, int index){
//        double v = BMCloudPopulationClass2[index] + valueToInsert;
//        BMCloudPopulationClass2[index] = v;
//    }
//
//    public double[] getBMGlobalPopulation() {
//        return BMGlobalPopulation;
//    }
//
//    public double[] getBMCloudletPopulation() {
//        return BMCloudletPopulation;
//    }
//
//    public double[] getBMCloudPopulation() {
//        return BMCloudPopulation;
//    }
//
//    public double[] getBMGlobalPopulationClass1() {
//        return BMGlobalPopulationClass1;
//    }
//
//    public double[] getBMCloudletPopulationClass1() {
//        return BMCloudletPopulationClass1;
//    }
//
//    public double[] getBMCloudPopulationClass1() {
//        return BMCloudPopulationClass1;
//    }
//
//    public double[] getBMGlobalPopulationClass2() {
//        return BMGlobalPopulationClass2;
//    }
//
//    public double[] getBMCloudletPopulationClass2() {
//        return BMCloudletPopulationClass2;
//    }
//
//    public double[] getBMCloudPopulationClass2() {
//        return BMCloudPopulationClass2;
//    }
//
//
//    public void init(){
//        BMGlobalPopulation = new double[2];
//        BMCloudletPopulation = new double[2];
//        BMCloudPopulation = new double[2];
//        BMGlobalPopulationClass1 = new double[2];
//        BMCloudletPopulationClass1= new double[2];
//        BMCloudPopulationClass1= new double[2];
//        BMGlobalPopulationClass2= new double[2];
//        BMCloudletPopulationClass2= new double[2];
//        BMCloudPopulationClass2= new double[2];
//    }
//
//
//    @Override
//    public String toString() {
//        return "BatchMeans{" +
//                "BMGlobalPopulation=" + Arrays.toString(BMGlobalPopulation) +
//                ", BMCloudletPopulation=" + Arrays.toString(BMCloudletPopulation) +
//                ", BMCloudPopulation=" + Arrays.toString(BMCloudPopulation) +
//                ", BMGlobalPopulationClass1=" + Arrays.toString(BMGlobalPopulationClass1) +
//                ", BMCloudletPopulationClass1=" + Arrays.toString(BMCloudletPopulationClass1) +
//                ", BMCloudPopulationClass1=" + Arrays.toString(BMCloudPopulationClass1) +
//                ", BMGlobalPopulationClass2=" + Arrays.toString(BMGlobalPopulationClass2) +
//                ", BMCloudletPopulationClass2=" + Arrays.toString(BMCloudletPopulationClass2) +
//                ", BMCloudPopulationClass2=" + Arrays.toString(BMCloudPopulationClass2) +
//                '}';
//    }

}



