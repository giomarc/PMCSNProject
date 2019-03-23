package system;

import runners.simulation.StatisticsGenerator;

import java.io.*;

public class CSVlogger {

    private static CSVlogger instance = new CSVlogger();
    private String fileResponseTime;
    private String fileThroughput;
    private String fileAVGjobs;

    private CSVlogger(){}

    public static CSVlogger getInstance(){
        return instance;
    }

    public void createFileIfNotExists(String fileResponseTime, String fileThroughput, String fileAVGjobs){
        this.fileResponseTime = fileResponseTime;
        this.fileThroughput = fileThroughput;
        this.fileAVGjobs = fileAVGjobs;

        try {
            File directory = new File("./LOGS/");
            if (! directory.exists()){
                if(directory.mkdir()){
                    System.out.println("LOGS Directory created");
                }
            }

            File fileRT = new File("./LOGS/" + fileResponseTime);
            File fileX = new File("./LOGS/" + fileThroughput);
            File fileEN = new File("./LOGS/" + fileAVGjobs);
            if (fileRT.createNewFile()) {
                BufferedWriter outRT = new BufferedWriter(new FileWriter("./LOGS/" + fileResponseTime, true));
                System.out.println("File Response Time has been created.");
                outRT.write("iterations, seed, Global_sys_response_time, Cloud_response_time, Cloudlet_response_time, "
                         + "Global_class1_response_time, Cloud_class1_response_time, Cloudlet_class1_response_time , "
                         + "Global_class2_response_time , Cloud_class2_response_time, Cloudlet_class2_response_time");
                outRT.flush();
            }
            if (fileX.createNewFile()) {
                BufferedWriter outX = new BufferedWriter(new FileWriter("./LOGS/" + fileThroughput, true));
                System.out.println("File Throughput has been created.");
                outX.write("iterations, seed, Global_sys_response_time, Cloud_response_time, Cloudlet_response_time, "
                                + "Global_class1_response_time, Cloud_class1_response_time, Cloudlet_class1_response_time,"
                                + "Global_class2_response_time , Cloud_class2_response_time, Cloudlet_class2_response_time");
                outX.flush();
            }
            if (fileEN.createNewFile()) {
                BufferedWriter outEN = new BufferedWriter(new FileWriter("./LOGS/" + fileAVGjobs, true));
                System.out.println("File AVG jobs has been created.");
                outEN.write("iterations, seed,  Global_sys_response_time, Cloud_response_time, Cloudlet_response_time, "
                        + "Global_class1_response_time, Cloud_class1_response_time, Cloudlet_class1_response_time,"
                        + "Global_class2_response_time , Cloud_class2_response_time, Cloudlet_class2_response_time");
                outEN.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void writeOnFiles(StatisticsGenerator sg){
        long seed = SystemConfiguration.SEED;
        long iterations = SystemConfiguration.ITERATIONS;

        double meanGlobalServiceTime = sg.getMeanResponseTime();
        double meanCloudletServiceTime = sg.getMeanResponseTimeCloudlet();
        double meanCloudServiceTime = sg.getMeanResponseTimeCloud();
        double meanCloudletServiceTimeClass1 = sg.getMeanResponseTimeClass1Cloudlet();
        double meanCloudletServiceTimeClass2 = sg.getMeanResponseTimeClass2Cloudlet();
        double meanCloudServiceTimeClass1 = sg.getMeanResponseTimeClass1Cloud();
        double meanCloudServiceTimeClass2 = sg.getMeanResponseTimeClass2Cloud();
        double meanClass1ServiceTime = sg.getMeanResponseTimeClass1();
        double meanClass2ServiceTime = sg.getMeanResponseTimeClass2();

        BufferedWriter outRT;
        try {
            outRT = new BufferedWriter(new FileWriter("./LOGS/" + fileResponseTime, true));
            System.out.println("File Response Time has been created.");
            outRT.write("\n" + iterations + "," + seed + "," +
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
