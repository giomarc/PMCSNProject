package system;

import cloud.Cloud;
import cloudlet.Cloudlet;
import cloudlet.CloudletController;
import cloudlet.Server;
import runners.Statistics.JobStatistics;
import runners.Statistics.Statistics;
import runners.Statistics.TimeStatistics;

import java.io.*;

public class CSVlogger {

    private static CSVlogger instance = new CSVlogger();
    private int totalMeansDuringSimulations = 1000;
    private String fileResponseTime;
    private String fileThroughput;
    private String fileAVGjobs;
    private String fileMeansInOneSimulation;
    private String fileServerStatus;

    private CSVlogger(){}

    public static CSVlogger getInstance(){
        return instance;
    }

    public void createFileIfNotExists(){
        if(SystemConfiguration.CSVLOGGER) {
            this.fileResponseTime = "ResponseTime.csv";
            this.fileThroughput = "Throughput.csv";
            this.fileAVGjobs = "AVGjobs.csv";
            this.fileMeansInOneSimulation = "MeansInOneSimulation.csv";
            this.fileServerStatus = "ServerStatus.csv";


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
                File fileMS = new File("./RESULT_OUTPUT/" + fileMeansInOneSimulation);
                File fileSV = new File("./RESULT_OUTPUT/" + fileServerStatus);
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
                    outX.write("distribution, operations, algorithm, threshold, iterations, seed, Global_sys_response_time, Cloud_response_time, Cloudlet_response_time, "
                            + "Global_class1_response_time, Cloud_class1_response_time, Cloudlet_class1_response_time,"
                            + "Global_class2_response_time , Cloud_class2_response_time, Cloudlet_class2_response_time");
                    outX.flush();
                }
                if (fileEN.createNewFile()) {
                    BufferedWriter outEN = new BufferedWriter(new FileWriter("./RESULT_OUTPUT/" + fileAVGjobs, true));
                    System.out.println("File AVG jobs has been created.");
                    outEN.write("distribution, operations, algorithm, threshold, iterations, seed,  Global_sys_response_time, Cloud_response_time, Cloudlet_response_time, "
                            + "Global_class1_response_time, Cloud_class1_response_time, Cloudlet_class1_response_time,"
                            + "Global_class2_response_time , Cloud_class2_response_time, Cloudlet_class2_response_time");
                    outEN.flush();
                }
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
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public void writeOnFiles(TimeStatistics ts, double globaltime){
        if(SystemConfiguration.CSVLOGGER) {
            writeResponseTime(ts);
            writeServerStatistics(globaltime);
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

    public void writeMeanPopulation(JobStatistics js){
        if(SystemConfiguration.CSVLOGGER) {
            if (totalMeansDuringSimulations > 0) {
                totalMeansDuringSimulations--;

                long seed = SystemConfiguration.SEED;
                double globalTime = js.getGlobalTime();

                double meanCloudletPopulation = js.getMeanCloudletPopulation();
                double meanCloudletPopulationClass1 = js.getMeanCloudletPopulationClass1();
                double meanCloudletPopulationClass2 = js.getMeanCloudletPopulationClass2();

                double meanCloudPopulation = js.getMeanCloudPopulation();
                double meanCloudPopulationClass1 = js.getMeanCloudPopulationClass1();
                double meanCloudPopulationClass2 = js.getMeanCloudPopulationClass2();

                double meanClass1Population = js.getMeanGlobalPopulationClass1();
                double meanClass2Population = js.getMeanGlobalPopulationClass2();

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

    public void reset(){
        totalMeansDuringSimulations = 1000;
    }


}
