package results;

import cloudlet.Cloudlet;
import cloudlet.Server;
import statistics.BatchMeans;
import statistics.JobStatistics;
import statistics.TimeStatistics;
import system.SystemConfiguration;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class CSVlogger {

    private static CSVlogger instance = new CSVlogger();

    private int maxRowsStored = 1000;
    private int totalMeansDuringSimulations = 2000;
    private int totalResponseTimeMeansDuringSimulations = 200000;

    private static int iterationsInOneBatch = (int) (SystemConfiguration.ITERATIONS/SystemConfiguration.NUM_BATCH) - 1;

    private String fileResponseTime;
    private String fileThroughput;
    private String fileAVGjobs;
    private String fileMeansInOneSimulation;
    private String fileServerStatus;
    private String fileBatchMeans;
    private String fileBatchVariance;
    private String fileVarianceJobs;
    private String fileThroughputBatch;
    private String fileSystemSimulation;
    private String fileResponseTimeMeansInOneSimulation;


    private CSVlogger(){}

    public static CSVlogger getInstance(){
        return instance;
    }

    public void createFileIfNotExists(){
        if(SystemConfiguration.CSVLOGGER) {
            this.fileResponseTime = "ResponseTime.csv";
            this.fileThroughput = "Throughput.csv";
            this.fileAVGjobs = "AVGjobs.csv";
            this.fileVarianceJobs = "VarianceJobs.csv";
            this.fileMeansInOneSimulation = "MeansInOneSimulation.csv";
            this.fileServerStatus = "ServerStatus.csv";
            this.fileBatchMeans = "BatchMeans.csv";
            this.fileThroughputBatch = "ThoughputBatch.csv";
            this.fileBatchVariance = "BatchVariances.csv";
            this.fileSystemSimulation = "SystemSimulation.csv";
            this.fileResponseTimeMeansInOneSimulation = "ResponseTimeMeansInOneSimulation.csv";


            try {
                File directory = new File("./RESULT_OUTPUT/");
                if (!directory.exists()) {
                    if (directory.mkdir()) {
                        System.out.println("RESULT_OUTPUT Directory created");
                    }
                }

                File fileRT = new File("./RESULT_OUTPUT/" + fileResponseTime);
                File fileX = new File("./RESULT_OUTPUT/" + fileThroughput);
                File fileEN = new File("./RESULT_OUTPUT/" + fileAVGjobs);
                File fileVJ = new File("./RESULT_OUTPUT/" + fileVarianceJobs);
                File fileRTOS = new File("./RESULT_OUTPUT/" + fileResponseTimeMeansInOneSimulation);
                File fileMS = new File("./RESULT_OUTPUT/" + fileMeansInOneSimulation);
                File fileSV = new File("./RESULT_OUTPUT/" + fileServerStatus);
                File fileBM = new File("./RESULT_OUTPUT/" + fileBatchMeans);
                File fileTBM = new File("./RESULT_OUTPUT/" + fileThroughputBatch);
                File fileBV = new File("./RESULT_OUTPUT/" + fileBatchVariance);
                File fileSS = new File("./RESULT_OUTPUT/" + fileSystemSimulation);

                if (fileRT.createNewFile()) {
                    BufferedWriter outRT = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileResponseTime, true));
                    System.out.println("File Response Time has been created.");
                    outRT.write("distribution, operations, algorithm, threshold, iterations, seed, Global_sys_response_time, Cloud_response_time, Cloudlet_response_time, "
                            + "Global_class1_response_time, Cloud_class1_response_time, Cloudlet_class1_response_time , "
                            + "Global_class2_response_time , Cloud_class2_response_time, Cloudlet_class2_response_time");
                    outRT.flush();
                }
                if (fileX.createNewFile()) {
                    BufferedWriter outX = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileThroughput, true));
                    System.out.println("File Throughput has been created.");
                    outX.write("distribution, operations, algorithm, threshold, iterations, seed, " +
                            "Global_sys_throughput, Cloud_throughput, Cloudlet_throughput, "
                            + "Global_class1_throughput, Cloud_class1_throughput, Cloudlet_class1_throughput,"
                            + "Global_class2_throughput, Cloud_class2_throughput, Cloudlet_class2_throughput");
                    outX.flush();
                }
                if (fileEN.createNewFile()) {
                    BufferedWriter outEN = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileAVGjobs, true));
                    System.out.println("File AVG jobs has been created.");
                    outEN.write("distribution, operations, algorithm, threshold, iterations, seed,  " +
                            " Global_sys_avg_jobs, Cloud_avg_jobs, Cloudlet_avg_jobs, "
                            + "Global_class1_avg_jobs, Cloud_class1_avg_jobs, Cloudlet_class1_avg_jobs,"
                            + "Global_class2_avg_jobs, Cloud_class2_avg_jobs, Cloudlet_class2_avg_jobs");
                    outEN.flush();
                }
                if (fileVJ.createNewFile()) {
                    BufferedWriter outVJ = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileVarianceJobs, true));
                    System.out.println("File Variance jobs has been created.");
                    outVJ.write("distribution, operations, algorithm, threshold, iterations, seed,  " +
                            " Global_sys_variance_jobs, Cloud_variance_jobs, Cloudlet_variance_jobs, "
                            + "Global_class1_variance_jobs, Cloud_class1_variance_jobs, Cloudlet_class1_variance_jobs,"
                            + "Global_class2_variance_jobs, Cloud_class2_variance_jobs, Cloudlet_class2_variance_jobs");
                    outVJ.flush();
                }
                if (fileSS.createNewFile()) {
                    BufferedWriter outSS = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileSystemSimulation, true));
                    System.out.println("File System simulation has been created.");
                    outSS.write("distribution, operations, algorithm, threshold, iterations, seed,  " +
                            " Real_time, Simulation_time, RAM_Usage");
                    outSS.flush();
                }
                if(!SystemConfiguration.MULTI_RUN) {
                    fileMS.delete();
                    if (fileMS.createNewFile()) {
                        BufferedWriter outMS = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileMeansInOneSimulation, true));
                        System.out.println("File Means in one simulation jobs has been created.");
                        outMS.write("seed,  global_time, cloudlet_class1, cloudlet_class2, "
                                + "cloudletGeneral, cloud_class1, cloud_class2,"
                                + "cloudGeneral, class1, class2");
                        outMS.flush();
                    }
                    fileRTOS.delete();
                    if (fileRTOS.createNewFile()) {
                        BufferedWriter outRTOS = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileResponseTimeMeansInOneSimulation, true));
                        System.out.println("File Response Time in one simulation has been created.");
                        outRTOS.write("seed,  class, cloudlet_or_cloud, response_time");
                        outRTOS.flush();
                    }
                    fileSV.delete();
                    if (fileSV.createNewFile()) {
                        BufferedWriter outSV = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileServerStatus, true));
                        System.out.println("File Server Status in one simulation jobs has been created.");
                        outSV.write("distribution, operations, algorithm, threshold, iterations, seed, id, utilization, packets_completed");
                        outSV.flush();
                    }
                    fileBM.delete();
                    fileTBM.delete();
                    if(SystemConfiguration.BATCH) {
                        if (fileBM.createNewFile()) {
                            BufferedWriter outBM = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileBatchMeans, true));
                            System.out.println("File Batch Means Mean Population has been created.");
                            outBM.write("batch_iteration, distribution, operations, algorithm, threshold, iterations, seed,  " +
                                    " Global_sys_avg_jobs, Cloud_avg_jobs, Cloudlet_avg_jobs, "
                                    + "Global_class1_avg_jobs, Cloud_class1_avg_jobs, Cloudlet_class1_avg_jobs,"
                                    + "Global_class2_avg_jobs, Cloud_class2_avg_jobs, Cloudlet_class2_avg_jobs");
                            outBM.flush();
                        }
                        if(fileTBM.createNewFile()){
                            BufferedWriter outBM = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileThroughputBatch, true));
                            System.out.println("File Throughput Batch Means has been created.");
                            outBM.write("batch_iteration, distribution, operations, algorithm, iterations, seed,  " +
                                    "System throughput, System variance, Cloudlet Throughput, Cloudlet variance, Cloud Throuhput, Cloud variance");
                            outBM.flush();
                        }
                    }
                    fileBV.delete();
                    if(SystemConfiguration.BATCH) {
                        if (fileBV.createNewFile()) {
                            BufferedWriter outBV = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileBatchVariance, true));
                            System.out.println("File Batch Means Population Variance has been created.");
                            outBV.write("batch_iteration, distribution, operations, algorithm, threshold, iterations, seed,  " +
                                    " Global_sys_variance_jobs, Cloud_variance_jobs, Cloudlet_variance_jobs, "
                                    + "Global_class1_variance_jobs, Cloud_class1_variance_jobs, Cloudlet_class1_variance_jobs,"
                                    + "Global_class2_variance_jobs, Cloud_class2_variance_jobs, Cloudlet_class2_variance_jobs");
                            outBV.flush();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public void writeOnFiles(double globaltime){
        JobStatistics js = JobStatistics.getInstance();
        TimeStatistics ts = TimeStatistics.getInstance();
        BatchMeans bm = BatchMeans.getInstance();
        if(SystemConfiguration.CSVLOGGER) {
            writeResponseTime(ts);
            writeAVGjobsPopulation(bm, js);
            writeVarianceJobsPopulation(bm, js);
            writeThroughput(js);
            writeSystemSimulation();
            if(!SystemConfiguration.MULTI_RUN) {
                writeServerStatistics(globaltime);
                if(SystemConfiguration.BATCH) {
                    writeBatchMeansjobs(bm);
                    writeBatchMeansVarianceJobs(bm);
                    writeBatchMeansThroughput(bm);
                }
            }
        }
    }

    private void writeBatchMeansThroughput(BatchMeans bm) {
        long seed = SystemConfiguration.SEED;
        long iterations = SystemConfiguration.ITERATIONS;
        int algorithm = SystemConfiguration.ALGORITHM;
        int threshold = -1;
        if(algorithm == 4)
            threshold = SystemConfiguration.THRESHOLD;
        String distribution;
        if (SystemConfiguration.HYPEREXPO_ENABLED)
            distribution = "hyperexponential";
        else
            distribution = "exponential";
        boolean operations = SystemConfiguration.OPERATIONS_ENABLED;
        ArrayList<Double> systemThroughput = bm.getAvgThroughputArray(0);
        ArrayList<Double> cloudletThroughput = bm.getAvgThroughputArray(1);
        ArrayList<Double> cloudThroughput = bm.getAvgThroughputArray(2);
        ArrayList<Double> varSystemThroughput = bm.getVarThroughputArray(0);
        ArrayList<Double> varCloudletThroughput = bm.getVarThroughputArray(1);
        ArrayList<Double> varCloudThroughput = bm.getVarThroughputArray(2);

        BufferedWriter outAVG;
        try {
            outAVG = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileThroughputBatch, true));
            for(int i = 0; i < SystemConfiguration.NUM_BATCH; i++) {
                outAVG.write("\n" + i + ", " + distribution + ", " + operations + ", " + algorithm + ", " + threshold + ", " + iterations + "," + seed + "," +
                        systemThroughput.get(i) + "," + varSystemThroughput + "," + cloudletThroughput.get(i) + "," + varCloudletThroughput + ","
                        + cloudThroughput.get(i) + "," + varCloudThroughput);
            }
            outAVG.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void writeResponseTime(TimeStatistics ts){
        long seed = SystemConfiguration.SEED;
        long iterations = SystemConfiguration.ITERATIONS;
        int algorithm = SystemConfiguration.ALGORITHM;
        int threshold = -1;
        if(algorithm == 4)
            threshold = SystemConfiguration.THRESHOLD;
        String distribution;
        if (SystemConfiguration.HYPEREXPO_ENABLED)
            distribution = "hyperexponential";
        else
            distribution = "exponential";
        boolean operations = SystemConfiguration.OPERATIONS_ENABLED;

        double meanGlobalServiceTime = ts.getMeanResponseTime();
        double meanCloudletServiceTime = ts.getMeanResponseTimeCloudlet();
        double meanCloudServiceTime = ts.getMeanResponseTimeCloud();
        double meanCloudletServiceTimeClass1 = ts.getMeanResponseTimeClass1Cloudlet();
        double meanCloudletServiceTimeClass2 = ts.getMeanResponseTimeClass2Cloudlet();
        double meanCloudServiceTimeClass1 = ts.getMeanResponseTimeClass1Cloud();
        double meanCloudServiceTimeClass2 = ts.getMeanResponseTimeClass2Cloud();
        double meanClass1ServiceTime = ts.getMeanResponseTimeClass1();
        double meanClass2ServiceTime = ts.getMeanResponseTimeClass2();

        BufferedWriter outRT;
        try {
            outRT = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileResponseTime, true));
            outRT.write("\n" + distribution + ", " + operations + ", " + algorithm + ", " + threshold + ", " + iterations + "," + seed + "," +
                    meanGlobalServiceTime + "," + meanCloudServiceTime + "," + meanCloudletServiceTime + "," +
                    meanClass1ServiceTime + "," + meanCloudServiceTimeClass1 + "," + meanCloudletServiceTimeClass1 + "," +
                    meanClass2ServiceTime + "," + meanCloudServiceTimeClass2 + "," + meanCloudletServiceTimeClass2
            );
            outRT.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeMeansInOneSimulation(JobStatistics js){
        if(SystemConfiguration.CSVLOGGER && !SystemConfiguration.MULTI_RUN) {
            if (totalMeansDuringSimulations > 0) {
                totalMeansDuringSimulations--;

                long seed = SystemConfiguration.SEED;
                double globalTime = js.getGlobalTime();

                double meanCloudletPopulation = js.getMeanCloudletPopulation(0);
                double meanCloudletPopulationClass1 = js.getMeanCloudletPopulation(1);
                double meanCloudletPopulationClass2 = js.getMeanCloudletPopulation(2);

                double meanCloudPopulation = js.getMeanCloudPopulation(0);
                double meanCloudPopulationClass1 = js.getMeanCloudPopulation(1);
                double meanCloudPopulationClass2 = js.getMeanCloudPopulation(2);

                double meanClass1Population = js.getMeanGlobalPopulation(1);
                double meanClass2Population = js.getMeanGlobalPopulation(2);

                BufferedWriter outMS;
                try {
                    outMS = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileMeansInOneSimulation, true));
                    outMS.write("\n" + seed + "," + globalTime + "," + meanCloudletPopulationClass1 + "," + meanCloudletPopulationClass2 + ","
                            + meanCloudletPopulation + "," + meanCloudPopulationClass1 + "," + meanCloudPopulationClass2 + ","
                            + meanCloudPopulation + "," + meanClass1Population + "," + meanClass2Population);
                    outMS.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeServerStatistics(double globalTime){

        long seed = SystemConfiguration.SEED;
        long iterations = SystemConfiguration.ITERATIONS;
        int algorithm = SystemConfiguration.ALGORITHM;
        int threshold = -1;
        if(algorithm == 4)
            threshold = SystemConfiguration.THRESHOLD;
        String distribution;
        if (SystemConfiguration.HYPEREXPO_ENABLED)
            distribution = "hyperexponential";
        else
            distribution = "exponential";
        boolean operations = SystemConfiguration.OPERATIONS_ENABLED;

        int id;
        double utilization;
        long jobsCompleted;
        BufferedWriter outServer;
        try {
            for(Server s : Cloudlet.getInstance().getServerList()) {
                id = s.getId();
                utilization = s.getTimeBusy()/globalTime;
                jobsCompleted = s.getJobProcessed();
                outServer = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileServerStatus, true));
                outServer.write("\n" + distribution + ", " + operations + ", " + algorithm + ", " + threshold + ", " + iterations + "," + seed + "," + id + "," + utilization + "," + jobsCompleted);
                outServer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeThroughput(JobStatistics js){
        long seed = SystemConfiguration.SEED;
        long iterations = SystemConfiguration.ITERATIONS;
        int algorithm = SystemConfiguration.ALGORITHM;
        int threshold = -1;
        if(algorithm == 4)
            threshold = SystemConfiguration.THRESHOLD;
        String distribution;
        if (SystemConfiguration.HYPEREXPO_ENABLED)
            distribution = "hyperexponential";
        else
            distribution = "exponential";
        boolean operations = SystemConfiguration.OPERATIONS_ENABLED;

        double cloudletThroughput = js.getCloudletThroughput();
        double cloudThroughput = js.getCloudThroughput();
        double globalThroughput = cloudletThroughput + cloudThroughput;
        double cloudletThroughputClass1 = js.getCloudletClass1Throughput();
        double cloudletThroughputClass2 = js.getCloudletClass2Throughput();
        double cloudThroughputClass1 = js.getCloudClass1Throughput();
        double cloudThroughputClass2 = js.getCloudClass2Throughput();
        double class1Throughput = cloudletThroughputClass1 + cloudThroughputClass1;
        double class2Throughput = cloudletThroughputClass2 + cloudThroughputClass2;

        BufferedWriter outRT;
        try {
            outRT = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileThroughput, true));
            outRT.write("\n" + distribution + ", " + operations + ", " + algorithm + ", " + threshold + ", " + iterations + "," + seed + "," +
                    globalThroughput + "," + cloudThroughput + "," + cloudletThroughput + "," +
                    class1Throughput + "," + cloudThroughputClass1 + "," + cloudletThroughputClass1 + "," +
                    class2Throughput + "," + cloudThroughputClass2 + "," + cloudletThroughputClass2
            );
            outRT.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAVGjobsPopulation(BatchMeans bm, JobStatistics js){
        long seed = SystemConfiguration.SEED;
        long iterations = SystemConfiguration.ITERATIONS;
        int algorithm = SystemConfiguration.ALGORITHM;
        int threshold = -1;
        if(algorithm == 4)
            threshold = SystemConfiguration.THRESHOLD;
        String distribution;
        if (SystemConfiguration.HYPEREXPO_ENABLED)
            distribution = "hyperexponential";
        else
            distribution = "exponential";
        boolean operations = SystemConfiguration.OPERATIONS_ENABLED;
        double meanGlobalAvgJobs;
        double meanCloudletAvgJobs;
        double meanCloudAvgJobs;
        double meanCloudletAvgJobsClass1;
        double meanCloudletAvgJobsClass2;
        double meanCloudAvgJobsClass1;
        double meanCloudAvgJobsClass2;
        double meanClass1AvgJobs;
        double meanClass2AvgJobs;

        if(SystemConfiguration.BATCH) {
            meanGlobalAvgJobs = bm.getMeanBMGlobalPopulation(0);
            meanCloudletAvgJobs = bm.getMeanBMCloudletPopulation(0);
            meanCloudAvgJobs = bm.getMeanBMCloudPopulation(0);
            meanCloudletAvgJobsClass1 = bm.getMeanBMCloudletPopulation(1);
            meanCloudletAvgJobsClass2 = bm.getMeanBMCloudletPopulation(2);
            meanCloudAvgJobsClass1 = bm.getMeanBMCloudPopulation(1);
            meanCloudAvgJobsClass2 = bm.getMeanBMCloudPopulation(2);
            meanClass1AvgJobs = bm.getMeanBMGlobalPopulation(1);
            meanClass2AvgJobs = bm.getMeanBMGlobalPopulation(2);
        }
        else{
            meanGlobalAvgJobs = js.getMeanGlobalPopulation(0);
            meanCloudletAvgJobs = js.getMeanCloudletPopulation(0);
            meanCloudAvgJobs = js.getMeanCloudPopulation(0);
            meanCloudletAvgJobsClass1 = js.getMeanCloudletPopulation(1);
            meanCloudletAvgJobsClass2 = js.getMeanCloudletPopulation(2);
            meanCloudAvgJobsClass1 = js.getMeanCloudPopulation(1);
            meanCloudAvgJobsClass2 = js.getMeanCloudPopulation(2);
            meanClass1AvgJobs = js.getMeanGlobalPopulation(1);
            meanClass2AvgJobs = js.getMeanGlobalPopulation(2);
        }


        BufferedWriter outAVG;
        try {
            outAVG = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileAVGjobs, true));
            outAVG.write("\n" + distribution + ", " + operations + ", " + algorithm + ", " + threshold + ", " + iterations + "," + seed + "," +
                    meanGlobalAvgJobs + "," + meanCloudAvgJobs + "," + meanCloudletAvgJobs + "," +
                    meanClass1AvgJobs + "," + meanCloudAvgJobsClass1 + "," + meanCloudletAvgJobsClass1 + "," +
                    meanClass2AvgJobs + "," + meanCloudAvgJobsClass2 + "," + meanCloudletAvgJobsClass2
            );
            outAVG.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeVarianceJobsPopulation(BatchMeans bm, JobStatistics js){
        long seed = SystemConfiguration.SEED;
        long iterations = SystemConfiguration.ITERATIONS;
        int algorithm = SystemConfiguration.ALGORITHM;
        int threshold = -1;
        if(algorithm == 4)
            threshold = SystemConfiguration.THRESHOLD;
        String distribution;
        if (SystemConfiguration.HYPEREXPO_ENABLED)
            distribution = "hyperexponential";
        else
            distribution = "exponential";
        boolean operations = SystemConfiguration.OPERATIONS_ENABLED;

        double varianceGlobalAvgJobs;
        double varianceCloudletAvgJobs;
        double varianceCloudAvgJobs;
        double varianceCloudletAvgJobsClass1;
        double varianceCloudletAvgJobsClass2;
        double varianceCloudAvgJobsClass1;
        double varianceCloudAvgJobsClass2;
        double varianceClass1AvgJobs;
        double varianceClass2AvgJobs;

        if(SystemConfiguration.BATCH) {
            varianceGlobalAvgJobs = bm.getMeanBMVarianceGlobalPopulation(0);
            varianceCloudletAvgJobs = bm.getMeanBMVarianceCloudletPopulation(0);
            varianceCloudAvgJobs = bm.getMeanBMVarianceCloudPopulation(0);
            varianceCloudletAvgJobsClass1 = bm.getMeanBMVarianceCloudletPopulation(1);
            varianceCloudletAvgJobsClass2 = bm.getMeanBMVarianceCloudletPopulation(2);
            varianceCloudAvgJobsClass1 = bm.getMeanBMVarianceCloudPopulation(1);
            varianceCloudAvgJobsClass2 = bm.getMeanBMVarianceCloudPopulation(2);
            varianceClass1AvgJobs = bm.getMeanBMVarianceGlobalPopulation(1);
            varianceClass2AvgJobs = bm.getMeanBMVarianceGlobalPopulation(2);
        }
        else {
            varianceGlobalAvgJobs = js.getVarGlobalPopulation(0);
            varianceCloudletAvgJobs = js.getVarCloudletPopulation(0);
            varianceCloudAvgJobs = js.getVarCloudPopulation(0);
            varianceCloudletAvgJobsClass1 = js.getVarCloudletPopulation(1);
            varianceCloudletAvgJobsClass2 = js.getVarCloudletPopulation(2);
            varianceCloudAvgJobsClass1 = js.getVarCloudPopulation(1);
            varianceCloudAvgJobsClass2 = js.getVarCloudPopulation(2);
            varianceClass1AvgJobs = js.getVarGlobalPopulation(1);
            varianceClass2AvgJobs = js.getVarGlobalPopulation(2);
        }


        BufferedWriter outVar;
        try {
            outVar = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileVarianceJobs, true));
            outVar.write("\n" + distribution + ", " + operations + ", " + algorithm + ", " + threshold + ", " + iterations + "," + seed + "," +
                    varianceGlobalAvgJobs + "," + varianceCloudAvgJobs + "," +       varianceCloudletAvgJobs + "," +
                    varianceClass1AvgJobs + "," + varianceCloudAvgJobsClass1 + "," + varianceCloudletAvgJobsClass1 + "," +
                    varianceClass2AvgJobs + "," + varianceCloudAvgJobsClass2 + "," + varianceCloudletAvgJobsClass2
            );
            outVar.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSystemSimulation(){
        PerformanceLogger pl = PerformanceLogger.getInstance();
        long seed = SystemConfiguration.SEED;
        long iterations = SystemConfiguration.ITERATIONS;
        int algorithm = SystemConfiguration.ALGORITHM;
        int threshold = -1;
        if(algorithm == 4)
            threshold = SystemConfiguration.THRESHOLD;
        String distribution;
        if (SystemConfiguration.HYPEREXPO_ENABLED)
            distribution = "hyperexponential";
        else
            distribution = "exponential";
        boolean operations = SystemConfiguration.OPERATIONS_ENABLED;

        long real_time =          pl.getRealTime();
        long simulation_time =    pl.getSimulationTime();
        double RAM =              pl.getRam();


        BufferedWriter outVar;
        try {
            outVar = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileSystemSimulation, true));
            outVar.write("\n" + distribution + ", " + operations + ", " + algorithm + ", " + threshold + ", " + iterations + "," + seed + "," +
                    real_time + "," + simulation_time + "," +  RAM);
            outVar.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeBatchMeansjobs(BatchMeans bm){
        long seed = SystemConfiguration.SEED;
        long iterations = SystemConfiguration.ITERATIONS;
        int algorithm = SystemConfiguration.ALGORITHM;
        int threshold = -1;
        if(algorithm == 4)
            threshold = SystemConfiguration.THRESHOLD;
        String distribution;
        if (SystemConfiguration.HYPEREXPO_ENABLED)
            distribution = "hyperexponential";
        else
            distribution = "exponential";
        boolean operations = SystemConfiguration.OPERATIONS_ENABLED;

        ArrayList<Double> meanGlobalAvgJobs = bm.getBMGlobalPopulation(0);
        ArrayList<Double> meanCloudletAvgJobs = bm.getBMCloudletPopulation(0);
        ArrayList<Double> meanCloudAvgJobs = bm.getBMCloudPopulation(0);
        ArrayList<Double> meanCloudletAvgJobsClass1 = bm.getBMCloudletPopulation(1);
        ArrayList<Double> meanCloudletAvgJobsClass2 = bm.getBMCloudletPopulation(2);
        ArrayList<Double> meanCloudAvgJobsClass1 = bm.getBMCloudPopulation(1);
        ArrayList<Double> meanCloudAvgJobsClass2 = bm.getBMCloudPopulation(2);
        ArrayList<Double> meanClass1AvgJobs = bm.getBMGlobalPopulation(1);
        ArrayList<Double> meanClass2AvgJobs = bm.getBMGlobalPopulation(2);


        BufferedWriter outAVG;
        try {
            outAVG = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileBatchMeans, true));
            for(int i = 0; i < SystemConfiguration.NUM_BATCH; i++) {
                outAVG.write("\n" + i + ", " + distribution + ", " + operations + ", " + algorithm + ", " + threshold + ", " + iterations + "," + seed + "," +
                        meanGlobalAvgJobs.get(i) + "," + meanCloudAvgJobs.get(i) + "," + meanCloudletAvgJobs.get(i) + "," +
                        meanClass1AvgJobs.get(i) + "," + meanCloudAvgJobsClass1.get(i) + "," + meanCloudletAvgJobsClass1.get(i) + "," +
                        meanClass2AvgJobs.get(i) + "," + meanCloudAvgJobsClass2.get(i) + "," + meanCloudletAvgJobsClass2.get(i)
                );
            }
            outAVG.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeBatchMeansVarianceJobs(BatchMeans bm){
        long seed = SystemConfiguration.SEED;
        long iterations = SystemConfiguration.ITERATIONS;
        int algorithm = SystemConfiguration.ALGORITHM;
        int threshold = -1;
        if(algorithm == 4)
            threshold = SystemConfiguration.THRESHOLD;
        String distribution;
        if (SystemConfiguration.HYPEREXPO_ENABLED)
            distribution = "hyperexponential";
        else
            distribution = "exponential";
        boolean operations = SystemConfiguration.OPERATIONS_ENABLED;

        ArrayList<Double> varianceGlobalAvgJobs =           bm.getBMVarianceGlobalPopulation(0);
        ArrayList<Double> varianceCloudletAvgJobs =         bm.getBMVarianceCloudletPopulation(0);
        ArrayList<Double> varianceCloudAvgJobs =            bm.getBMVarianceCloudPopulation(0);
        ArrayList<Double> varianceCloudletAvgJobsClass1 =   bm.getBMVarianceCloudletPopulation(1);
        ArrayList<Double> varianceCloudletAvgJobsClass2 =   bm.getBMVarianceCloudletPopulation(2);
        ArrayList<Double> varianceCloudAvgJobsClass1 =      bm.getBMVarianceCloudPopulation(1);
        ArrayList<Double> varianceCloudAvgJobsClass2 =      bm.getBMVarianceCloudPopulation(2);
        ArrayList<Double> varianceClass1AvgJobs =           bm.getBMVarianceGlobalPopulation(1);
        ArrayList<Double> varianceClass2AvgJobs =           bm.getBMVarianceGlobalPopulation(2);


        BufferedWriter outVar;
        try {
            outVar = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileBatchVariance, true));
            for(int i = 0; i < SystemConfiguration.NUM_BATCH; i++) {
                outVar.write("\n" + i + ", " + distribution + ", " + operations + ", " + algorithm + ", " + threshold + ", " + iterations + "," + seed + "," +
                        varianceGlobalAvgJobs.get(i) + "," + varianceCloudAvgJobs.get(i) + "," +       varianceCloudletAvgJobs.get(i) + "," +
                        varianceClass1AvgJobs.get(i) + "," + varianceCloudAvgJobsClass1.get(i) + "," + varianceCloudletAvgJobsClass1.get(i) + "," +
                        varianceClass2AvgJobs.get(i) + "," + varianceCloudAvgJobsClass2.get(i) + "," + varianceCloudletAvgJobsClass2.get(i)
                );
            }
            outVar.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset(){
        totalMeansDuringSimulations = 1000;
    }

    public void writePopulationMeanInOneSimulation(JobStatistics js){
        if(SystemConfiguration.CSVLOGGER && !SystemConfiguration.MULTI_RUN) {
            if(totalMeansDuringSimulations > iterationsInOneBatch)
                totalMeansDuringSimulations = iterationsInOneBatch;
            if (totalMeansDuringSimulations > 0) {
                totalMeansDuringSimulations--;

                PopOneSimulationToWrite.instance.globalTime.add(js.getGlobalTime());
                PopOneSimulationToWrite.instance.meanCloudletPopulation.add(js.getMeanCloudletPopulation(0));
                PopOneSimulationToWrite.instance.meanCloudletPopulationClass1.add(js.getMeanCloudletPopulation(1));
                PopOneSimulationToWrite.instance.meanCloudletPopulationClass2.add(js.getMeanCloudletPopulation(2));
                PopOneSimulationToWrite.instance.meanCloudPopulation.add(js.getMeanCloudPopulation(0));
                PopOneSimulationToWrite.instance.meanCloudPopulationClass1.add(js.getMeanCloudPopulation(1));
                PopOneSimulationToWrite.instance.meanCloudPopulationClass2.add(js.getMeanCloudPopulation(2));
                PopOneSimulationToWrite.instance.meanClass1Population.add(js.getMeanGlobalPopulation(1));
                PopOneSimulationToWrite.instance.meanClass2Population.add(js.getMeanGlobalPopulation(2));

                if(PopOneSimulationToWrite.instance.globalTime.size() == maxRowsStored || totalMeansDuringSimulations == 0){
                    BufferedWriter outMS;
                    try {
                        outMS = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileMeansInOneSimulation, true));
                        for(int i = 0; i < PopOneSimulationToWrite.instance.globalTime.size(); i++){
                            outMS.write("\n" + SystemConfiguration.SEED + "," +
                                    PopOneSimulationToWrite.instance.globalTime.get(i) + "," +
                                    PopOneSimulationToWrite.instance.meanCloudletPopulationClass1.get(i) + "," +
                                    PopOneSimulationToWrite.instance.meanCloudletPopulationClass2.get(i) + "," +
                                    PopOneSimulationToWrite.instance.meanCloudletPopulation.get(i) + "," +
                                    PopOneSimulationToWrite.instance.meanCloudPopulationClass1.get(i) + "," +
                                    PopOneSimulationToWrite.instance.meanCloudPopulationClass2.get(i) + "," +
                                    PopOneSimulationToWrite.instance.meanCloudPopulation.get(i) + "," +
                                    PopOneSimulationToWrite.instance.meanClass1Population.get(i) + "," +
                                    PopOneSimulationToWrite.instance.meanClass2Population.get(i) );
                        }
                        outMS.flush();
                        PopOneSimulationToWrite.instance.reset();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void writResponseTimeMeanInOneSimulation(int jobClass, int cloudletOrCloud, double responseTime){
        if(SystemConfiguration.CSVLOGGER && !SystemConfiguration.MULTI_RUN) {
            if (totalResponseTimeMeansDuringSimulations > 0) {
                totalResponseTimeMeansDuringSimulations--;

                RTOneSimulationToWrite.instance.jobClass.add(jobClass);
                RTOneSimulationToWrite.instance.cloudletOrCloud.add(cloudletOrCloud);
                RTOneSimulationToWrite.instance.responseTime.add(responseTime);

                long seed = SystemConfiguration.SEED;

                if(RTOneSimulationToWrite.instance.jobClass.size() == maxRowsStored || totalResponseTimeMeansDuringSimulations == 0){
                    BufferedWriter outMS;
                    try {
                        outMS = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileResponseTimeMeansInOneSimulation, true));
                        for(int i = 0; i < maxRowsStored ; i++) {
                            outMS.write("\n" + seed + "," +
                                    RTOneSimulationToWrite.instance.jobClass.get(i) + "," +
                                    RTOneSimulationToWrite.instance.cloudletOrCloud.get(i) + "," +
                                    RTOneSimulationToWrite.instance.responseTime.get(i));
                        }
                        outMS.flush();
                        RTOneSimulationToWrite.instance.reset();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    private static class PopOneSimulationToWrite {

        private static PopOneSimulationToWrite instance = new PopOneSimulationToWrite();


        ArrayList<Double> globalTime = new ArrayList<>();
        ArrayList<Double> meanCloudletPopulation = new ArrayList<>();
        ArrayList<Double> meanCloudletPopulationClass1 = new ArrayList<>();
        ArrayList<Double> meanCloudletPopulationClass2 = new ArrayList<>();
        ArrayList<Double> meanCloudPopulation = new ArrayList<>();
        ArrayList<Double> meanCloudPopulationClass1 = new ArrayList<>();
        ArrayList<Double> meanCloudPopulationClass2 = new ArrayList<>();
        ArrayList<Double> meanClass1Population = new ArrayList<>();
        ArrayList<Double> meanClass2Population = new ArrayList<>();

        void reset(){
            this.globalTime = new ArrayList<>();
            this.meanCloudletPopulation = new ArrayList<>();
            this.meanCloudletPopulationClass1 = new ArrayList<>();
            this.meanCloudletPopulationClass2 = new ArrayList<>();
            this.meanCloudPopulation = new ArrayList<>();
            this.meanCloudPopulationClass1 = new ArrayList<>();
            this.meanCloudPopulationClass2 = new ArrayList<>();
            this.meanClass1Population = new ArrayList<>();
            this.meanClass2Population = new ArrayList<>();
        }
    }

    private static class RTOneSimulationToWrite{

        private static RTOneSimulationToWrite instance = new RTOneSimulationToWrite();

        ArrayList<Integer> jobClass = new ArrayList<>();
        ArrayList<Integer> cloudletOrCloud = new ArrayList<>();
        ArrayList<Double> responseTime = new ArrayList<>();

        void reset(){
            jobClass = new ArrayList<>();
            cloudletOrCloud = new ArrayList<>();
            responseTime = new ArrayList<>();
        }
    }

}


