package runners.simulation;

import cloud.Cloud;
import cloudlet.Cloudlet;
import cloudlet.CloudletController;
import event.ArrivalEvent;
import system.SystemConfiguration;
import event.Event;
import system.CSVlogger;
import system.PerformanceLogger;
import variablesGenerator.InitGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Simulation {

    private static Cloudlet cloudlet;
    private static Cloud cloud;
    private static StatisticsGenerator statistics;
    private static CloudletController cloudletController;
    private static double globalTime = 0.0;

    public static void main(String[] args) {
        if(args.length == 1)
            runWithCustomSeed(Long.parseLong(args[0]));
        run();
    }

    public static void run(){
        SystemConfiguration.getConfigParams();


        CSVlogger.getInstance().createFileIfNotExists("Response_time.csv",
                "Throughput.csv", "AVG_jobs.csv");
        if(SystemConfiguration.VERBOSE) {
            PerformanceLogger.getInstance().printInitialConfiguration();
            PerformanceLogger.getInstance().startTest();
        }

        initialize();

        for(int i = 0; i < SystemConfiguration.ITERATIONS; i++){
            ArrivalEvent e = ArrivalEvent.createNewArrivalEvent();
            cloudletController.dispatchArrivals(e);
            statistics.increaseAllPackets();
            globalTime += e.getTime();
        }
        PerformanceLogger.getInstance().printFinalResults(statistics, cloudlet, globalTime);
    }

    /**
     * fai girare la simulazione con un seed custom diverso da quello su file
     * utile per il calcolo di intervallo di confidenza e test vari
     * @param customSeed
     */
    private static void runWithCustomSeed(long customSeed) {
        InitGenerator.getInstance().putNewSeed(customSeed);
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

    public static void initialize(){
        cloudlet = new Cloudlet(SystemConfiguration.N);
        cloud = new Cloud();
        statistics = StatisticsGenerator.getInstance();
        cloudletController = new CloudletController(cloudlet,cloud);
    }



}
