package simulation;

import cloud.Cloud;
import cloudlet.Cloudlet;
import cloudlet.CloudletController;
import event.Event;
import event.EventGenerator;
import results.CSVlogger;
import results.PerformanceLogger;
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

    /**
     * If multi-run is enabled this function starts the simulation putting a new seed at the beginning
     * @param customSeed
     */
    private static void runWithCustomSeed(long customSeed) {
        initialize();
        InitGenerator.getInstance().putNewSeed(customSeed);
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

    /**
     * start the simulation
     */
    private static void run(){
        initialize();
        PerformanceLogger.getInstance().printInitialConfiguration();
        for(int i = 0; i < SystemConfiguration.ITERATIONS; i++){

            //print the percentage to the console
            PerformanceLogger.getInstance().updateProgress(i, SystemConfiguration.ITERATIONS);

            Event e = EventGenerator.getInstance().generateArrival();
            eventList.add(e);
            sortEventList();
            handleEvent();
        }
        jobStatistics.setGlobalTime(jobStatistics.getGlobalTime() + handleEvent());

        PerformanceLogger.getInstance().printFinalResults(jobStatistics,timeStatistics, batchMeans);
    }

    /**
     * sort the eventlist using the event time
     */
    private static void sortEventList(){

        eventList.sort((o1, o2) -> {
            if (o1.getEventTime() < o2.getEventTime())
                return -1;
            else if(o1.getEventTime() == o2.getEventTime()){
                if(o1.getType() != 0)
                    return -1;
                else
                    return 1;
            }
            else
                return 1;
        });

    }

    /**
     * extract the first event from the eventlist. If this event is a completion then process it and extract a new event.
     * If the event is an arrival then process it and exit from the cycle
     * @return
     */
    private static double handleEvent(){

        //the event is flagged. the event time of flagged events is reduced when an arrival is extracted
        for (Event event: eventList){
            event.setValid(true);
        }

        Iterator i = eventList.iterator();
        double time = 0;
        int[] numberOfJobsInCloudlet = {0,0};
        int[] numberOfJobsInCloud = {0,0};
        while(i.hasNext()){

            //retrieve the system state
            numberOfJobsInCloudlet =  Cloudlet.getInstance().getJobsInCloudlet();
            numberOfJobsInCloud =     Cloud.getInstance().returnJobsInCloud();

            Event e = (Event) i.next();
            time = e.getEventTime();

            if(e.getType() == 0){               // arrival
                break;
            }
            else if(e.getType() == 1){          // cloudlet
                if(e.isValid()) {
                    cloudlet.processCompletion(e);
                    i.remove();
                }
            }
            else{                               // cloud
                if(e.isValid()) {
                    cloud.processCompletion(e);
                    i.remove();
                }
            }
        }

        //if the simulation is not ended
        if(eventList.size() != 0) {
            Event e = eventList.get(0);

            //update the simulation clock
            jobStatistics.setGlobalTime(jobStatistics.getGlobalTime() + e.getJob().getArrivalTime());
            jobStatistics.setActualTime(jobStatistics.getActualTime() + e.getJob().getArrivalTime());

            //update population means
            jobStatistics.updatePopulationMeans(numberOfJobsInCloudlet, numberOfJobsInCloud);

            //process the arrival
            cloudletController.dispatchArrivals(e);

            eventList.remove(0);

            for (Event event : eventList) {
                if(event.isValid())
                    event.setEventTime(event.getEventTime() - time);
            }

        }

        else {
            return time;
        }

        return 0;

    }

    /**
     * initialiation function
     */
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

    /**
     * reset function used for the multi-run
     */
    public static void resetAll(){
        initialize();
        JobStatistics.getInstance().resetStatistics();
        TimeStatistics.getInstance().resetStatistics();
        Cloud.getInstance().reset();
        Cloudlet.getInstance().reset();
        CSVlogger.getInstance().reset();
        BatchMeans.getInstance().reset();
    }

    /**
     * add a completion event to the eventList
     * @param e
     */
    public static void addComplitionToEventList(Event e){
        e.setValid(false);
        eventList.add(e);
    }

}






















