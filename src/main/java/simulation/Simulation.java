package simulation;

import cloud.Cloud;
import cloudlet.Cloudlet;
import cloudlet.CloudletController;
import event.ArrivalEvent;
import system.SystemConfiguration;
import event.Event;
import system.CSVlogger;
import system.PerformanceLogger;
import system.Printer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Simulation {

    private static Cloudlet cloudlet;
    private static Cloud cloud;
    private static StatisticsGenerator statistics;
    private static CloudletController cloudletController;

    public static void main(String[] args) {
            run();
    }

    public static void run(){
        SystemConfiguration.getConfigParams();


        CSVlogger.getInstance().createFileIfNotExists("Response_time.csv",
                "Trhoughput.csv", "AVG_jobs.csv");
        if(SystemConfiguration.VERBOSE) {
            printInitialConfiguration();
            PerformanceLogger.getInstance().startTest();
        }

        initialize();

        for(int i = 0; i < SystemConfiguration.ITERATIONS; i++){

            ArrivalEvent e = ArrivalEvent.createNewArrivalEvent();
            cloudletController.dispatchArrivals(e);
            statistics.increaseAllPackets();
        }
        printFinalResults(statistics, cloudlet);
    }



    private static void saveEventsOnCSV(Cloudlet c){
        File file1 = new File("./events.txt");
        if(file1.delete()) System.out.println("files deleted");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./events.txt"));
            for(Event e : c.getCloudletEventList()) {
                if(e.getJobEvent().getJobclass()==1)
                    writer.write(e.getTime() + ", ");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printInitialConfiguration(){
        Printer.getInstance().print("Execution started with VERBOSE=true. To reduce logs start again the execution setting VERBOSE=false", "cyan");
        Printer.getInstance().print("\nARRIVAL RATES", "yellow");
        System.out.println("lambda_1 = " + SystemConfiguration.ARRIVAL_RATE_1);
        System.out.println("lambda_2 = " + SystemConfiguration.ARRIVAL_RATE_2);
        Printer.getInstance().print("\nSERVICE RATES", "yellow");
        Printer.getInstance().print("Cloudlet", "green");
        System.out.println("mu_1 = " + SystemConfiguration.CLOUDLET_M1);
        System.out.println("mu_2 = " + SystemConfiguration.CLOUDLET_M2);
        Printer.getInstance().print("Cloud", "green");
        System.out.println("mu_1 = " + SystemConfiguration.CLOUD_M1);
        System.out.println("mu_2 = " + SystemConfiguration.CLOUD_M2);
        Printer.getInstance().print("\n# CLOUDLET", "yellow");
        System.out.println(SystemConfiguration.N);
        Printer.getInstance().print("\nSEED", "yellow");
        System.out.println(SystemConfiguration.SEED);
    }

    private static void printFinalResults(StatisticsGenerator statistics, Cloudlet c){
        Printer.getInstance().print("\nP_LOSS", "yellow");
        System.out.println(statistics.calculatePLoss());
        if(SystemConfiguration.VERBOSE) {
            Printer.getInstance().print("\nANALYTIC THROUGHPUT", "yellow");
            System.out.println(statistics.getCloudletThroughput());
            Printer.getInstance().print("\nEMPIRICAL THROUGHPUT", "yellow");
            System.out.println(statistics.getSecondThroughput(c));
            PerformanceLogger.getInstance().endTest(c.getSimulationTime());
        }
    }

    public static void initialize(){
        cloudlet = new Cloudlet(SystemConfiguration.N);
        cloud = new Cloud();
        statistics = StatisticsGenerator.getInstance();
        cloudletController = new CloudletController(cloudlet,cloud);
    }



}
