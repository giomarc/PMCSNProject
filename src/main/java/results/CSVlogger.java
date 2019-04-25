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

public class CSVlogger {

    private static CSVlogger instance = new CSVlogger();
    private int totalMeansDuringSimulations = 2000;
    private String fileResponseTime;
    private String fileThroughput;
    private String fileAVGjobs;
    private String fileMeansInOneSimulation;
    private String fileServerStatus;
    private String fileBatchMeans;
    private String fileVarianceJobs;

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
                File fileMS = new File("./RESULT_OUTPUT/" + fileMeansInOneSimulation);
                File fileSV = new File("./RESULT_OUTPUT/" + fileServerStatus);
                File fileBM = new File("./RESULT_OUTPUT/" + fileBatchMeans);

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
                    fileSV.delete();
                    if (fileSV.createNewFile()) {
                        BufferedWriter outSV = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileServerStatus, true));
                        System.out.println("File Server Status in one simulation jobs has been created.");
                        outSV.write("distribution, operations, algorithm, threshold, iterations, seed, id, utilization, packets_completed");
                        outSV.flush();
                    }
                    fileBM.delete();
                    if (fileBM.createNewFile()) {
                        BufferedWriter outBM = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileBatchMeans, true));
                        System.out.println("File Batch Means has been created.");
                        outBM.write("batch_iteration, distribution, operations, algorithm, threshold, iterations, seed,  " +
                                " Global_sys_avg_jobs, Cloud_avg_jobs, Cloudlet_avg_jobs, "
                                + "Global_class1_avg_jobs, Cloud_class1_avg_jobs, Cloudlet_class1_avg_jobs,"
                                + "Global_class2_avg_jobs, Cloud_class2_avg_jobs, Cloudlet_class2_avg_jobs");
                        outBM.flush();
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
            // writeAVGjobs(bm);
            writeThroughput(js);
            if(!SystemConfiguration.MULTI_RUN) {
                writeServerStatistics(globaltime);
                if(SystemConfiguration.BATCH)
                    writeBatchMeansjobs(bm);
            }
        }
    }

    @SuppressWarnings("Duplicates")
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

    @SuppressWarnings("Duplicates")
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

    @SuppressWarnings("Duplicates")
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

    @SuppressWarnings("Duplicates")
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

   /* @SuppressWarnings("Duplicates")
    private void writeAVGjobs(BatchMeans bm){
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

       double meanGlobalAvgJobs = bm.getMeanBMGlobalPopulation();
        double meanCloudletAvgJobs = bm.getMeanBMCloudletPopulation();
        double meanCloudAvgJobs = bm.getMeanBMCloudPopulation();
        double meanCloudletAvgJobsClass1 = bm.getMeanBMCloudletPopulationClass1();
        double meanCloudletAvgJobsClass2 = bm.getMeanBMCloudletPopulationClass2();
        double meanCloudAvgJobsClass1 = bm.getMeanBMCloudPopulationClass1();
        double meanCloudAvgJobsClass2 = bm.getMeanBMCloudPopulationClass2();
        double meanClass1AvgJobs = bm.getMeanBMGlobalPopulationClass1();
        double meanClass2AvgJobs = bm.getMeanBMGlobalPopulationClass2();


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
    }*/

    private void writeBatchMeansjobs(BatchMeans js){
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

        ArrayList<Double> meanGlobalAvgJobs = js.getBMGlobalPopulation();
        ArrayList<Double> meanCloudletAvgJobs = js.getBMCloudletPopulation();
        ArrayList<Double> meanCloudAvgJobs = js.getBMCloudPopulation();
        ArrayList<Double> meanCloudletAvgJobsClass1 = js.getBMCloudletPopulationClass1();
        ArrayList<Double> meanCloudletAvgJobsClass2 = js.getBMCloudletPopulationClass2();
        ArrayList<Double> meanCloudAvgJobsClass1 = js.getBMCloudPopulationClass1();
        ArrayList<Double> meanCloudAvgJobsClass2 = js.getBMCloudPopulationClass2();
        ArrayList<Double> meanClass1AvgJobs = js.getBMGlobalPopulationClass1();
        ArrayList<Double> meanClass2AvgJobs = js.getBMGlobalPopulationClass2();


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

    public void reset(){
        totalMeansDuringSimulations = 1000;
    }


}
