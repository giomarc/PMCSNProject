package event;

import job.Job;
import system.SystemConfiguration;
import variablesGenerator.Arrivals;
import variablesGenerator.Services;



public class EventGenerator {

    private static EventGenerator instance = new EventGenerator();

    private EventGenerator(){}

    public static EventGenerator getInstance(){return instance;}

    /**
     * generate a new job with an own class, arrival time and (if enabled) the size. Then create a ner arrival event
     * @return
     */
    public Event generateArrival(){
        double arrival = Arrivals.getInstance().getArrival();
        Job job;
        if(SystemConfiguration.OPERATIONS_ENABLED)
            job = new Job(Arrivals.getInstance().determineJobClass(), arrival, Services.getInstance().getJobOperations());
        else
            job = new Job(Arrivals.getInstance().determineJobClass(), arrival, 0);
        return new Event(0, job);
    }

    /**
     * generate a new completion event
     * @param cloudletOrCloud: can assume only values {1,2} related to the system which has processed the job
     * @param job
     * @return
     */
    public Event generateCompletion(int cloudletOrCloud, Job job){
        if(cloudletOrCloud == 1 || cloudletOrCloud == 2)
            return new Event(cloudletOrCloud, job);
        else
            return null;
    }

}
