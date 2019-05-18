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

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("Duplicates")
public class Simulation {

    private static JobStatistics jobStatistics;
    private static TimeStatistics timeStatistics;
    private static BatchMeans batchMeans;
    private static CloudletController cloudletController;
    private static Cloudlet cloudlet;
    private static Cloud cloud;

    private static ArrayList<Event> eventList;

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
            eventList.add(e);
            sortEventList();
            handleEvent();
        }
        jobStatistics.setGlobalTime(jobStatistics.getGlobalTime() + handleEvent());

        PerformanceLogger.getInstance().printFinalResults(jobStatistics,timeStatistics, batchMeans);

    }

    private static void sortEventList(){

        eventList.sort((o1, o2) -> {
            if (o1.getEventTime() < o2.getEventTime())
                return -1;
            else
                return 1;
        });

    }

    private static double handleEvent(){
        Iterator i = eventList.iterator();
        double time = 0;
        while(i.hasNext()){
            Event e = (Event) i.next();
            if(e.getType() == 0){               // arrival
                break;
            }
            else if(e.getType() == 1){          // cloudlet
                cloudlet.processCompletion(e);
                time = e.getEventTime();
                i.remove();
            }
            else{                               // cloud
                cloud.processCompletion(e);
                time = e.getEventTime();
                i.remove();
            }
        }

        if(eventList.size() != 0) {
            Event e = eventList.get(0);
            cloudletController.dispatchArrivals(e);
            time = e.getEventTime();
            jobStatistics.setGlobalTime(jobStatistics.getGlobalTime() + e.getJob().getArrivalTime());
            jobStatistics.setActualTime(jobStatistics.getGlobalTime() + e.getJob().getArrivalTime());
            eventList.remove(0);

            for (Event event : eventList) {
                event.setEventTime(event.getEventTime() - time);
            }
        }
        else{
            return time;
        }
        return 0;

    }

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
        jobStatistics.setGlobalTime(jobStatistics.getGlobalTime() + handleEvent());
        PerformanceLogger.getInstance().printFinalResults(jobStatistics,timeStatistics,batchMeans);
    }

    private static void initialize(){
        eventList = new ArrayList<>();
        SystemConfiguration.getConfigParams();
        jobStatistics = JobStatistics.getInstance();
        timeStatistics = TimeStatistics.getInstance();
        cloudletController = CloudletController.getInstance();
        cloudlet = Cloudlet.getInstance();
        cloud = Cloud.getInstance();
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

    public static void addComplitionToEventList(Event e){
        eventList.add(e);
    }
}






















