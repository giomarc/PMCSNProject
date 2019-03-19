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
        if(args.length == 2) {
            System.out.println("running with custom seed");
            for(int i = 0; i< Integer.parseInt(args[1]); i++){
                runWithCustomSeed(Long.parseLong(args[0]) + i);
            }
        }
        else
            run();
    }

    private static void run(){
        initialize();
        PerformanceLogger.getInstance().printInitialConfiguration();
        for(int i = 0; i < SystemConfiguration.ITERATIONS; i++){
            PerformanceLogger.getInstance().updateProgress(i, SystemConfiguration.ITERATIONS);
            Event e = EventGenerator.getInstance().generateArrival();
            cloudletController.dispatchArrivals(e);
            statistics.setGlobalTime(statistics.getGlobalTime() + e.getJob().getArrivalTime());
        }
        statistics.setGlobalTime(statistics.getGlobalTime() + cloudletController.endSimulation());
        PerformanceLogger.getInstance().printFinalResults(statistics);
        statistics.printSampleMean();
        statistics.printVariance();
    }


    private static void runWithCustomSeed(long customSeed) {
        initialize();
        InitGenerator.getInstance().putNewSeed(customSeed);
        PerformanceLogger.getInstance().printInitialConfiguration();
        for(int i = 0; i < SystemConfiguration.ITERATIONS; i++){
            PerformanceLogger.getInstance().updateProgress(i, SystemConfiguration.ITERATIONS);
            Event e = EventGenerator.getInstance().generateArrival();
            cloudletController.dispatchArrivals(e);
            statistics.setGlobalTime(statistics.getGlobalTime() + e.getJob().getArrivalTime());
        }
        statistics.setGlobalTime(statistics.getGlobalTime() + cloudletController.endSimulation());
        PerformanceLogger.getInstance().printFinalResults(statistics);
        statistics.printSampleMean();
        statistics.printVariance();
    }

    private static void initialize(){
        SystemConfiguration.getConfigParams();
        statistics = StatisticsGenerator.getInstance();
        cloudletController = CloudletController.getInstance();
        CSVlogger.getInstance().createFileIfNotExists("Response_time.csv",
                "Throughput.csv", "AVG_jobs.csv");
    }



}
