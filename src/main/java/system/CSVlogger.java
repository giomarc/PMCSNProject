package system;

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

            try {
                File directory = new File("./LOGS/");
                if (!directory.exists()) {
                    if (directory.mkdir()) {
                        System.out.println("LOGS Directory created");
                    }
                }

                File fileRT = new File("./LOGS/" + fileResponseTime);
                File fileX = new File("./LOGS/" + fileThroughput);
                File fileEN = new File("./LOGS/" + fileAVGjobs);
                File fileMS = new File("./LOGS/" + fileMeansInOneSimulation);
                if (fileRT.createNewFile()) {
                    BufferedWriter outRT = new BufferedWriter(new FileWriter("./LOGS/" + fileResponseTime, true));
                    System.out.println("File Response Time has been created.");
                    outRT.write("algorithm, threshold, iterations, seed, Global_sys_response_time, Cloud_response_time, Cloudlet_response_time, "
                            + "Global_class1_response_time, Cloud_class1_response_time, Cloudlet_class1_response_time , "
                            + "Global_class2_response_time , Cloud_class2_response_time, Cloudlet_class2_response_time");
                    outRT.flush();
                }
                if (fileX.createNewFile()) {
                    BufferedWriter outX = new BufferedWriter(new FileWriter("./LOGS/" + fileThroughput, true));
                    System.out.println("File Throughput has been created.");
                    outX.write("algorithm, threshold, iterations, seed, Global_sys_response_time, Cloud_response_time, Cloudlet_response_time, "
                            + "Global_class1_response_time, Cloud_class1_response_time, Cloudlet_class1_response_time,"
                            + "Global_class2_response_time , Cloud_class2_response_time, Cloudlet_class2_response_time");
                    outX.flush();
                }
                if (fileEN.createNewFile()) {
                    BufferedWriter outEN = new BufferedWriter(new FileWriter("./LOGS/" + fileAVGjobs, true));
                    System.out.println("File AVG jobs has been created.");
                    outEN.write("algorithm, threshold, iterations, seed,  Global_sys_response_time, Cloud_response_time, Cloudlet_response_time, "
                            + "Global_class1_response_time, Cloud_class1_response_time, Cloudlet_class1_response_time,"
                            + "Global_class2_response_time , Cloud_class2_response_time, Cloudlet_class2_response_time");
                    outEN.flush();
                }
                fileMS.delete();
                if (fileMS.createNewFile()) {
                    BufferedWriter outMS = new BufferedWriter(new FileWriter("./LOGS/" + fileMeansInOneSimulation, true));
                    System.out.println("File Means in one simulation jobs has been created.");
                    outMS.write("seed,  global_time, cloudlet_class1, cloudlet_class2, "
                            + "cloudletGeneral, cloud_class1, cloud_class2,"
                            + "cloudGeneral, class1, class2");
                    outMS.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public void writeOnFiles(TimeStatistics ts){
        if(SystemConfiguration.CSVLOGGER) {
            long seed = SystemConfiguration.SEED;
            long iterations = SystemConfiguration.ITERATIONS;
            int algorithm = SystemConfiguration.ALGORITHM;
            int threshold = -1;
            if(algorithm == 4)
                threshold = SystemConfiguration.THRESHOLD;

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
                outRT = new BufferedWriter(new FileWriter("./LOGS/" + fileResponseTime, true));
                outRT.write("\n" + algorithm + ", " + threshold + ", " + iterations + "," + seed + "," +
                        meanGlobalServiceTime + "," + meanCloudServiceTime + "," + meanCloudletServiceTime + "," +
                        meanClass1ServiceTime + "," + meanCloudServiceTimeClass1 + "," + meanCloudletServiceTimeClass1 + "," +
                        meanClass2ServiceTime + "," + meanCloudServiceTimeClass2 + "," + meanCloudletServiceTimeClass2
                );
                outRT.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                    outMS = new BufferedWriter(new FileWriter("./LOGS/" + fileMeansInOneSimulation, true));
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


}
