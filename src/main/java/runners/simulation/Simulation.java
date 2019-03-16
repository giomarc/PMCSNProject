package runners.simulation;

import cloudlet.CloudletController;
import event.Event;
import event.EventGenerator;
import system.CSVlogger;
import system.PerformanceLogger;
import system.SystemConfiguration;
import variablesGenerator.InitGenerator;


public class Simulation {

    private static StatisticsGenerator statistics;
    private static CloudletController cloudletController;

    public static void main(String[] args) {
        if(args.length == 1) {
            System.out.println("running with custom seed");
            runWithCustomSeed(Long.parseLong(args[0]));
        }
        run();
    }

    private static void run(){
        SystemConfiguration.getConfigParams();
        CSVlogger.getInstance().createFileIfNotExists("Response_time.csv",
                "Throughput.csv", "AVG_jobs.csv");
        if(SystemConfiguration.VERBOSE) {
            PerformanceLogger.getInstance().printInitialConfiguration();
            PerformanceLogger.getInstance().startTest();
        }

        initialize();

        for(int i = 0; i < SystemConfiguration.ITERATIONS; i++){
            if(i%1000 == 0){
                PerformanceLogger.getInstance().updateProgress(i, SystemConfiguration.ITERATIONS);
            }
            Event e = EventGenerator.getInstance().generateArrival();
            cloudletController.dispatchArrivals(e);
            //statistics.increaseAllPackets();
            statistics.setGlobalTime(statistics.getGlobalTime() + e.getJob().getArrivalTime());
        }
        PerformanceLogger.getInstance().printFinalResults(statistics, statistics.getGlobalTime());
    }


    private static void runWithCustomSeed(long customSeed) {
        InitGenerator.getInstance().putNewSeed(customSeed);
    }

    private static void initialize(){
        statistics = StatisticsGenerator.getInstance();
        cloudletController = CloudletController.getInstance();
    }



}
