package runners.simulation;

import cloud.Cloud;
import cloudlet.Cloudlet;
import cloudlet.CloudletController;
import event.Event;
import event.EventGenerator;
import runners.Statistics.JobStatistics;
import runners.Statistics.Statistics;
import runners.Statistics.TimeStatistics;
import system.CSVlogger;
import system.PerformanceLogger;
import system.SystemConfiguration;
import variablesGenerator.InitGenerator;

import java.sql.Time;


public class Simulation {

    private static JobStatistics jobStatistics;
    private static TimeStatistics timeStatistics;
    private static CloudletController cloudletController;
    //new development

    public static void main(String[] args) {
        initialize();
        if(args.length == 2) {
            System.out.println("running with custom seed");
            for(int i = 0; i< Integer.parseInt(args[1]); i++){
                runWithCustomSeed(Long.parseLong(args[0]) + i);
                resetAll();
            }


        }
        else
            run();

    }

    @SuppressWarnings("Duplicates")
    private static void run(){
        initialize();
        PerformanceLogger.getInstance().printInitialConfiguration();
        for(int i = 0; i < SystemConfiguration.ITERATIONS; i++){
            PerformanceLogger.getInstance().updateProgress(i, SystemConfiguration.ITERATIONS);
            Event e = EventGenerator.getInstance().generateArrival();
            cloudletController.dispatchArrivals(e);
            jobStatistics.setGlobalTime(jobStatistics.getGlobalTime() + e.getJob().getArrivalTime());
        }
        jobStatistics.setGlobalTime(jobStatistics.getGlobalTime() + cloudletController.endSimulation());
        PerformanceLogger.getInstance().printFinalResults(jobStatistics,timeStatistics);
    }

    @SuppressWarnings("Duplicates")
    private static void runWithCustomSeed(long customSeed) {
        initialize();
        InitGenerator.getInstance().putNewSeed(customSeed);
        PerformanceLogger.getInstance().printInitialConfiguration();
        for(int i = 0; i < SystemConfiguration.ITERATIONS; i++){
            PerformanceLogger.getInstance().updateProgress(i, SystemConfiguration.ITERATIONS);
            Event e = EventGenerator.getInstance().generateArrival();
            cloudletController.dispatchArrivals(e);
            jobStatistics.setGlobalTime(jobStatistics.getGlobalTime() + e.getJob().getArrivalTime());
        }
        jobStatistics.setGlobalTime(jobStatistics.getGlobalTime() + cloudletController.endSimulation());
        PerformanceLogger.getInstance().printFinalResults(jobStatistics,timeStatistics);
    }

    private static void initialize(){
        SystemConfiguration.getConfigParams();
        jobStatistics = JobStatistics.getInstance();
        timeStatistics = TimeStatistics.getInstance();
        cloudletController = CloudletController.getInstance();
        CSVlogger.getInstance().createFileIfNotExists();
    }


    public static void resetAll(){
        initialize();
        JobStatistics.getInstance().resetStatistics();
        TimeStatistics.getInstance().resetStatistics();
        Cloud.getInstance().reset();
        Cloudlet.getInstance().reset();
        CloudletController.getInstance().reset();
    }

}
