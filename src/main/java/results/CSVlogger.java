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
    private int totalMeansDuringSimulations = 2500;
    private int totalResponseTimeMeansDuringSimulations = 200000;
    private static int iterationsInOneBatch = (int) (SystemConfiguration.ITERATIONS/SystemConfiguration.NUM_BATCH) - 1;

    private String fileResponseTime;
    private String fileThroughput;
    private String fileAVGjobs;
    private String fileMeansInOneSimulation;
    private String fileServerStatus;
    private String fileBatchMeans;
    private String fileThroughputBatch;
    private String fileSystemSimulation;
    private String fileResponseTimeMeansInOneSimulation;
    private String fileBatchMeansPopulation;


    private CSVlogger(){}

    public static CSVlogger getInstance(){
        return instance;
    }

    /**
     * initialize the environment creating several files in the RESULT_OUTPUT directory
     */
    public void createFileIfNotExists(){
        if(SystemConfiguration.CSVLOGGER) {
            this.fileResponseTime = "ResponseTime.csv";
            this.fileThroughput = "Throughput.csv";
            this.fileAVGjobs = "AVGjobs.csv";
            this.fileMeansInOneSimulation = "MeansInOneSimulation.csv";
            this.fileServerStatus = "ServerStatus.csv";
            this.fileBatchMeans = "BatchMeans.csv";
            this.fileThroughputBatch = "ThoughputBatch.csv";
            this.fileSystemSimulation = "SystemSimulation.csv";
            this.fileResponseTimeMeansInOneSimulation = "ResponseTimeMeansInOneSimulation.csv";
            this.fileBatchMeansPopulation =  "BatchMeansPopulationCI.csv";


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
                File fileRTOS = new File("./RESULT_OUTPUT/" + fileResponseTimeMeansInOneSimulation);
                File fileMS = new File("./RESULT_OUTPUT/" + fileMeansInOneSimulation);
                File fileSV = new File("./RESULT_OUTPUT/" + fileServerStatus);
                File fileBM = new File("./RESULT_OUTPUT/" + fileBatchMeans);
                File fileTBM = new File("./RESULT_OUTPUT/" + fileThroughputBatch);
                File fileSS = new File("./RESULT_OUTPUT/" + fileSystemSimulation);
                File fileBMP = new File("./RESULT_OUTPUT/" + fileBatchMeansPopulation);


                if (fileRT.createNewFile()) {
                    BufferedWriter outRT = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileResponseTime, true));
                    System.out.println("File Response Time has been created.");
                    outRT.write("distribution, operations, algorithm, threshold, iterations, seed, n_server, "
                            + "Global_sys_response_time, Cloud_response_time, Cloudlet_response_time, "
                            + "Global_class1_response_time, Cloud_class1_response_time, Cloudlet_class1_response_time , "
                            + "Global_class2_response_time , Cloud_class2_response_time, Cloudlet_class2_response_time");
                    outRT.flush();
                }
                if (fileX.createNewFile()) {
                    BufferedWriter outX = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileThroughput, true));
                    System.out.println("File Throughput has been created.");
                    outX.write("distribution, operations, algorithm, threshold, iterations, seed, n_server, " +
                            "Global_sys_throughput, Cloud_throughput, Cloudlet_throughput, "
                            + "Global_class1_throughput, Cloud_class1_throughput, Cloudlet_class1_throughput,"
                            + "Global_class2_throughput, Cloud_class2_throughput, Cloudlet_class2_throughput");
                    outX.flush();
                }
                if (fileEN.createNewFile()) {
                    BufferedWriter outEN = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileAVGjobs, true));
                    System.out.println("File AVG jobs has been created.");
                    outEN.write("distribution, operations, algorithm, threshold, iterations, seed, n_server, " +
                            " Global_sys_avg_jobs, Cloud_avg_jobs, Cloudlet_avg_jobs, "
                            + "Global_class1_avg_jobs, Cloud_class1_avg_jobs, Cloudlet_class1_avg_jobs,"
                            + "Global_class2_avg_jobs, Cloud_class2_avg_jobs, Cloudlet_class2_avg_jobs");
                    outEN.flush();
                }
                if (fileSS.createNewFile()) {
                    BufferedWriter outSS = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileSystemSimulation, true));
                    System.out.println("File System simulation has been created.");
                    outSS.write("distribution, operations, algorithm, threshold, iterations, seed, n_server, " +
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
                        outSV.write("distribution, operations, algorithm, threshold, iterations, seed, " +
                                "n_server, id, utilization, packets_completed");
                        outSV.flush();
                    }
                    fileBM.delete();
                    fileTBM.delete();
                    if(SystemConfiguration.BATCH) {
                        if (fileBM.createNewFile()) {
                            BufferedWriter outBM = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileBatchMeans, true));
                            System.out.println("File Batch Means Mean Population has been created.");
                            outBM.write("batch_iteration, distribution, operations, algorithm, threshold, " +
                                    "iterations, seed, n_server, " +
                                    " Global_sys_avg_jobs, Cloud_avg_jobs, Cloudlet_avg_jobs,"
                                    + "Global_class1_avg_jobs, Cloud_class1_avg_jobs, Cloudlet_class1_avg_jobs, "
                                    + "Global_class2_avg_jobs, Cloud_class2_avg_jobs, Cloudlet_class2_avg_jobs,");
                            outBM.flush();
                        }


                        if (fileBMP.createNewFile()) {
                            BufferedWriter outBM = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileBatchMeansPopulation, true));
                            System.out.println("File Batch Means Mean Population has been created.");
                            outBM.write("distribution, operations, algorithm, threshold, iterations, seed, n_server, " +
                                    " Global_sys_avg_jobs, g_width, Cloud_avg_jobs, cd_width, Cloudlet_avg_jobs, cl_width, "
                                            + "Global_class1_avg_jobs, g1_width, Cloud_class1_avg_jobs, cd1_width, Cloudlet_class1_avg_jobs, cl1_width,"
                                            + "Global_class2_avg_jobs, g2_width, Cloud_class2_avg_jobs, cd2_width, Cloudlet_class2_avg_jobs cl2_width");
                            outBM.flush();
                        }


                        if(fileTBM.createNewFile()){
                            BufferedWriter outTBM = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileThroughputBatch, true));
                            System.out.println("File Throughput Batch Means has been created.");
                            outTBM.write("batch_iteration, distribution, operations, algorithm, treshold, " +
                                    "iterations, seed, n_server, System throughput, Sys class 1, Sys class 2, Cloudlet Throughput, " +
                                    "Clet class 1, Clet class 2, Cloud Throughput. Cloud class 1, Cloud class 2");
                            outTBM.flush();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    /**
     * write the files in the end of the simulation
     * @param globaltime
     */
    void writeOnFiles(double globaltime){
        JobStatistics js = JobStatistics.getInstance();
        TimeStatistics ts = TimeStatistics.getInstance();
        BatchMeans bm = BatchMeans.getInstance();
        if(SystemConfiguration.CSVLOGGER) {
            writeResponseTime(ts);
            writeAVGjobsPopulation(js);
            writeThroughput(js);
            writeSystemSimulation();
            if(!SystemConfiguration.MULTI_RUN) {
                writeServerStatistics(globaltime);
                if(SystemConfiguration.BATCH) {
                    writeBatchMeansjobs(bm);
                    writeCIBatchMeansPopulation(bm);
                    writeBatchMeansThroughput(bm);
                }
            }
        }
    }

    /**
     * write the file related to the batch means throughput
     * @param bm
     */
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
        int numberOfServers = SystemConfiguration.N;
        ArrayList<Double> systemThroughput = bm.getBMThroughputArray(0,"sys");
        ArrayList<Double> systemThroughput1 = bm.getBMThroughputArray(1,"sys");
        ArrayList<Double> systemThroughput2 = bm.getBMThroughputArray(2,"sys");

        ArrayList<Double> cloudletThroughput = bm.getBMThroughputArray(0,"clet");
        ArrayList<Double> cloudletThroughput1 = bm.getBMThroughputArray(1,"clet");
        ArrayList<Double> cloudletThroughput2 = bm.getBMThroughputArray(2,"clet");

        ArrayList<Double> cloudThroughput = bm.getBMThroughputArray(0,"cloud");
        ArrayList<Double> cloudThroughput1 = bm.getBMThroughputArray(1,"cloud");
        ArrayList<Double> cloudThroughput2 = bm.getBMThroughputArray(2,"cloud");

        BufferedWriter outAVG;
        try {
            outAVG = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileThroughputBatch, true));
            for(int i = 0; i < SystemConfiguration.NUM_BATCH; i++) {
                outAVG.write("\n" + i + ", " + distribution + ", " + operations + ", " + algorithm + ", " +
                        threshold + ", " + iterations + "," + seed + "," + numberOfServers + ',' +
                        systemThroughput.get(i) +  "," +  systemThroughput1.get(i) +  "," +  systemThroughput2.get(i) +
                        "," + cloudletThroughput.get(i) + "," + cloudletThroughput1.get(i) + "," + cloudletThroughput2.get(i) +
                        ","+ cloudThroughput.get(i) +  ","+ cloudThroughput1.get(i) +  ","+ cloudThroughput2.get(i) );
            }
            outAVG.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * write the file related to the response time
     * @param ts
     */
    private void writeResponseTime(TimeStatistics ts){
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
        int numberOfServers = SystemConfiguration.N;

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
            outRT.write("\n" + distribution + ", " + operations + ", " + algorithm + ", " + threshold + ", " +
                    iterations + "," + seed + "," + numberOfServers + ',' +
                    meanGlobalServiceTime + "," + meanCloudServiceTime + "," + meanCloudletServiceTime + "," +
                    meanClass1ServiceTime + "," + meanCloudServiceTimeClass1 + "," +
                    meanCloudletServiceTimeClass1 + "," +
                    meanClass2ServiceTime + "," + meanCloudServiceTimeClass2 + "," + meanCloudletServiceTimeClass2
            );
            outRT.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * write the file related to the server statistics
     * @param globalTime
     */
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
        int numberOfServers = SystemConfiguration.N;

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
                outServer.write("\n" + distribution + ", " + operations + ", " + algorithm + ", " + threshold +
                        ", " + iterations + "," + seed + "," + numberOfServers + ',' + id +
                        "," + utilization + "," + jobsCompleted);
                outServer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * write the file related to the throughput
     * @param js
     */
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
        int numberOfServers = SystemConfiguration.N;

        double globalThroughput = js.getSystemThroughput();
        double cloudletThroughput = js.getCloudletThroughput();
        double cloudThroughput = js.getCloudThroughput();
        double cloudletThroughputClass1 = js.getCloudletClass1Throughput();
        double cloudletThroughputClass2 = js.getCloudletClass2Throughput();
        double cloudThroughputClass1 = js.getCloudClass1Throughput();
        double cloudThroughputClass2 = js.getCloudClass2Throughput();
        double class1Throughput = js.getSystemClass1Throughput();
        double class2Throughput = js.getSystemClass2Throughput();

        BufferedWriter outRT;
        try {
            outRT = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileThroughput, true));
            outRT.write("\n" + distribution + ", " + operations + ", " + algorithm + ", " + threshold +
                    ", " + iterations + "," + seed + "," + numberOfServers + ',' +
                    globalThroughput + "," + cloudThroughput + "," + cloudletThroughput + "," +
                    class1Throughput + "," + cloudThroughputClass1 + "," + cloudletThroughputClass1 + "," +
                    class2Throughput + "," + cloudThroughputClass2 + "," + cloudletThroughputClass2
            );
            outRT.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write the file related to the average population for each system and class
     * @param js
     */
    private void writeAVGjobsPopulation(JobStatistics js){
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
        int numberOfServers = SystemConfiguration.N;

        double meanGlobalAvgJobs = js.getMeanGlobalPopulation(0);
        double meanCloudletAvgJobs = js.getMeanCloudletPopulation(0);
        double meanCloudAvgJobs = js.getMeanCloudPopulation(0);
        double meanCloudletAvgJobsClass1 = js.getMeanCloudletPopulation(1);
        double meanCloudletAvgJobsClass2 = js.getMeanCloudletPopulation(2);
        double meanCloudAvgJobsClass1 = js.getMeanCloudPopulation(1);
        double meanCloudAvgJobsClass2 = js.getMeanCloudPopulation(2);
        double meanClass1AvgJobs = js.getMeanGlobalPopulation(1);
        double meanClass2AvgJobs = js.getMeanGlobalPopulation(2);


        BufferedWriter outAVG;
        try {
            outAVG = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileAVGjobs, true));
            outAVG.write("\n" + distribution + ", " + operations + ", " + algorithm + ", " + threshold +
                    ", " + iterations + "," + seed + "," + numberOfServers + ',' +
                    meanGlobalAvgJobs + "," + meanCloudAvgJobs + "," + meanCloudletAvgJobs + "," +
                    meanClass1AvgJobs + "," + meanCloudAvgJobsClass1 + "," + meanCloudletAvgJobsClass1 + "," +
                    meanClass2AvgJobs + "," + meanCloudAvgJobsClass2 + "," + meanCloudletAvgJobsClass2
            );
            outAVG.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write the file related to the performance of the simulation
     */
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
        int numberOfServers = SystemConfiguration.N;

        long real_time =          pl.getRealTime();
        long simulation_time =    pl.getSimulationTime();
        double RAM =              pl.getRam();


        BufferedWriter outVar;
        try {
            outVar = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileSystemSimulation, true));
            outVar.write("\n" + distribution + ", " + operations + ", " + algorithm + ", " + threshold +
                    ", " + iterations + "," + seed + "," + numberOfServers + ',' +
                    real_time + "," + simulation_time + "," +  RAM);
            outVar.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write the file related to the average population using batch means
     * @param bm
     */
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
        int numberOfServers = SystemConfiguration.N;

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
                outAVG.write("\n" + i + ", " + distribution + ", " + operations + ", " + algorithm + ", " +
                        threshold + ", " + iterations + "," + seed + "," + numberOfServers + ',' +
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

    /**
     * write the file related to the batch means population with confidence interval
     * @param bm
     */
    private void writeCIBatchMeansPopulation(BatchMeans bm){
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
        int numberOfServers = SystemConfiguration.N;

        double[] globalAvgJobs = bm.getMeanBMGlobalPopulation(0);
        double[] cloudletAvgJobs = bm.getMeanBMCloudletPopulation(0);
        double[] cloudAvgJobs = bm.getMeanBMCloudPopulation(0);
        double[] cloudletAvgJobsClass1 = bm.getMeanBMCloudletPopulation(1);
        double[] cloudletAvgJobsClass2 = bm.getMeanBMCloudletPopulation(2);
        double[] cloudAvgJobsClass1 = bm.getMeanBMCloudPopulation(1);
        double[] cloudAvgJobsClass2 = bm.getMeanBMCloudPopulation(2);
        double[] class1AvgJobs = bm.getMeanBMGlobalPopulation(1);
        double[] class2AvgJobs = bm.getMeanBMGlobalPopulation(2);

        BufferedWriter outVar;
        try {
            outVar = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileBatchMeansPopulation, true));
            outVar.write( "\n" + distribution + ", " + operations + ", " + algorithm + ", " +
                  threshold + ", " + iterations + "," + seed + "," + numberOfServers + ',' +
                  globalAvgJobs[0] + "," + globalAvgJobs[1] + "," + cloudAvgJobs[0] + "," + cloudAvgJobs[1] + "," + cloudletAvgJobs[0] + "," + cloudletAvgJobs[1] + "," +
                    class1AvgJobs[0] + "," + class1AvgJobs[1] + "," + cloudAvgJobsClass1[0] + "," + cloudAvgJobsClass1[1] + "," + cloudletAvgJobsClass1[0] + "," + cloudletAvgJobsClass1[1] + "," +
                    class2AvgJobs[0] + "," + class2AvgJobs[1] + "," + cloudAvgJobsClass2[0] + "," + cloudAvgJobsClass2[1] + "," + cloudletAvgJobsClass2[0] + "," + cloudletAvgJobsClass2[1]);
            outVar.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write the file related to the population means in one simulation
     * @param js
     */
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

    /**
     * write the file related to the response time means in one simulation
     * @param jobClass
     * @param cloudletOrCloud
     * @param responseTime
     */
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
                        for(int i = 0; i < Math.min(maxRowsStored, totalResponseTimeMeansDuringSimulations) ; i++) {
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

    /**
     * reset the valure of totalMeansDuringSimulation
     */
    public void reset(){
        totalMeansDuringSimulations = 1000;
    }

    /**
     * class used to save a batch of data related to the population to write in into the correct file
     */
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

    /**
     * class used to save a batch of data related to the response time to write in into the correct file
     */
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


