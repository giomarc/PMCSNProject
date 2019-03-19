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
                outRT.write("iterations, lambda1, lambda2, mu1cloudlet, mu2cloudlet, mu1cloud, mu2cloud, n, seed, " +
                        "system general, cloud general, cloudlet general, " +
                        "system class 1, cloud class 1, cloudlet class 1 , " +
                        "system class 2, cloud class 2, cloudlet class 2");
                outRT.flush();
            }
            if (fileX.createNewFile()) {
                BufferedWriter outX = new BufferedWriter(new FileWriter("./LOGS/" + fileThroughput, true));
                System.out.println("File Throughput has been created.");
                outX.write("iterations, lambda1, lambda2, mu1cloudlet, mu2cloudlet, mu1cloud, mu2cloud, n, seed, " +
                        "system general, cloud general, cloudlet general, " +
                        "system class 1, cloud class 1, cloudlet class 1 , " +
                        "system class 2, cloud class 2, cloudlet class 2");
                outX.flush();
            }
            if (fileEN.createNewFile()) {
                BufferedWriter outEN = new BufferedWriter(new FileWriter("./LOGS/" + fileAVGjobs, true));
                System.out.println("File AVG jobs has been created.");
                outEN.write("iterations, lambda1, lambda2, mu1cloudlet, mu2cloudlet, mu1cloud, mu2cloud, n, seed, " +
                        "system general, cloud general, cloudlet general, " +
                        "system class 1, cloud class 1, cloudlet class 1 , " +
                        "system class 2, cloud class 2, cloudlet class 2");
                outEN.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void writeOnFiles(StatisticsGenerator sg){
        double lambda1 = SystemConfiguration.ARRIVAL_RATE_1;
        double lambda2 = SystemConfiguration.ARRIVAL_RATE_2;
        double mu1cloudlet = SystemConfiguration.CLOUDLET_M1;
        double mu2cloudlet = SystemConfiguration.CLOUDLET_M2;
        double mu1Cloud = SystemConfiguration.CLOUD_M1;
        double mu2Cloud = SystemConfiguration.CLOUD_M2;
        int nServers = SystemConfiguration.N;
        long seed = SystemConfiguration.SEED;
        long iterations = SystemConfiguration.ITERATIONS;

        double meanGlobalServiceTime = sg.getMeanServiceTime();
        double meanCloudletServiceTime = sg.getMeanServiceTimeCloudlet();
        double meanCloudServiceTime = sg.getMeanServiceTimeCloud();
        double meanCloudletServiceTimeClass1 = sg.getMeanServiceTimeClass1Cloudlet();
        double meanCloudletServiceTimeClass2 = sg.getMeanServiceTimeClass2Cloudlet();
        double meanCloudServiceTimeClass1 = sg.getMeanServiceTimeClass1Cloud();
        double meanCloudServiceTimeClass2 = sg.getMeanServiceTimeClass2Cloud();
        double meanClass1ServiceTime = sg.getMeanServiceTimeClass1();
        double meanClass2ServiceTime = sg.getMeanServiceTimeClass2();

        BufferedWriter outRT;
        try {
            outRT = new BufferedWriter(new FileWriter("./LOGS/" + fileResponseTime, true));
            System.out.println("File Response Time has been created.");
            outRT.write("\n" + iterations + "," + lambda1 + "," + lambda2 + "," +
                    mu1cloudlet + "," + mu2cloudlet + "," + mu1Cloud + "," + mu2Cloud + "," +
                    nServers + "," + seed + "," +
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
