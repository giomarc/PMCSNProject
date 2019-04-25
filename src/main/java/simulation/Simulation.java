package simulation;

import cloud.Cloud;
import results.CSVlogger;
import results.PerformanceLogger;
import cloudlet.Cloudlet;
import cloudlet.CloudletController;
import event.Event;
import event.EventGenerator;
import statistics.BatchMeans;
import statistics.JobStatistics;
import statistics.TimeStatistics;
import system.SystemConfiguration;
import variablesGenerator.InitGenerator;

public class Simulation {

    private static JobStatistics jobStatistics;
    private static TimeStatistics timeStatistics;
    private static BatchMeans batchMeans;
    private static CloudletController cloudletController;

    public static void main(String[] args) {
        initialize();
        if(SystemConfiguration.MULTI_RUN) {
            for(int i = 0; i< SystemConfiguration.RUNS; i++){
                runWithCustomSeed(SystemConfiguration.SEED + i);
                resetAll();
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
            jobStatistics.setGlobalTime(jobStatistics.getGlobalTime() + e.getJob().getArrivalTime());
        }
        jobStatistics.setGlobalTime(jobStatistics.getGlobalTime() + cloudletController.endSimulation());
        PerformanceLogger.getInstance().printFinalResults(jobStatistics,timeStatistics, batchMeans);
        //BatchMeans.getInstance().computeBatchMeans();

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
        PerformanceLogger.getInstance().printFinalResults(jobStatistics,timeStatistics,batchMeans);
    }

    private static void initialize(){
        SystemConfiguration.getConfigParams();
        jobStatistics = JobStatistics.getInstance();
        timeStatistics = TimeStatistics.getInstance();
        cloudletController = CloudletController.getInstance();
        batchMeans = BatchMeans.getInstance();
        CSVlogger.getInstance().createFileIfNotExists();
    }


    public static void resetAll(){
        initialize();
        JobStatistics.getInstance().resetStatistics();
        TimeStatistics.getInstance().resetStatistics();
        Cloud.getInstance().reset();
        Cloudlet.getInstance().reset();
        CSVlogger.getInstance().reset();
        BatchMeans.getInstance().reset();
    }

}
